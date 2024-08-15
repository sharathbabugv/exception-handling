package com.codestorm.exceptionhandlingjpa.service;

import com.codestorm.exceptionhandlingjpa.dto.UserDto;
import com.codestorm.exceptionhandlingjpa.repository.UserRepository;
import com.codestorm.exceptionhandlingjpa.utils.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserDto(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::userToUserDTO)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
