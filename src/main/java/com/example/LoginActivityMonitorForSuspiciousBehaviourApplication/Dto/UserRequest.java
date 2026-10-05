package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Dto;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Enum.Role;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private Role role;
}
