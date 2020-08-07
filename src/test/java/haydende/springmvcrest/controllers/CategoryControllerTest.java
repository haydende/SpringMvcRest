package haydende.springmvcrest.controllers;

import haydende.springmvcrest.api.model.CategoryDTO;
import haydende.springmvcrest.repositories.CategoryRepository;
import haydende.springmvcrest.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

    public static final String NAME = "Dried";

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testListCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName(NAME);

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Nuts");

        List<CategoryDTO> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        // tell the mock mvc to GET /api/v1/categories/
        mockMvc.perform(get("/api/v1/categories/")
            // expect a content type of a JSON file
            .contentType(MediaType.APPLICATION_JSON))
            // expect the HTTP code to be 200
            .andExpect(status().isOk())
            // expect the number of Category items in the JSON to be 2
            .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName(NAME);

        when(categoryService.getCategoryByName(anyString())).thenReturn(category1);

        // tell the mock mvc to GET /api/v1/categories/dried/
        mockMvc.perform(get("/api/v1/categories/" + NAME)
            // expect a content type of a JSON file
            .contentType(MediaType.APPLICATION_JSON))
            // expect the HTTP code to be 200
            .andExpect(status().isOk())
            // expect the name of the Category item in the JSON to be "Dried"
            .andExpect(jsonPath("$.name", equalTo(NAME)));
    }
}