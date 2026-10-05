package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.SuspiciousActivity;

public interface SuspiciousActivityRepository extends JpaRepository<SuspiciousActivity, Long> {

    boolean existsByIpAddressAndReasonAndTimestampAfter(
            String ipAddress,
            String reason,
            LocalDateTime timestamp);
}
