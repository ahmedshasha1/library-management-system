package com.library.mapper.auth;

import com.library.controller.vm.UserResponseVM;
import com.library.dto.auth.UserDto;
import com.library.model.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper mapper= Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDtoList(List<User> users);

    UserResponseVM toResponse(User user);
    List<UserResponseVM> toResponseList(List<User> users);

    void updateFromDto(UserDto userDto, @MappingTarget User user);
}
