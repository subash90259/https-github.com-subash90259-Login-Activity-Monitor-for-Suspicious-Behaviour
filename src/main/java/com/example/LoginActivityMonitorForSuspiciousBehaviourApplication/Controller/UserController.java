package com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Dto.UserRequest;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Entity.User;
import com.example.LoginActivityMonitorForSuspiciousBehaviourApplication.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/RBAC")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String CreateUser(@RequestBody UserRequest request) {
        String response = userService.createUser(request);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(
            @Valid @RequestBody UserRequest request) {

        User user = userService.login(
                request.getUsername(),
                request.getPassword());

        return ResponseEntity.ok(user);
    }
}
