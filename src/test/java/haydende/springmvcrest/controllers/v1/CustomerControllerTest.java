package haydende.springmvcrest.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import haydende.springmvcrest.api.model.CustomerDTO;
import haydende.springmvcrest.domain.Customer;
import haydende.springmvcrest.services.CustomerService;
import org.json.JSONString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {

    private static final Long ID = Long.valueOf(1L);
    private static final String NAME = "Hayden";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        List<CustomerDTO> customers = Arrays.asList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void testGetCustomerByName() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setName(NAME);

        when(customerService.getCustomerByName(anyString())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/" + NAME)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/" + ID)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setName(NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setName(NAME);
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        mockMvc.perform(post("/api/v1/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", equalTo(NAME)))
            .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setName(NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setName(NAME);
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer)))
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete("/api/v1/customers/" + ID))
                .andExpect(status().isOk());
    }

    /**
     * Method for converting Java Object into JSON Strings.
     * @param obj Object to convert
     * @return JSON String object
     */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}