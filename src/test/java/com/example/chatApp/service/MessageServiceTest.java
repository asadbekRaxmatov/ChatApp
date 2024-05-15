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

        Message createdMessage = messageService.createMessage(messageDTO);

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

        Message retrievedMessage = messageService.getMessageById(1L);

        assertEquals(message.getId(), retrievedMessage.getId());
    }

    @Test
    public void testGetAllMessagesByChatId() {
        Long chatId = 1L;
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findAllByChatId(chatId)).thenReturn(messages);

        List<Message> retrievedMessages = messageService.getAllMessagesByChatId(chatId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

    @Test
    public void testGetAllMessagesByUserId() {
        Long userId = 1L;
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findAllByAuthor_Id(userId)).thenReturn(messages);

        List<Message> retrievedMessages = messageService.getAllMessagesByUserId(userId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

    @Test
    public void testGetAllMessages() {
        List<Message> messages = new ArrayList<>();
        when(messageRepository.findAll()).thenReturn(messages);

        List<Message> retrievedMessages = messageService.getAllMessages();

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

        Message result = messageService.updateMessage(messageDTO, messageId);

        assertNotNull(result);
        assertEquals(updatedMessage.getId(), result.getId());
        assertEquals(updatedMessage.getAuthor(), result.getAuthor());
        assertEquals(updatedMessage.getChat(), result.getChat());
        assertEquals(updatedMessage.getContentType(), result.getContentType());
        assertEquals(updatedMessage.getContent(), result.getContent());
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

        Message result = messageService.updateMessageContent(messageId, newContent);

        assertNotNull(result);
        assertEquals(updatedMessage.getId(), result.getId());
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

        List<Message> retrievedMessages = messageService.getMessagesByChatId(chatId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);
    }

}
