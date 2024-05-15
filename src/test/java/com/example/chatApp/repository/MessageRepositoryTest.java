package com.example.chatApp.repository;


import com.example.chatApp.model.Chat;
import com.example.chatApp.model.Message;
import com.example.chatApp.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    private User user;
    private Chat chat;
    private Message message1;
    private Message message2;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        chat = new Chat();
        chat.setName("Test Chat");
        chat = chatRepository.save(chat);

        message1 = new Message();
        message1.setChat(chat);
        message1.setAuthor(user);
        message1.setContent("Hello, World!");
        message1.setCreatedAt(LocalDateTime.now());
        message1 = messageRepository.save(message1);

        message2 = new Message();
        message2.setChat(chat);
        message2.setAuthor(user);
        message2.setContent("Second message");
        message2.setCreatedAt(LocalDateTime.now());
        message2 = messageRepository.save(message2);
    }

    @Test
    public void testFindAllByChatId() {
        List<Message> messages = messageRepository.findAllByChatId(chat.getId());
        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getChat().getId()).isEqualTo(chat.getId());
    }

    @Test
    public void testFindAllByAuthor_Id() {
        List<Message> messages = messageRepository.findAllByAuthor_Id(user.getId());
        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getAuthor().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testFindByChatId() {
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getChat().getId()).isEqualTo(chat.getId());
    }
}
