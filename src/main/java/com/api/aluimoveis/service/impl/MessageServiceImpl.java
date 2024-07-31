package com.api.aluimoveis.service.impl;

import com.api.aluimoveis.dto.MessageDto;
import com.api.aluimoveis.entity.Message;
import com.api.aluimoveis.exception.BusinessException;
import com.api.aluimoveis.repository.MessageRepository;
import com.api.aluimoveis.service.MessageService;
import com.api.aluimoveis.service.PropertyService;
import com.api.aluimoveis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;


    @Override
    public Message createMessage(MessageDto message) {
        if (Objects.equals(message.getRecipient(), message.getSender())) {
            throw new BusinessException("failure: unable to send message to yourself");
        }

        var sender = this.userService.findById(message.getSender());
        var recipient = this.userService.findById(message.getRecipient());
        if (sender.isEmpty() || recipient.isEmpty()) {
            throw new BusinessException("fail to create message: sender or recipient id is invalid");
        }
        Message messageCreated = new Message(
                sender.get(),
                recipient.get(),
                message.getContent(),
                LocalDateTime.now()
        );

        return messageRepository.save(messageCreated);
    }

    @Override
    public List<Message> getMessageBetweenUsers(Long user1, Long user2) {
        return this.messageRepository.getMessageBetweenUsers(user1, user2);
    }
}
