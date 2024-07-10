package com.api.aluimoveis.service;

import com.api.aluimoveis.dto.MessageDto;
import com.api.aluimoveis.entity.Message;

import java.util.List;

public interface MessageService {

    public Message createMessage(MessageDto message);

    List<Message> getMessageBetweenUsers(Long actualUser, Long sender);
}
