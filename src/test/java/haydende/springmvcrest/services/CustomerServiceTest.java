package haydende.springmvcrest.services;

import haydende.springmvcrest.api.model.CustomerDTO;
import haydende.springmvcrest.domain.Customer;
import haydende.springmvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    private static final Long ID = Long.valueOf(1L);
    private static final String NAME = "Barry";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();

        assertEquals(3, customerDTOs.size());
    }

    @Test
    void getCustomerByName() {
        Customer customer = new Customer();
        customer.setName(NAME);

        when(customerRepository.findByName(anyString())).thenReturn(customer);

        CustomerDTO customerDTO = customerService.getCustomerByName(NAME);

        assertEquals(NAME, customerDTO.getName());
    }
}