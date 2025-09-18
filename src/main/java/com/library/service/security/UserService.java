package com.library.service.security;

import com.library.controller.vm.UserResponseVM;
import com.library.dto.auth.UserDto;
import com.library.enumration.Role;
import com.library.model.auth.User;

import java.util.List;

public interface UserService {

    UserResponseVM getUserByEmail(String email);
    UserResponseVM updateUser(UserDto userDto);
    void deleteUser(Long id);
    UserResponseVM getUserById(Long id);
    List<UserResponseVM> getUsersByRole(Role role);
    List<UserResponseVM> getUsersByName(String name);

    User checkClientExistByToken(String token) throws RuntimeException;

    List<UserResponseVM> getAllUsers();
}
