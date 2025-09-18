package com.library.mapper;

import com.library.dto.CategoryDto;
import com.library.dto.response.CategoryResponse;
import com.library.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper mapper= Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(Category category);

    @Mapping(target = "parent", ignore = true)
    Category toEntity(CategoryDto categoryDto);

    List<CategoryDto> toDtoList(List<Category> categories);

    @Mapping(target = "parentName", expression = "java(category.getParent() != null ? category.getParent().getName() : null)")
    CategoryResponse toResponseDto(Category category);

    List<CategoryResponse> toResponseDtoList(List<Category> categories);

    @Mapping(target = "parent", ignore = true)
    void updateCategoryFromDto(CategoryDto categoryDto, @MappingTarget Category category);
}
