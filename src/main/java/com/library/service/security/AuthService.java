package com.library.service.security;

import com.library.dto.auth.LoginDto;
import com.library.dto.auth.TokenDto;
import com.library.dto.auth.UserDto;

public interface AuthService {

    TokenDto login(LoginDto loginDto);
    void signUp(UserDto userDto);
}
