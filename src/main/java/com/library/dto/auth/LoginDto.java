package com.library.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginDto {

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
}
