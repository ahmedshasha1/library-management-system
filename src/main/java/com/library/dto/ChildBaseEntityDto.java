package com.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChildBaseEntityDto extends BaseEntityDTO {
    @NotEmpty(message = "name.empty")
    private String name;
}
