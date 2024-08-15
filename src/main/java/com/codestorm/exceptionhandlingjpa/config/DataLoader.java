package com.codestorm.exceptionhandlingjpa.config;

import com.codestorm.exceptionhandlingjpa.dao.Users;
import com.codestorm.exceptionhandlingjpa.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        Users user1 = Users.builder()
                .username("Charles")
                .email("charles@gmail.com")
                .build();

        Users user2 = Users.builder()
                .username("Carlos")
                .email("carlos@gmail.com")
                .build();

        userRepository.saveAll(List.of(user1, user2));
    }
}
