package haydende.springmvcrest.services;

import haydende.springmvcrest.api.model.CustomerDTO;
import haydende.springmvcrest.bootstrap.DataLoader;
import haydende.springmvcrest.domain.Customer;
import haydende.springmvcrest.repositories.CategoryRepository;
import haydende.springmvcrest.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeEach
    public void setUp() throws Exception {
        log.debug("Loading the Customer data");

        DataLoader dataLoader = new DataLoader(categoryRepository, customerRepository);
        dataLoader.run();

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void patchCustomerUpdateName() throws Exception {
        String updateName = "updatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        String originalName = originalCustomer.getName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(updateName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();
        assertNotNull(updatedCustomer);
        assertEquals(updateName, updatedCustomer.getName());
        assertThat(originalName, not(equalTo(updatedCustomer)));
    }

    public Long getCustomerIdValue() throws Exception {
        List<Customer> customers = customerRepository.findAll();
        return customers.get(0).getId();
    }
}
