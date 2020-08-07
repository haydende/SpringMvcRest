package haydende.springmvcrest.api.mapper;

import haydende.springmvcrest.api.model.CategoryDTO;
import haydende.springmvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // map the Category.id to CategoryDTO.id
    @Mapping(source = "id", target = "id")
    CategoryDTO categoryToCategoryDTO(Category category);
}
