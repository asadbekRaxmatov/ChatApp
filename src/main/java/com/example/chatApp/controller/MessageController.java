package com.example.chatApp.controller;

import com.example.chatApp.dto.MessageDTO;
import com.example.chatApp.model.Message;
import com.example.chatApp.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Log4j2
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMessage(@RequestBody MessageDTO messageDTO) {
        Message newMessage = messageService.createMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMessage);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        log.debug("Get by id "+ id);

        Message usersList = messageService.getMessagesById(id);
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long chatId) {
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/messages/chat/{chatId}")
    public ResponseEntity<List<Message>> getAllMessagesByChatId(@PathVariable Long chatId) {
        List<Message> messages = messageService.getAllMessagesByChatId(chatId);
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/messages/user/{userId}")
    public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable Long userId) {
        List<Message> messages = messageService.getAllMessagesByUserId(userId);
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok().body(messages);
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Message> updateMessageContent(@PathVariable Long messageId, @RequestBody String newContent) {
        Message updatedMessage = messageService.updateMessageContent(messageId, newContent);
        return ResponseEntity.ok(updatedMessage);
    }

    @PutMapping("/api/messages/{messageId}/content")
    public ResponseEntity<Message> updateMessageContentById(@PathVariable Long messageId, @RequestBody String newContent) {
        Message updatedMessage = messageService.updateMessageContent(messageId, newContent);
        return ResponseEntity.ok(updatedMessage);
    }

    @DeleteMapping("/{messageId}/remove")
    public ResponseEntity<Void> removeMessage(@PathVariable Long messageId) {
        messageService.removeMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}

