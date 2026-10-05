package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Dto;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Enum.LoginStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "IP address is required")
    private String ipAddress;

    @NotNull(message = "Status is required")
    private LoginStatus status;

}
