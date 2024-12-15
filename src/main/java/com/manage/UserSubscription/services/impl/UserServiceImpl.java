package com.manage.UserSubscription.services.impl;

import com.manage.UserSubscription.entities.User;
import com.manage.UserSubscription.repositories.UserRepository;
import com.manage.UserSubscription.services.UserService;
import com.manage.UserSubscription.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String authenticate(String username, String password) {
        User user = userRepository.findByuserName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(username, user.getId().toString());
        }
        throw new RuntimeException("Invalid credentials");
    }


    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public User deleteUserById(Long id) {
        User user = getUserById(id);
        if (user == null)
            return null;
        userRepository.deleteById(id);

        return hidePassword(user);
    }

    @Override
    public User updateUserById(Long id, User user) {
        User fetchedUser = getUserById(id);

        if (fetchedUser == null)
            return null;

        fetchedUser.setUserName(user.getUserName());
        fetchedUser.setPassword(user.getPassword());
        userRepository.save(fetchedUser);

        return hidePassword(fetchedUser);
    }

    private User hidePassword(User user) {
        if (user == null)
            return null;
        user.setPassword("*");
        return user;
    }

}
