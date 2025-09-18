package com.library.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.dto.BaseEntityDTO;
import com.library.enumration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseEntityDTO {
    @NotEmpty(message = "name.empty")
    private String firstName;
    private String lastName;
    @Email(message ="invalid.email")
    @NotBlank(message = "invalid.email")
    private String email;

    @NotBlank(message = "password.invalid")
    @Size(min = 8,message = "password.invalid")
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
//            message = "password.invalid"
//    )

    private String password;
    private Role role;


}
