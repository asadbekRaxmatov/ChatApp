package com.example.chatApp.repository;

import com.example.chatApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatId(Long chatId);

    List<Message> findAllByAuthor_Id(Long userId);
    
    List<Message> findByChatId(Long chatId);

}