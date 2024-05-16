package com.example.chatApp.service;

import com.example.chatApp.dto.MessageDTO;
import com.example.chatApp.exception.ResourceNotFoundException;
import com.example.chatApp.mapper.MessageMapper;
import com.example.chatApp.model.Chat;
import com.example.chatApp.model.Message;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.ChatRepository;
import com.example.chatApp.repository.MessageRepository;
import com.example.chatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    private final MessageMapper messageMapper;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        User author = userRepository.getById(messageDTO.getAuthorId());
        Chat chat = chatRepository.getById(messageDTO.getChatId());

        message.setChat(chat);
        message.setAuthor(author);
        message.setContentType(messageDTO.getContentType());
        message.setContent(messageDTO.getContent());

        message = messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    //redis
    @Cacheable(value = "user", key = "#id")
    public MessageDTO getMessagesById(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Message", "id", id));
        return messageMapper.toDto(message);
    }

    public MessageDTO getMessageById(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        return messageMapper.toDto(message);
    }

    public List<MessageDTO> getAllMessagesByChatId(Long chatId) {
        List<Message> messages = messageRepository.findAllByChatId(chatId);
        return messages.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    public List<MessageDTO> getAllMessagesByUserId(Long userId) {
        List<Message> messages = messageRepository.findAllByAuthor_Id(userId);
        return messages.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    public List<MessageDTO> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    public MessageDTO updateMessage(MessageDTO messageDTO, Long messageId) {
        Message existingMessage = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        User author = userRepository.getById(messageDTO.getAuthorId());
        Chat chat = chatRepository.getById(messageDTO.getChatId());

        existingMessage.setChat(chat);
        existingMessage.setAuthor(author);
        existingMessage.setContentType(messageDTO.getContentType());
        existingMessage.setContent(messageDTO.getContent());

        Message updatedMessage = messageRepository.save(existingMessage);
        return messageMapper.toDto(updatedMessage); // Map the updated Message to MessageDTO
    }

    public MessageDTO updateMessageContent(Long messageId, String newContent) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        message.setContent(newContent);
        Message updatedMessage = messageRepository.save(message);
        return messageMapper.toDto(updatedMessage);
    }

    public void removeMessage(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        messageRepository.delete(message);
    }

    public List<MessageDTO> getMessagesByChatId(Long chatId) {
        List<Message> messages = messageRepository.findByChatId(chatId);
        return messages.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

}
