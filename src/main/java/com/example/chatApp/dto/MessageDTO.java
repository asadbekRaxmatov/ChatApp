package com.example.chatApp.dto;


import java.awt.*;

public class MessageDTO {
    private Long chatId;
    private Long authorId;
    private TrayIcon.MessageType contentType;
    private String content;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public TrayIcon.MessageType getContentType() {
        return contentType;
    }

    public void setContentType(TrayIcon.MessageType contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

