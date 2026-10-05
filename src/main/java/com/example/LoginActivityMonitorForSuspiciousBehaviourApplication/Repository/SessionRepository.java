package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Repository;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findBySessionId(String sessionId);

    List<Session> findByActiveTrue();

    List<Session> findByUsernameAndActiveTrue(String username);
}