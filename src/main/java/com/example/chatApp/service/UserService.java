package com.example.chatApp.service;


import com.example.chatApp.exception.ResourceNotFoundException;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    //redis
    @Cacheable(value = "user", key = "#id")
    public User getUsersById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setUsername(userDetails.getUsername());
        user.setCreatedAt(userDetails.getCreatedAt());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public List<User> getUsersByCreatedAt(LocalDateTime createdAt) {
        return userRepository.findAllByCreatedAt(createdAt);
    }

    public User updateUsername(Long id, String newUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public List<User> searchUsersByUsername(String usernameFragment) {
        return userRepository.findByUsernameContaining(usernameFragment);
    }

}

