package haydende.springmvcrest.api.mapper;

import haydende.springmvcrest.api.model.CustomerDTO;
import haydende.springmvcrest.domain.Category;
import haydende.springmvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerMapperTest {

    public static final String NAME = "Dried";
    public static final Long ID = Long.valueOf(1L);

    CustomerMapper categoryMapper = CustomerMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() throws Exception {

        // given
        Customer customer = new Customer();
        customer.setName(NAME);
        customer.setId(ID);

        // when
        CustomerDTO customerDTO = categoryMapper.customerToCustomerDTO(customer);

        // then
        assertEquals(Long.valueOf(1L), customerDTO.getId());
        assertEquals(NAME, customerDTO.getName());
    }
}
