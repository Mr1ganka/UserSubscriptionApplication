package com.manage.UserSubscription.controllers;

import com.manage.UserSubscription.ApiResponse;
import com.manage.UserSubscription.entities.User;
import com.manage.UserSubscription.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, newUser, "CREATED", HttpStatus.CREATED));
    }

    @GetMapping()
    public ResponseEntity <ApiResponse<List<User>>> fetchAll() {
        List<User> users = userService.getAllUsers();

        if(users.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, users, "NO USERS TO DISPLAY", HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse<>(true, users, "FETCHED", HttpStatus.FOUND));
    }

    @GetMapping("/{userId}")
    public ResponseEntity <ApiResponse<User>> getUser(@PathVariable Long userId, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }

        User user = userService.getUserById(userId);

        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, user, "USER NOT FOUND FOR ID: "+userId, HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse<>(true, user, "FETCHED", HttpStatus.FOUND));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity <ApiResponse<User>> deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }

        User user = userService.deleteUserById(userId);

        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, user, "USER NOT FOUND FOR ID: "+userId, HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse<>(true, user, "DELETED", HttpStatus.OK));
    }

    @PutMapping("/{userId}")
    public ResponseEntity <ApiResponse<User>> updateUser(@PathVariable Long userId, @RequestBody User user, HttpServletRequest request) {
        String token = request.getAttribute("userId").toString();
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "JWT TOKEN NOT FOUND", HttpStatus.FORBIDDEN));

        if (!token.equals(String.valueOf(user.getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, null, "USER ID DOES NOT MATCH WITH THE TOKEN", HttpStatus.FORBIDDEN));
        }

        User fetchedUser = userService.updateUserById(userId, user);

        if (fetchedUser == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, fetchedUser, "USER NOT FOUND FOR ID: "+userId, HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new ApiResponse<>(true, fetchedUser, "USER UPDATED SUCCESSFULLY", HttpStatus.OK));
    }
}