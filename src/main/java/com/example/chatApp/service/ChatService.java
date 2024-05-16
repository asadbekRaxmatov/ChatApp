package com.example.chatApp.service;

import com.example.chatApp.dto.ChatDTO;
import com.example.chatApp.dto.UserDTO;
import com.example.chatApp.exception.ResourceNotFoundException;
import com.example.chatApp.mapper.ChatMapper;
import com.example.chatApp.mapper.UserMapper;
import com.example.chatApp.model.Chat;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.ChatRepository;
import com.example.chatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    public ChatService(ChatMapper chatMapper, UserMapper userMapper) {
        this.chatMapper = chatMapper;
        this.userMapper = userMapper;
    }

    public ChatDTO createChat(ChatDTO chatDTO) {
        Chat chat = new Chat();
        chat.setName(chatDTO.getName());
        List<User> users = userRepository.findAllById(chatDTO.getUserIds());
        chat.setUsers(users);
        chat = chatRepository.save(chat);
        return chatMapper.toDto(chat);
    }

    public ChatDTO getChatById(Long id) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));
        return chatMapper.toDto(chat);
    }

    public List<ChatDTO> getAllChats() {
    List<Chat> chats = chatRepository.findAll();
        return chats.stream().map(chatMapper::toDto).collect(Collectors.toList());
    }

    public ChatDTO updateChat(Long id, ChatDTO chatDTO) {
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));

        chat.setName(chatDTO.getName());
        List<User> users = userRepository.findAllById(chatDTO.getUserIds());
        chat.setUsers(users);
        chat = chatRepository.save(chat);
        return chatMapper.toDto(chat);
    }

    public void deleteChat(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chat", "id", id));

        chatRepository.delete(chat);
    }

    public List<ChatDTO> getChatsByUserId(Long userId) {
        List<Chat> chats = chatRepository.findAllByUsers_Id(userId);
        return chats.stream().map(chatMapper::toDto).collect(Collectors.toList());
    }

    public ChatDTO getChatByName(String name) {
        Chat chat = chatRepository.findByName(name);
        return chatMapper.toDto(chat);
    }

    public List<ChatDTO> searchChatsByName(String nameFragment) {
        List<Chat> chats = chatRepository.findByNameContaining(nameFragment);
        return chats.stream().map(chatMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ChatDTO addUserToChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        chat.getUsers().add(user);
        chat = chatRepository.save(chat);

        return chatMapper.toDto(chat);
    }


    @Transactional
    public ChatDTO removeUserFromChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        chat.getUsers().remove(user);
        chat = chatRepository.save(chat);

        return chatMapper.toDto(chat);
    }

    public List<UserDTO> getUsersInChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        return chat.getUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ChatDTO updateChatName(Long chatId, String newName) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        chat.setName(newName);
        chat = chatRepository.save(chat);

        return chatMapper.toDto(chat);
    }

    public void removeChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ResourceNotFoundException("Chat", "id", chatId));
        chatRepository.delete(chat);
    }
}
