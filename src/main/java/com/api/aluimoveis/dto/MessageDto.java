package com.api.aluimoveis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

public class MessageDto {
    @NotNull(message = "Sender ID is mandatory")
    private Long sender;

    @NotNull(message = "Recipient ID is mandatory")
    private Long recipient;

    @NotBlank(message = "Content is mandatory")
    @Size(max = 500, message = "Content must be less than 500 characters")
    private String content;

    // Getters and setters
    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public MessageDto(Long sender, Long recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public MessageDto() {}
}
