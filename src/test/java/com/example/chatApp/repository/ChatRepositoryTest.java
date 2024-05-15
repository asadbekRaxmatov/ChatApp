package com.example.chatApp.repository;
import com.example.chatApp.model.Chat;
import com.example.chatApp.service.ChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatRepositoryTest {

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void testFindAllByUsers_Id() {
        // Mocking data
        Long userId = 1L;
        List<Chat> mockChats = new ArrayList<>();
        mockChats.add(new Chat(1L, "Chat 1", new ArrayList<>()));
        mockChats.add(new Chat(2L, "Chat 2", new ArrayList<>()));

        // Mocking repository method call
        when(chatRepository.findAllByUsers_Id(userId)).thenReturn(mockChats);

        // Calling repository method
        List<Chat> resultChats = chatRepository.findAllByUsers_Id(userId);

        // Assertions
        assertEquals(mockChats.size(), resultChats.size());
        for (int i = 0; i < mockChats.size(); i++) {
            assertEquals(mockChats.get(i).getId(), resultChats.get(i).getId());
            assertEquals(mockChats.get(i).getName(), resultChats.get(i).getName());
        }
    }

    @Test
    public void testFindByName() {
        // Mocking data
        String name = "Chat 1";
        Chat mockChat = new Chat(1L, name, new ArrayList<>());

        // Mocking repository method call
        Mockito.when(chatRepository.findByName(name)).thenAnswer(invocation -> Optional.of(mockChat));

        // Calling repository method
        Optional<Chat> resultChatOptional = Optional.ofNullable(chatRepository.findByName(name));

        // Assertions
        assertEquals(true, resultChatOptional.isPresent());
        Chat resultChat = resultChatOptional.get();
        assertEquals(mockChat.getId(), resultChat.getId());
        assertEquals(mockChat.getName(), resultChat.getName());
    }

    @Test
    public void testFindByNameContaining() {
        // Mocking data
        String nameFragment = "Chat";
        List<Chat> mockChats = new ArrayList<>();

        mockChats.add(new Chat(1L, "Chat 1", new ArrayList<>()));
        mockChats.add(new Chat(2L, "My Chat", new ArrayList<>()));

        // Mocking repository method call
        when(chatRepository.findByNameContaining(nameFragment)).thenReturn(mockChats);

        // Calling repository method
        List<Chat> resultChats = chatRepository.findByNameContaining(nameFragment);

        // Assertions
        assertEquals(mockChats.size(), resultChats.size());
        for (int i = 0; i < mockChats.size(); i++) {
            assertEquals(mockChats.get(i).getId(), resultChats.get(i).getId());
            assertEquals(mockChats.get(i).getName(), resultChats.get(i).getName());
        }
    }
}
