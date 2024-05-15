package com.example.chatApp.service;

import com.example.chatApp.dto.ChatDTO;
import com.example.chatApp.exception.ResourceNotFoundException;
import com.example.chatApp.model.Chat;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.ChatRepository;
import com.example.chatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;


    public Chat createChat(ChatDTO chatDTO) {
        Chat chat = new Chat();
        chat.setName(chatDTO.getName());
        List<User> users = userRepository.findAllById(chatDTO.getUserIds());
        chat.setUsers(users);
        return chatRepository.save(chat);
    }

    public Chat getChatById(Long id) {
        return chatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat updateChat(Long id, ChatDTO chatDTO) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));

        chat.setName(chatDTO.getName());
        List<User> users = userRepository.findAllById(chatDTO.getUserIds());
        chat.setUsers(users);
        return chatRepository.save(chat);
    }

    public void deleteChat(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));

        chatRepository.delete(chat);
    }

    public List<Chat> getChatsByUserId(Long userId) {
        return chatRepository.findAllByUsers_Id(userId);
    }

    public Chat getChatByName(String name) {
        return chatRepository.findByName(name);
//                .orElseThrow(() -> new ResourceNotFoundException("Chat", "name", name));
    }

    public List<Chat> searchChatsByName(String nameFragment) {
        return chatRepository.findByNameContaining(nameFragment);
    }

    public Chat addUserToChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        chat.getUsers().add(user);
        return chatRepository.save(chat);
    }

    public Chat removeUserFromChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        chat.getUsers().remove(user);
        return chatRepository.save(chat);
    }

    public List<User> getUsersInChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));

        return chat.getUsers();
    }

    public Chat updateChatName(Long chatId, String newName) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        chat.setName(newName);
        return chatRepository.save(chat);
    }

    public void removeChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        chatRepository.delete(chat);
    }
}
