package com.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto extends ChildBaseEntityDto {

    @Email(message ="invalid.email")
    @NotBlank(message = "invalid.email")
    private String email;
    @NotBlank(message = "phone.invalid")
    @Pattern(
            regexp = "^(010|011|012|015)[0-9]{8}$",
            message = "phone.invalid"
    )
    private String phoneNumber;

    @JsonProperty("membershipDate")
    private LocalDateTime membershipDate;
    private boolean active;

}
