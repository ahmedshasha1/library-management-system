package com.library.mapper;

import com.library.dto.AuthorDto;
import com.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper mapper= Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(Author author);
    Author toEntity(AuthorDto authorDto);
    List<AuthorDto> toDtoList(List<Author> authors);
    void updateFromDto(AuthorDto authorDto, @MappingTarget Author author);

}
