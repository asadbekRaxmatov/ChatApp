package com.example.chatApp.service;

import com.example.chatApp.dto.MessageDTO;
import com.example.chatApp.exception.ResourceNotFoundException;
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

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(MessageDTO messageDTO) {
        Message message = new Message();
        User author = userRepository.getById(messageDTO.getAuthorId());
        Chat chat = chatRepository.getById(messageDTO.getChatId());

        message.setChat(chat);
        message.setAuthor(author);
        message.setContentType(messageDTO.getContentType());
        message.setContent(messageDTO.getContent());

        return messageRepository.save(message);
    }

    //redis
    @Cacheable(value = "user", key = "#id")
    public Message getMessagesById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
    }

    public List<Message> getAllMessagesByChatId(Long chatId) {
        return messageRepository.findAllByChatId(chatId);
    }

    public List<Message> getAllMessagesByUserId(Long userId) {
        return messageRepository.findAllByAuthor_Id(userId);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message updateMessage(MessageDTO messageDTO, Long messageId) {
        Message existingMessage = getMessageById(messageId);
        User author = userRepository.getById(messageDTO.getAuthorId());
        Chat chat = chatRepository.getById(messageDTO.getChatId());

        existingMessage.setChat(chat);
        existingMessage.setAuthor(author);
        existingMessage.setContentType(messageDTO.getContentType());
        existingMessage.setContent(messageDTO.getContent());
        return messageRepository.save(existingMessage);
    }

    public Message updateMessageContent(Long messageId, String newContent) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        message.setContent(newContent);
        return messageRepository.save(message);
    }

    public void removeMessage(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "id", messageId));
        messageRepository.delete(message);
    }

    public List<Message> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

}
