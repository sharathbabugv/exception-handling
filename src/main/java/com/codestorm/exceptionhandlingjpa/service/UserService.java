package com.codestorm.exceptionhandlingjpa.service;

import com.codestorm.exceptionhandlingjpa.dao.Users;
import com.codestorm.exceptionhandlingjpa.dto.UserDto;
import com.codestorm.exceptionhandlingjpa.exceptions.UnableToProcessException;
import com.codestorm.exceptionhandlingjpa.exceptions.UserNotFoundException;
import com.codestorm.exceptionhandlingjpa.repository.UserRepository;
import com.codestorm.exceptionhandlingjpa.utils.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto) {
        log.info("createUser: {}", userDto);
        if (userDto.id() != null) {
            throw new UnableToProcessException("User ID must be null while creating the user!");
        }

        Users users = UserMapper.userDTOToUser(userDto);
        users.setInternalUserId(UUID.randomUUID().toString());
        Users savedUser = userRepository.save(users);
        return UserMapper.userToUserDTO(savedUser);
    }

    public UserDto getUser(Long id) {
        log.info("getUser: {}", id);
        if (id == null || id == 0L) {
            log.error("Id is invalid: {}", id);
            throw new UnableToProcessException("ID must not be null");
        }
        return userRepository.findById(id)
                .map(UserMapper::userToUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public UserDto updateUser(UserDto userDto) {
        log.info("updateUser method entered");
        if (userDto.id() == null) {
            log.error("User ID must not be null");
            throw new UnableToProcessException("User ID must not be null while updating the user!");
        }
        Users users = UserMapper.userDTOToUser(userDto);
        Users updatedUser = userRepository.save(users);
        return UserMapper.userToUserDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        log.info("deleteUser: {}", id);
        if (id == null || id == 0L) {
            log.error("ID must not be null");
            throw new UnableToProcessException("ID must not be null");
        }
        userRepository.findById(id)
                .ifPresentOrElse(user -> userRepository.deleteById(user.getId()), () -> {
                    throw new UserNotFoundException("User not found!");
                });
    }
}
