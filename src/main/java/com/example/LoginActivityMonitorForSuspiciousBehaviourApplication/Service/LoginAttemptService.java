package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Dto.LoginRequest;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.LoginAttempt;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.SuspiciousActivity;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Enum.LoginStatus;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository.LoginAttemptRepository;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository.SuspiciousActivityRepository;

@Service
public class LoginAttemptService {

        private final LoginAttemptRepository loginAttemptRepository;

        private final SuspiciousActivityRepository suspiciousActivityRepository;

        @Autowired
        public LoginAttemptService(
                        LoginAttemptRepository loginAttemptRepository,
                        SuspiciousActivityRepository suspiciousActivityRepository) {

                this.loginAttemptRepository = loginAttemptRepository;
                this.suspiciousActivityRepository = suspiciousActivityRepository;
        }

        public String recordLoginAttempt(LoginRequest request) {

                LocalDateTime now = LocalDateTime.now();

                LoginAttempt loginAttempt = new LoginAttempt();

                loginAttempt.setUsername(request.getUsername());
                loginAttempt.setIpAddress(request.getIpAddress());
                loginAttempt.setTimestamp(now);
                loginAttempt.setStatus(request.getStatus());

                loginAttemptRepository.save(loginAttempt);

                suspiciousActivity(request, now);

                return "Login attempt recorded successfully";
        }

        public void suspiciousActivity(
                        LoginRequest request,
                        LocalDateTime now) {

                LocalDateTime tenMinutesAgo = now.minusMinutes(10);

                if (request.getStatus() == LoginStatus.SUCCESS) {

                        List<LoginAttempt> recentFailures = loginAttemptRepository
                                        .findByUsernameAndStatusAndTimestampAfter(
                                                        request.getUsername(),
                                                        LoginStatus.FAILURE,
                                                        tenMinutesAgo);

                        if (recentFailures.size() >= 3) {

                                String reason = "Successful login immediately following multiple failures";

                                boolean alreadyExists = suspiciousActivityRepository
                                                .existsByIpAddressAndReasonAndTimestampAfter(
                                                                request.getIpAddress(),
                                                                reason,
                                                                tenMinutesAgo);

                                if (!alreadyExists) {

                                        saveSuspiciousActivity(
                                                        request,
                                                        reason,
                                                        now);
                                }
                        }

                        return;
                }

                if (request.getStatus() == LoginStatus.FAILURE) {

                        List<LoginAttempt> ipFailures = loginAttemptRepository
                                        .findByIpAddressAndStatusAndTimestampAfter(
                                                        request.getIpAddress(),
                                                        LoginStatus.FAILURE,
                                                        tenMinutesAgo);

                        if (ipFailures.size() >= 5) {

                                String reason = "5 failed logins from the same IP in 10 minutes";

                                boolean alreadyExists = suspiciousActivityRepository
                                                .existsByIpAddressAndReasonAndTimestampAfter(
                                                                request.getIpAddress(),
                                                                reason,
                                                                tenMinutesAgo);

                                if (!alreadyExists) {

                                        saveSuspiciousActivity(
                                                        request,
                                                        reason,
                                                        now);
                                }
                        }

                        List<LoginAttempt> userFailures = loginAttemptRepository
                                        .findByUsernameAndStatusAndTimestampAfter(
                                                        request.getUsername(),
                                                        LoginStatus.FAILURE,
                                                        tenMinutesAgo);

                        if (userFailures.size() >= 5) {

                                String reason = "5 failed logins for the same user in 10 minutes";

                                boolean alreadyExists = suspiciousActivityRepository
                                                .existsByIpAddressAndReasonAndTimestampAfter(
                                                                request.getIpAddress(),
                                                                reason,
                                                                tenMinutesAgo);

                                if (!alreadyExists) {

                                        saveSuspiciousActivity(
                                                        request,
                                                        reason,
                                                        now);
                                }
                        }
                }
        }

        private void saveSuspiciousActivity(
                        LoginRequest request,
                        String reason,
                        LocalDateTime now) {

                SuspiciousActivity suspiciousActivity = new SuspiciousActivity();

                suspiciousActivity.setIpAddress(
                                request.getIpAddress());

                suspiciousActivity.setUsername(
                                request.getUsername());

                suspiciousActivity.setReason(reason);

                suspiciousActivity.setTimestamp(now);

                suspiciousActivityRepository.save(
                                suspiciousActivity);
        }
}