package com.library.service.security;

import com.library.config.security.JwtTokenHandler;
import com.library.controller.vm.UserResponseVM;
import com.library.dao.auth.UserRepo;
import com.library.dto.auth.UserDto;
import com.library.enumration.Role;
import com.library.mapper.auth.UserMapper;
import com.library.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtTokenHandler jwtTokenHandler;
    @Override
    public UserResponseVM getUserByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new RuntimeException("invalid.data");
        }
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user.not.found"));
        return UserMapper.mapper.toResponse(user);
    }

    @Override
    public UserResponseVM updateUser(UserDto userDto) {
        if (userDto == null) throw new RuntimeException("invalid.data");
        if (Objects.isNull(userDto.getId())) throw new RuntimeException("id.empty");

        User user = userRepo.findById(userDto.getId())
                .orElseThrow(()->new RuntimeException("user.not.found"));
        UserMapper.mapper.updateFromDto(userDto,user);


        return UserMapper.mapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        User user =userRepo.findById(id)
                .orElseThrow(()->new RuntimeException("user.not.found"));
        userRepo.delete(user);
    }

    @Override
    public UserResponseVM getUserById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.empty");
        }
        User user =userRepo.findById(id)
                .orElseThrow(()->new RuntimeException("user.not.found"));


        return UserMapper.mapper.toResponse(user);
    }

    @Override
    public List<UserResponseVM> getUsersByRole(Role role) {
        if (Objects.isNull(role)) {
            throw new RuntimeException("invalid.data");
        }
        List<User> user = userRepo.findByRole(role);
        return UserMapper.mapper.toResponseList(user);
    }

    @Override
    public List<UserResponseVM> getUsersByName(String name) {
        if (Objects.isNull(name)) {
            throw new RuntimeException("invalid.data");
        }
        List<User> users = userRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name);
        return UserMapper.mapper.toResponseList(users);
    }

    @Override
    public User checkClientExistByToken(String token) throws RuntimeException {
        String email = jwtTokenHandler.getSubject(token);

        if (Objects.isNull(email)){
            throw new RuntimeException("user.not.found");
        }
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user.not.found"));

        UserResponseVM userResponseVM =new UserResponseVM();
        userResponseVM.setEmail(user.getEmail());
        userResponseVM.setFirstName(user.getFirstName());
        userResponseVM.setLastName(user.getLastName());
        userResponseVM.setRole(user.getRole());
        return user;
    }

    @Override
    public List<UserResponseVM> getAllUsers() {
//        List<User>users = userRepo.findAll();
//        List<UserResponseVM> userResponseVMS = new ArrayList<>();
//        for (int i = 0; i < users.size(); i++) {
//            UserResponseVM userResponseVM =new UserResponseVM();
//            userResponseVM.setEmail(users.get(i).getEmail());
//            userResponseVM.setFirstName(users.get(i).getFirstName());
//            userResponseVM.setLastName(users.get(i).getLastName());
//            userResponseVM.setRole(users.get(i).getRole());
//            userResponseVMS.add(userResponseVM);
//        }


        return UserMapper.mapper.toResponseList(userRepo.findAll());
    }
}
