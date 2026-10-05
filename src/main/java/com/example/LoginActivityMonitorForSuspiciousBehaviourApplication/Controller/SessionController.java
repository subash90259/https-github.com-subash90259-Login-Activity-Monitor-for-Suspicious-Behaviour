package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Controller;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.Session;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<Session> createSession(
            @RequestBody String username) {

        Session session = sessionService.createSession(username);

        return ResponseEntity.ok(session);
    }

    @GetMapping("/validate/{sessionId}")
    public ResponseEntity<String> validateSession(
            @PathVariable String sessionId) {

        boolean valid = sessionService.validateSession(sessionId);

        if (!valid) {
            return ResponseEntity.status(401)
                    .body("Session expired or invalid");
        }

        return ResponseEntity.ok("Session is active");
    }

    @GetMapping("/active")
    public ResponseEntity<List<Session>> getActiveSessions() {

        List<Session> sessions = sessionService.getActiveSessions();

        return ResponseEntity.ok(sessions);
    }
}  