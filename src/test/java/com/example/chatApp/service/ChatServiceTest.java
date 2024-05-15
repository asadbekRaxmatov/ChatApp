package com.example.chatApp.service;

import com.example.chatApp.dto.ChatDTO;
import com.example.chatApp.model.Chat;
import com.example.chatApp.model.User;
import com.example.chatApp.repository.ChatRepository;
import com.example.chatApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void testGetChatById() {
        Long chatId = 1L;
        Chat chat = new Chat();
        chat.setId(chatId);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        Chat retrievedChat = chatService.getChatById(chatId);

        assertEquals(chat.getId(), retrievedChat.getId());
        verify(chatRepository, times(1)).findById(chatId);
    }

    @Test
    public void testGetAllChats() {
        List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat());
        chatList.add(new Chat());

        when(chatRepository.findAll()).thenReturn(chatList);

        List<Chat> retrievedChats = chatService.getAllChats();

        assertEquals(chatList.size(), retrievedChats.size());
        verify(chatRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateChat() {
        Long chatId = 1L;
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setName("Updated Chat");
        chatDTO.setUserIds(new ArrayList<>());

        Chat existingChat = new Chat();
        existingChat.setId(chatId);

        Chat updatedChat = new Chat();
        updatedChat.setId(chatId);
        updatedChat.setName(chatDTO.getName());

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(existingChat));
        when(userRepository.findAllById(chatDTO.getUserIds())).thenReturn(new ArrayList<>());
        when(chatRepository.save(any(Chat.class))).thenReturn(updatedChat);

        Chat result = chatService.updateChat(chatId, chatDTO);

        assertEquals(updatedChat.getId(), result.getId());
        assertEquals(updatedChat.getName(), result.getName());
        verify(chatRepository, times(1)).findById(chatId);
        verify(userRepository, times(1)).findAllById(chatDTO.getUserIds());
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    public void testDeleteChat() {
        Long chatId = 1L;
        Chat chat = new Chat();
        chat.setId(chatId);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        chatService.deleteChat(chatId);

        verify(chatRepository, times(1)).delete(chat);
    }

    @Test
    public void testGetChatsByUserId() {
        Long userId = 1L;
        List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat());
        chatList.add(new Chat());

        when(chatRepository.findAllByUsers_Id(userId)).thenReturn(chatList);

        List<Chat> retrievedChats = chatService.getChatsByUserId(userId);

        assertEquals(chatList.size(), retrievedChats.size());
        verify(chatRepository, times(1)).findAllByUsers_Id(userId);
    }

    @Test
    public void testGetChatByName() {
        String name = "Chat 1";
        Chat chat = new Chat();
        chat.setName(name);

        when(chatRepository.findByName(name)).thenReturn(chat);

        Chat retrievedChat = chatService.getChatByName(name);

        assertEquals(chat.getName(), retrievedChat.getName());
        verify(chatRepository, times(1)).findByName(name);
    }

    @Test
    public void testSearchChatsByName() {
        String nameFragment = "Chat";
        List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat());
        chatList.add(new Chat());

        when(chatRepository.findByNameContaining(nameFragment)).thenReturn(chatList);

        List<Chat> retrievedChats = chatService.searchChatsByName(nameFragment);

        assertEquals(chatList.size(), retrievedChats.size());
        verify(chatRepository, times(1)).findByNameContaining(nameFragment);
    }

    @Test
    public void testCreateChat() {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setName("New Chat");
        chatDTO.setUserIds(Arrays.asList(1L, 2L));

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(userRepository.findAllById(chatDTO.getUserIds())).thenReturn(Arrays.asList(user1, user2));

        Chat savedChat = new Chat();
        savedChat.setId(1L);
        savedChat.setName(chatDTO.getName());
        savedChat.setUsers(Arrays.asList(user1, user2));

        when(chatRepository.save(any(Chat.class))).thenReturn(savedChat);

        Chat createdChat = chatService.createChat(chatDTO);

        assertNotNull(createdChat);
        assertEquals(chatDTO.getName(), createdChat.getName());
        assertEquals(chatDTO.getUserIds().size(), createdChat.getUsers().size());
        verify(userRepository, times(1)).findAllById(chatDTO.getUserIds());
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

}


