package com.codestorm.exceptionhandlingjpa.utils;

import com.codestorm.exceptionhandlingjpa.dao.Users;
import com.codestorm.exceptionhandlingjpa.dto.UserDto;

public class UserMapper {

    public static UserDto userToUserDTO(Users user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
