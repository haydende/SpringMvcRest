package haydende.springmvcrest.bootstrap;

import haydende.springmvcrest.domain.Category;
import haydende.springmvcrest.domain.Customer;
import haydende.springmvcrest.repositories.CategoryRepository;
import haydende.springmvcrest.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;

    public DataLoader(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruit = new Category();
        Category nuts = new Category();
        Category dried = new Category();

        fruit.setName("fruit");
        nuts.setName("nuts");
        dried.setName("dried");

        categoryRepository.save(fruit);
        categoryRepository.save(nuts);
        categoryRepository.save(dried);

        log.debug("-------------------- Saved the Categories --------------------");

        Customer hayden = new Customer();
        Customer barry = new Customer();
        Customer scott = new Customer();

        hayden.setName("Hayden");
        barry.setName("Barry");
        scott.setName("Scott");

        customerRepository.save(hayden);
        customerRepository.save(barry);
        customerRepository.save(scott);

        log.debug("-------------------- Saved the Customers --------------------");

    }
}
