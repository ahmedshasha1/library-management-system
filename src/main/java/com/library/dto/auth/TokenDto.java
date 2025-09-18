package com.library.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class TokenDto {
        private String token;
        private String role;

}
