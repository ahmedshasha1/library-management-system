package com.library.mapper;

import com.library.dto.BookDto;
import com.library.dto.response.BookResponse;
import com.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(imports = {java.util.HashSet.class})
public interface BookMapper {
    BookMapper mapper= Mappers.getMapper(BookMapper.class);

    BookDto toDto(Book book);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toEntity(BookDto dto);

    List<BookDto> toDtoList(List<Book> books);
    @Mapping(target = "authors", expression = "java(new HashSet<>(book.getAuthors().stream().map(a -> a.getName()).toList()))")
    @Mapping(target = "publisher", expression = "java(book.getPublisher() != null ? book.getPublisher().getName() : null)")
    @Mapping(target = "categories", expression = "java(new HashSet<>(book.getCategories().stream().map(c -> c.getName()).toList()))")
    BookResponse toResponseDto(Book book);

    List<BookResponse> toResponseDtoList(List<Book> books);
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateBookFromDto(BookDto bookDto, @MappingTarget Book book);
}
