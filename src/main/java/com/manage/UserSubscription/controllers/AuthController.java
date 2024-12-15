package com.manage.UserSubscription.controllers;

import com.manage.UserSubscription.ApiResponse;
import com.manage.UserSubscription.dto.LoginRequest;
import com.manage.UserSubscription.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity <ApiResponse<String>> authenticateUser(@RequestBody LoginRequest request) {
        String token = userService.authenticate(request.getUserName(), request.getPassword());
        if (token != null)
            return ResponseEntity.ok(new ApiResponse<>(true, token, "AUTH TOKEN GENERATED", HttpStatus.CREATED));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, token, "AUTH TOKEN COULD NOT BE GENERATED", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
