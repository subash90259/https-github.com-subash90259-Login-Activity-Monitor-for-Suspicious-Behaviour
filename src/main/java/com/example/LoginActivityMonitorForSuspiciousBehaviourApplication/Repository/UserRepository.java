package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
