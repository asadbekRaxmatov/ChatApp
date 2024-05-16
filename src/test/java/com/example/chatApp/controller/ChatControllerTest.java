package com.example.chatApp.controller;

import com.example.chatApp.dto.ChatDTO;
import com.example.chatApp.model.Chat;
import com.example.chatApp.service.ChatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;



@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    @Test
    public void testAddChat() {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setName("New Chat");

        Chat createdChat = new Chat();
        createdChat.setId(1L);
        createdChat.setName(chatDTO.getName());

        when(chatService.createChat(any(ChatDTO.class))).thenReturn(chatDTO);

        ResponseEntity<ChatDTO> responseEntity = chatController.addChat(chatDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createdChat, responseEntity.getBody());
    }

    @Test
    public void testGetChatById() {
        Long chatId = 1L;
        Chat chat = new Chat();
        ChatDTO chatDTO = new ChatDTO();
        chat.setId(chatId);

        when(chatService.getChatById(chatId)).thenReturn(chatDTO);

        ResponseEntity<ChatDTO> responseEntity = chatController.getChatById(chatId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(chat, responseEntity.getBody());
    }

    @Test
    public void testGetAllChats() {
        List<ChatDTO> chatList = new ArrayList<>();
        chatList.add(new ChatDTO());
        chatList.add(new ChatDTO());

        when(chatService.getAllChats()).thenReturn(chatList);

        ResponseEntity<List<ChatDTO>> responseEntity = chatController.getAllChats();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(chatList.size(), responseEntity.getBody().size());
    }

    @Test
    public void testDeleteChat() {
        Long chatId = 1L;

        ResponseEntity<Void> responseEntity = chatController.deleteChat(chatId);

        verify(chatService, times(1)).deleteChat(chatId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateChat() {
        Long chatId = 1L;
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setName("New Chat Name");

        ChatDTO updatedChat = new ChatDTO();
        updatedChat.setId(chatId);
        updatedChat.setName(chatDTO.getName());

        when(chatService.updateChat(chatId, chatDTO)).thenReturn(updatedChat);

        ResponseEntity<ChatDTO> responseEntity = chatController.updateChat(chatId, chatDTO);

        verify(chatService, times(1)).updateChat(chatId, chatDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedChat, responseEntity.getBody());
    }

    @Test
    public void testUpdateChatName() {
        Long chatId = 1L;
        String newName = "New Chat Name";

        ChatDTO updatedChat = new ChatDTO();
        updatedChat.setId(chatId);
        updatedChat.setName(newName);

        when(chatService.updateChatName(chatId, newName)).thenReturn(updatedChat);

        ResponseEntity<ChatDTO> responseEntity = chatController.updateChatName(chatId, newName);

        verify(chatService, times(1)).updateChatName(chatId, newName);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedChat, responseEntity.getBody());
    }

    @Test
    public void testRemoveChat() {
        Long chatId = 1L;

        ResponseEntity<Void> responseEntity = chatController.removeChat(chatId);

        verify(chatService, times(1)).removeChat(chatId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }


}
