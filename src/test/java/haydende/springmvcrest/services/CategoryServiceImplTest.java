package haydende.springmvcrest.services;

import haydende.springmvcrest.api.model.CategoryDTO;
import haydende.springmvcrest.domain.Category;
import haydende.springmvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {

    private static final Long ID = Long.valueOf(1L);
    private static final String NAME = "Dried";

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> actualCategories = categoryService.getAllCategories();

        assertEquals(Integer.valueOf(3), actualCategories.size());
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setName(NAME);

        when(categoryRepository.findByName(NAME)).thenReturn(category);

        CategoryDTO actualCategory = categoryService.getCategoryByName(NAME);

        assertEquals(NAME, actualCategory.getName());
    }
}