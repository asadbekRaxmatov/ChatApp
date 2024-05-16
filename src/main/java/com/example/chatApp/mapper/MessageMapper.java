package com.example.chatApp.mapper;


import com.example.chatApp.dto.MessageDTO;
import com.example.chatApp.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageDTO toDto(Message message) {
        if (message == null) {
            return null;
        }
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setChatId(message.getId());
        messageDTO.setContent(message.getContent());
        messageDTO.setContentType(message.getContentType());
        messageDTO.setAuthorId(message.getAuthor().getId());
        return messageDTO;
    }

}
