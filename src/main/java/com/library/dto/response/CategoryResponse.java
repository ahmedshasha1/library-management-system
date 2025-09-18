package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.dto.ChildBaseEntityDto;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse extends ChildBaseEntityDto {
    private String parentName;
}
