package com.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto extends ChildBaseEntityDto{
    private String language;
    private String summary;
    private String coverImage;
    @NotBlank(message = "invalid.isbn")
    private String isbn;
    private Integer publicationYear;

    @Min(value = 1, message = "total.copies")
    private Integer totalCopies;

    @Min(value = 0, message = "available.copy")
    private Integer availableCopies;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
    @NotNull(message = "publisher.empty")
    private Long publisherId;
    @NotEmpty(message = "author.empty")
    private Set<Long> authorIds;
    @NotEmpty(message = "category.empty")
    private Set<Long> categoryIds;

    private List<BorrowTransactionDto> transactions;
}
