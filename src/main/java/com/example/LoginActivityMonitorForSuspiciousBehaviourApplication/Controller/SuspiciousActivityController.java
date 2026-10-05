package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Controller;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.SuspiciousActivity;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository.SuspiciousActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suspicious")
public class SuspiciousActivityController {

    private final SuspiciousActivityRepository suspiciousActivityRepository;

    @Autowired
    public SuspiciousActivityController(
            SuspiciousActivityRepository suspiciousActivityRepository) {

        this.suspiciousActivityRepository = suspiciousActivityRepository;
    }

    @GetMapping
    public ResponseEntity<List<SuspiciousActivity>> getSuspiciousActivities() {

        List<SuspiciousActivity> suspiciousActivities = suspiciousActivityRepository.findAll();

        return ResponseEntity.ok(suspiciousActivities);
    }
}