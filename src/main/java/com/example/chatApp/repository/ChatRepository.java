package com.example.chatApp.repository;

import com.example.chatApp.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByUsers_Id(Long userId);

    Chat findByName(String name);

    List<Chat> findByNameContaining(String nameFragment);
}
