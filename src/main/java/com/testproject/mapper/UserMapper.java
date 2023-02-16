package com.testproject.mapper;

import com.testproject.dto.UserDto;
import com.testproject.dto.UserRegistrationDto;
import com.testproject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    UserRegistrationDto toRegistrationDto(User user);
}
