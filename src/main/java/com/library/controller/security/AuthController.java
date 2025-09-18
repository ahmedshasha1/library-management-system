package com.library.controller.security;

import com.library.dto.auth.LoginDto;
import com.library.dto.auth.TokenDto;
import com.library.dto.auth.UserDto;
import com.library.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody @Validated UserDto userDto) {
        authService.signUp(userDto);
        return ResponseEntity.created(URI.create("/create-user")).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

}
