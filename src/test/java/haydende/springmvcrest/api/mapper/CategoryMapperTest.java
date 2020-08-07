package haydende.springmvcrest.api.mapper;

import haydende.springmvcrest.api.model.CategoryDTO;
import haydende.springmvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String NAME = "Dried";
    public static final Long ID = Long.valueOf(1L);

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() throws Exception {

        // given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        // when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // then
        assertEquals(Long.valueOf(1L), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}