package com.codestorm.exceptionhandlingjpa.repository;

import com.codestorm.exceptionhandlingjpa.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
