package com.manage.UserSubscription.services.impl;

import com.manage.UserSubscription.entities.User;
import com.manage.UserSubscription.repositories.UserRepository;
import com.manage.UserSubscription.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
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
        if (user != null)
            userRepository.deleteById(id);

        return user;
    }

    @Override
    public User updateUserById(Long id, User user) {
        User fetchedUser = getUserById(id);

        if (fetchedUser != null) {
            fetchedUser.setUserName(user.getUserName());
            fetchedUser.setPassword(user.getPassword());
            userRepository.save(fetchedUser);
        }

        return fetchedUser;
    }

}
