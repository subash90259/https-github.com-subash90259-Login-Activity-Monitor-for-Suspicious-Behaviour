package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Controller;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Dto.LoginRequest;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.LoginAttempt;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Exception.RateLimitExceededException;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository.LoginAttemptRepository;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service.LoginAttemptService;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service.RateLimiterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginAttemptController {

    private final LoginAttemptService loginAttemptService;
    private final LoginAttemptRepository loginAttemptRepository;
    private final RateLimiterService rateLimiterService;

    @Autowired
    public LoginAttemptController(LoginAttemptService loginAttemptService,
            LoginAttemptRepository loginAttemptRepository,
            RateLimiterService rateLimiterService) {
        this.loginAttemptService = loginAttemptService;
        this.loginAttemptRepository = loginAttemptRepository;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping
    public ResponseEntity<String> recordLoginAttempt(@Valid @RequestBody LoginRequest request) {

        boolean allowed = rateLimiterService.isAllowed(request.getIpAddress());

        if (!allowed) {
            throw new RateLimitExceededException(
                    "Too many login attempts. Please try again later.");
        }

        String response = loginAttemptService.recordLoginAttempt(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LoginAttempt>> getLoginAttempts() {
        List<LoginAttempt> loginAttempts = loginAttemptRepository.findAll();
        return ResponseEntity.ok(loginAttempts);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<LoginAttempt>> getByUsername(
            @PathVariable String username) {

        List<LoginAttempt> loginAttempts = loginAttemptRepository.findByUsername(username);

        return ResponseEntity.ok(loginAttempts);
    }

    @GetMapping("/ip/{ipAddress}")
    public ResponseEntity<List<LoginAttempt>> getByIpAddress(
            @PathVariable String ipAddress) {

        List<LoginAttempt> loginAttempts = loginAttemptRepository.findByIpAddress(ipAddress);

        return ResponseEntity.ok(loginAttempts);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LoginAttempt>> getByStatus(
            @PathVariable com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Enum.LoginStatus status) {

        List<LoginAttempt> loginAttempts = loginAttemptRepository.findByStatus(status);

        return ResponseEntity.ok(loginAttempts);
    }
}