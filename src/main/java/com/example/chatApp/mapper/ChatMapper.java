package com.example.chatApp.mapper;

import com.example.chatApp.dto.ChatDTO;
import com.example.chatApp.model.Chat;
import com.example.chatApp.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

    public ChatDTO toDto(Chat chat) {
        if (chat == null) {
            return null;
        }

        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setId(chat.getId());
        chatDTO.setName(chat.getName());
        chatDTO.setCreatedAt(chat.getCreatedAt());

        if (chat.getUsers() != null) {
            List<Long> userIds = chat.getUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            chatDTO.setUserIds(userIds);
        }

        return chatDTO;
    }

}
