package haydende.springmvcrest.bootstrap;

import haydende.springmvcrest.domain.Category;
import haydende.springmvcrest.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    CategoryRepository categoryRepository;

    public DataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruit = new Category();
        Category nuts = new Category();
        Category dried = new Category();

        categoryRepository.save(fruit);
        categoryRepository.save(nuts);
        categoryRepository.save(dried);
    }
}
