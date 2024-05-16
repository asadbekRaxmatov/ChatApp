package com.example.chatApp.controller;

import com.example.chatApp.dto.MessageDTO;
import com.example.chatApp.model.Message;
import com.example.chatApp.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAddMessage() {
        MessageDTO messageDTO = new MessageDTO();
        Message newMessage = new Message();
        when(messageService.createMessage(messageDTO)).thenReturn(messageDTO);

        ResponseEntity<?> response = messageController.addMessage(messageDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newMessage, response.getBody());
    }

    @Test
    public void testGetMessageById() {
        Long messageId = 1L;
        Message message = new Message();
        MessageDTO messageDTO = new MessageDTO();
        when(messageService.getMessageById(messageId)).thenReturn(messageDTO);

        ResponseEntity<MessageDTO> response = messageController.getMessageById(messageId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testGetMessagesByChatId() {
        Long chatId = 1L;
        List<MessageDTO> messages = new ArrayList<>();
        when(messageService.getMessagesByChatId(chatId)).thenReturn(messages);

        ResponseEntity<List<MessageDTO>> response = messageController.getMessagesByChatId(chatId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    public void testGetAllMessagesByChatId() {
        Long chatId = 1L;
        List<MessageDTO> messages = new ArrayList<>();
        when(messageService.getAllMessagesByChatId(chatId)).thenReturn(messages);

        ResponseEntity<List<MessageDTO>> response = messageController.getAllMessagesByChatId(chatId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    public void testGetAllMessagesByUserId() {
        Long userId = 1L;
        List<MessageDTO> messages = new ArrayList<>();
        when(messageService.getAllMessagesByUserId(userId)).thenReturn(messages);

        ResponseEntity<List<MessageDTO>> response = messageController.getAllMessagesByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    public void testGetAllMessages() {
        List<MessageDTO> messages = new ArrayList<>();
        when(messageService.getAllMessages()).thenReturn(messages);

        ResponseEntity<List<MessageDTO>> response = messageController.getAllMessages();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    public void testUpdateMessageContent() {
        Long messageId = 1L;
        String newContent = "Updated content";
        MessageDTO updatedMessage = new MessageDTO();
        when(messageService.updateMessageContent(messageId, newContent)).thenReturn(updatedMessage);

        ResponseEntity<MessageDTO> response = messageController.updateMessageContent(messageId, newContent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMessage, response.getBody());
    }

    @Test
    public void testUpdateMessageContentById() {
        Long messageId = 1L;
        String newContent = "Updated content";
        MessageDTO updatedMessage = new MessageDTO();
        when(messageService.updateMessageContent(messageId, newContent)).thenReturn(updatedMessage);

        ResponseEntity<MessageDTO> response = messageController.updateMessageContentById(messageId, newContent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMessage, response.getBody());
    }

    @Test
    public void testRemoveMessage() {
        Long messageId = 1L;
        ResponseEntity<Void> response = messageController.removeMessage(messageId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(messageService, times(1)).removeMessage(messageId);
    }

}
