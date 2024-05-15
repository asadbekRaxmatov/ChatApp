package com.example.chatApp.repository;


import com.example.chatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String userName);

    Optional<User> findById(Long id);

    boolean existsByUsername(String username);

    List<User> findAllByCreatedAt(LocalDateTime createdAt);

    List<User> findByUsernameContaining(String usernameFragment);
}

