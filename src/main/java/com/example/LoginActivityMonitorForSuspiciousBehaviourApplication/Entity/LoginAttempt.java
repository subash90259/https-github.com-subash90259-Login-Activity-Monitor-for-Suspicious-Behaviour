package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.validation.annotation.Validated;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Enum.LoginStatus;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Validated
@Table(name = "login_attempts")
@Data
public class LoginAttempt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  @Column(name = "ip_address")
  private String ipAddress;
  private LocalDateTime timestamp;

  @Enumerated(EnumType.STRING)
  private LoginStatus status;

}
