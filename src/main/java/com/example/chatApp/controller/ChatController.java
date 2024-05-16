package com.example.chatApp.controller;


import com.example.chatApp.dto.ChatDTO;
import com.example.chatApp.dto.UserDTO;
import com.example.chatApp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/add")
    public ResponseEntity<ChatDTO> addChat(@RequestBody ChatDTO chatDTO) {
        ChatDTO createdChat = chatService.createChat(chatDTO);
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> getChatById(@PathVariable Long id) {
        ChatDTO chat = chatService.getChatById(id);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatDTO>> getAllChats() {
        List<ChatDTO> chats = chatService.getAllChats();
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChatDTO> updateChat(@PathVariable Long id, @RequestBody ChatDTO chatDTO) {
        ChatDTO updatedChat = chatService.updateChat(id, chatDTO);
        return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDTO>> getChatsByUserId(@PathVariable Long userId) {
        List<ChatDTO> chats = chatService.getChatsByUserId(userId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ChatDTO> getChatByName(@PathVariable String name) {
        ChatDTO chat = chatService.getChatByName(name);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/search/{nameFragment}")
    public ResponseEntity<List<ChatDTO>> searchChatsByName(@PathVariable String nameFragment) {
        List<ChatDTO> chats = chatService.searchChatsByName(nameFragment);
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/{chatId}/addUser/{userId}")
    public ResponseEntity<ChatDTO> addUserToChat(@PathVariable Long chatId, @PathVariable Long userId) {
        ChatDTO updatedChat = chatService.addUserToChat(chatId, userId);
        return ResponseEntity.ok(updatedChat);
    }

    @PutMapping("/{chatId}/removeUser/{userId}")
    public ResponseEntity<ChatDTO> removeUserFromChat(@PathVariable Long chatId, @PathVariable Long userId) {
        ChatDTO updatedChat = chatService.removeUserFromChat(chatId, userId);
        return ResponseEntity.ok(updatedChat);
    }

    @GetMapping("/{chatId}/users")
    public ResponseEntity<List<UserDTO>> getUsersInChat(@PathVariable Long chatId) {
        List<UserDTO> users = chatService.getUsersInChat(chatId);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{chatId}/updateName")
    public ResponseEntity<ChatDTO> updateChatName(@PathVariable Long chatId, @RequestParam String newName) {
        ChatDTO updatedChat = chatService.updateChatName(chatId, newName);
        return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/{chatId}/remove")
    public ResponseEntity<Void> removeChat(@PathVariable Long chatId) {
        chatService.removeChat(chatId);
        return ResponseEntity.noContent().build();
    }

}
