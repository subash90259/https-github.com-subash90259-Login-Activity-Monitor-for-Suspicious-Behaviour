package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.LoginAttempt;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Enum.LoginStatus;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    List<LoginAttempt> findByUsernameAndStatusAndTimestampAfter(String username, LoginStatus status,
            LocalDateTime timestamp);

    List<LoginAttempt> findByIpAddressAndStatusAndTimestampAfter(String ipAddress, LoginStatus status,
            LocalDateTime timestamp);

    List<LoginAttempt> findByUsername(String username);

    List<LoginAttempt> findByIpAddress(String ipAddress);

    List<LoginAttempt> findByStatus(LoginStatus status);
}
