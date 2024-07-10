package com.api.aluimoveis.dto;

import java.time.LocalDateTime;

public class MessageViewDto {

    private LocalDateTime date;
    private Long sender;
    private String content;

    private Long receiver;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public MessageViewDto(LocalDateTime date, Long sender, Long recipient, String content) {
        this.date = date;
        this.sender = sender;
        this.content = content;
        setReceiver(recipient);
    }
}
