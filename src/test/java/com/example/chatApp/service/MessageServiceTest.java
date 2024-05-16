package com.example.chatApp.service;

import com.example.chatApp.dto.MessageDTO;
import com.example.chatApp.model.Chat;
import com.example.chatApp.model.Message;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.ChatRepository;
import com.example.chatApp.repository.MessageRepository;
import com.example.chatApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMessage() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setAuthorId(1L);
        messageDTO.setChatId(1L);
        messageDTO.setContentType(TrayIcon.MessageType.valueOf("PLAIN_TEXT"));
        messageDTO.setContent("Test message");

        User author = new User();
        author.setId(1L);
        Chat chat = new Chat();
        chat.setId(1L);

        when(userRepository.getById(1L)).thenReturn(author);
        when(chatRepository.getById(1L)).thenReturn(chat);

        Message message = new Message();
        message.setId(1L);
        message.setAuthor(author);
        message.setChat(chat);
        message.setContentType(TrayIcon.MessageType.INFO);
        message.setContent("Test message");

        when(messageRepository.save(any(Message.class))).thenReturn(message);

        MessageDTO createdMessageDTO = messageService.createMessage(messageDTO);

        // Map the created MessageDTO to a Message object
        Message createdMessage = new Message();
        createdMessage.setId(createdMessageDTO.getChatId());
        createdMessage.setAuthor(userRepository.getById(createdMessageDTO.getAuthorId()));
        createdMessage.setChat(chatRepository.getById(createdMessageDTO.getChatId()));
        createdMessage.setContentType(createdMessageDTO.getContentType());
        createdMessage.setContent(createdMessageDTO.getContent());

        assertEquals(message.getId(), createdMessage.getId());
        assertEquals(message.getAuthor(), createdMessage.getAuthor());
        assertEquals(message.getChat(), createdMessage.getChat());
        assertEquals(message.getContentType(), createdMessage.getContentType());
        assertEquals(message.getContent(), createdMessage.getContent());
    }

    @Test
    public void testGetMessageById() {
        Message message = new Message();
        message.setId(1L);

        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        MessageDTO retrievedMessage = messageService.getMessageById(1L);

        assertEquals(message.getId(), retrievedMessage.getChatId());
    }

    @Test
    public void testGetAllMessagesByChatId() {
        Long chatId = 1L;
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findAllByChatId(chatId)).thenReturn(messages);

        List<MessageDTO> retrievedMessages = messageService.getAllMessagesByChatId(chatId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

    @Test
    public void testGetAllMessagesByUserId() {
        Long userId = 1L;
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findAllByAuthor_Id(userId)).thenReturn(messages);

        List<MessageDTO> retrievedMessages = messageService.getAllMessagesByUserId(userId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

    @Test
    public void testGetAllMessages() {
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findAll()).thenReturn(messages);

        List<MessageDTO> retrievedMessages = messageService.getAllMessages();

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

    @Test
    public void testUpdateMessage() {
        Long messageId = 1L;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setAuthorId(1L);
        messageDTO.setChatId(1L);
        messageDTO.setContentType(TrayIcon.MessageType.valueOf("PLAIN_TEXT"));
        messageDTO.setContent("Updated message");

        Message existingMessage = new Message();
        existingMessage.setId(messageId);
        User author = new User();
        author.setId(1L);
        Chat chat = new Chat();
        chat.setId(1L);
        existingMessage.setAuthor(author);
        existingMessage.setChat(chat);
        messageDTO.setContentType(TrayIcon.MessageType.valueOf("PLAIN_TEXT"));
        existingMessage.setContent("Original message");

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(userRepository.getById(1L)).thenReturn(author);
        when(chatRepository.getById(1L)).thenReturn(chat);

        Message updatedMessage = new Message();
        updatedMessage.setId(messageId);
        updatedMessage.setAuthor(author);
        updatedMessage.setChat(chat);
        messageDTO.setContentType(TrayIcon.MessageType.valueOf("PLAIN_TEXT"));
        updatedMessage.setContent("Updated message");
        when(messageRepository.save(any(Message.class))).thenReturn(updatedMessage);

        MessageDTO result = messageService.updateMessage(messageDTO, messageId);

        // Map the created MessageDTO to a Message object
        Message createdMessage = new Message();
        createdMessage.setId(result.getChatId());
        createdMessage.setAuthor(userRepository.getById(result.getAuthorId()));
        createdMessage.setChat(chatRepository.getById(result.getChatId()));
        createdMessage.setContentType(result.getContentType());
        createdMessage.setContent(result.getContent());

        assertNotNull(result);
        assertEquals(updatedMessage.getId(), createdMessage.getId());
        assertEquals(updatedMessage.getAuthor(), createdMessage.getAuthor());
        assertEquals(updatedMessage.getChat(), createdMessage.getChat());
        assertEquals(updatedMessage.getContentType(), createdMessage.getContentType());
        assertEquals(updatedMessage.getContent(), createdMessage.getContent());
    }

    @Test
    public void testUpdateMessageContent() {
        Long messageId = 1L;
        String newContent = "Updated content";

        Message message = new Message();
        message.setId(messageId);
        message.setContent("Original content");

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        Message updatedMessage = new Message();
        updatedMessage.setId(messageId);
        updatedMessage.setContent(newContent);
        when(messageRepository.save(any(Message.class))).thenReturn(updatedMessage);

        MessageDTO result = messageService.updateMessageContent(messageId, newContent);

        assertNotNull(result);
        assertEquals(updatedMessage.getId(), result.getChatId());
        assertEquals(updatedMessage.getContent(), result.getContent());
    }

    @Test
    public void testRemoveMessage() {
        Long messageId = 1L;
        Message message = new Message();
        message.setId(messageId);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        messageService.removeMessage(messageId);

        verify(messageRepository, times(1)).delete(message);
    }

    @Test
    public void testGetMessagesByChatId() {
        Long chatId = 1L;
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findByChatId(chatId)).thenReturn(messages);

        List<MessageDTO> retrievedMessages = messageService.getMessagesByChatId(chatId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

}
