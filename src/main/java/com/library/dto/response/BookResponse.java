package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.dto.ChildBaseEntityDto;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse extends ChildBaseEntityDto {
    private String language;
    private String summary;
    private String coverImage;
    private String isbn;
    private Integer publicationYear;
    private Integer totalCopies;
    private Integer availableCopies;

    private Set<String> authors;
    private String publisher;
    private Set<String> categories;
}
