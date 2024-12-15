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
        try {
            String token = userService.authenticate(request.getUserName(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse<>(true, token, "AUTH TOKEN GENERATED", HttpStatus.CREATED));
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, null, "AUTH TOKEN NOT GENERATED :: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
}
