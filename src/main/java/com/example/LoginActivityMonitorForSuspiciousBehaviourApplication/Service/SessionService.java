package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.Session;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    // Session timeout = 30 minutes
    private static final long SESSION_TIMEOUT_MINUTES = 30;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // Create new session
    public Session createSession(String username) {

        String sessionId = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now();

        Session session = new Session();

        session.setUsername(username);
        session.setSessionId(sessionId);
        session.setCreatedAt(now);
        session.setLastActivity(now);
        session.setActive(true);

        return sessionRepository.save(session);
    }

    // Validate session
    public boolean validateSession(String sessionId) {

        Session session = sessionRepository
                .findBySessionId(sessionId)
                .orElse(null);

        // Session not found
        if (session == null) {
            return false;
        }

        // Session already inactive
        if (!session.isActive()) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        // Check inactivity timeout
        if (session.getLastActivity()
                .plusMinutes(SESSION_TIMEOUT_MINUTES)
                .isBefore(now)) {

            session.setActive(false);

            sessionRepository.save(session);

            return false;
        }

        // Update last activity
        session.setLastActivity(now);

        sessionRepository.save(session);

        return true;
    }

    // Get all active sessions
    public List<Session> getActiveSessions() {

        return sessionRepository.findByActiveTrue();
    }
}