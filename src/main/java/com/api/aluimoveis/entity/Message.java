package com.api.aluimoveis.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity()
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User receiver;

    private String content;
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Message(User sender, User recipient, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = recipient;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Message(Long id, User sender, User recipient, String content, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = recipient;
        this.content = content;
        this.timestamp = timestamp;
    }
    public Message(){}
}
