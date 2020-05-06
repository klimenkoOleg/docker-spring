package com.oklimenko.dockerspring.controller;

import com.oklimenko.dockerspring.dto.CreateUserRequest;
import com.oklimenko.dockerspring.dto.CreateUserResponse;
import com.oklimenko.dockerspring.dto.GetUserResponse;
import com.oklimenko.dockerspring.dto.HealthStatus;
import com.oklimenko.dockerspring.dto.UpdateUserRequest;
import com.oklimenko.dockerspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HealthController {

    private final UserService userService;

    @GetMapping("/health")
    public HealthStatus health() {
        return new HealthStatus("OK");
    }

    @PostMapping("/user")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(request));
    }

    @GetMapping("/db")
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userId));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity
                    .status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") Long userId,
                                             @RequestBody UpdateUserRequest request) {
        userService.updateUser(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK).build();
    }
}