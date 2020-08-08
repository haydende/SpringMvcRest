package haydende.springmvcrest.services;

import haydende.springmvcrest.api.mapper.CustomerMapper;
import haydende.springmvcrest.api.model.CustomerDTO;
import haydende.springmvcrest.domain.Customer;
import haydende.springmvcrest.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(customerMapper::customerToCustomerDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByName(String name) {
        return customerMapper.customerToCustomerDTO(customerRepository.findByName(name));
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnCustomerDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());

        return returnCustomerDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    /**
     * Method for saving a Customer instance and returning back the DTO version.
     * @param customerToSave Customer instance
     * @return Saved version of that customer instance that have been converted into a CustomerDTO
     */
    private CustomerDTO saveAndReturnDTO(Customer customerToSave) {
        log.debug("Now in saveAndReturnDTO");
        log.debug("customerToSave type: " + customerToSave.getClass().toString());
        log.debug("customerToSave.getName(): " + customerToSave.getName());
        Customer savedCustomer = customerRepository.save(customerToSave);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        customerDTO.setCustomerUrl("/api/1/customer/" + customerDTO.getId());
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }
}
