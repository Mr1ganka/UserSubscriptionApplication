package com.manage.UserSubscription.services;

import com.manage.UserSubscription.entities.Plan;
import com.manage.UserSubscription.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User deleteUserById (Long id);

    User updateUserById(Long id, User user);

    String authenticate(String username, String password);
}
