package com.library.service.security;

import com.library.config.security.JwtTokenHandler;
import com.library.dao.auth.UserRepo;
import com.library.dto.auth.LoginDto;
import com.library.dto.auth.TokenDto;
import com.library.dto.auth.UserDto;
import com.library.enumration.Role;
import com.library.mapper.auth.UserMapper;
import com.library.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenHandler jwtTokenHandler;
    @Override
    public TokenDto login(LoginDto loginDto) {
        if (loginDto == null) throw new RuntimeException("invalid.data");
        User user = userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(()->new RuntimeException("user.not.found"));
        String role = user.getRole().toString();
        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new RuntimeException("error.pass");
        }

        return new TokenDto(
                jwtTokenHandler.createToken(user),
                role
        );
    }

    @Override
    public void signUp(UserDto userDto) {
        if (userDto == null) throw new RuntimeException("invalid.data");
        if (Objects.nonNull(userDto.getId())) throw new RuntimeException("id.nonnull");
        if(userRepo.findByEmail(userDto.getEmail()).isPresent() ){
            throw new RuntimeException("user.exist");
        }
        User savedUser=UserMapper.mapper.toEntity(userDto);
        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        savedUser.setRole(Role.STAFF);
        userRepo.save(savedUser);
    }
}
