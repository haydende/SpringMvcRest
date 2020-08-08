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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Test
    void updateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName(NAME);
        customer.setId(ID);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(customer.getId());
        returnDTO.setName(customer.getName());

        when(customerRepository.save(any())).thenReturn(customer);

        CustomerDTO actualDTO = customerService.saveCustomerByDTO(returnDTO.getId(), returnDTO);

        assertEquals(ID, actualDTO.getId());
        assertEquals(NAME, actualDTO.getName());
    }

    @Test
    void deleteCustomerById() throws Exception {
        customerRepository.deleteById(ID);

        verify(customerRepository, times(1)).deleteById(ID);
    }
}