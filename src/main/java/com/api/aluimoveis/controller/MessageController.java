package com.api.aluimoveis.controller;

import com.api.aluimoveis.dto.MessageDto;
import com.api.aluimoveis.dto.MessageViewDto;
import com.api.aluimoveis.entity.Message;
import com.api.aluimoveis.security.TokenService;
import com.api.aluimoveis.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@Tag(name = "Message Controller", description = "Contém endpoints para gerenciamento de mensagens")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    @Operation(summary = "Lista todas as mensagens", description = "Retorna uma lista de todas as mensagens")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/sender")
    @Operation(summary = "Obtém uma mensagem por ID", description = "Retorna uma mensagem específica pelo seu ID")
    public ResponseEntity<List<MessageViewDto>> getMessageBetweenUsers(
            @RequestParam Long user1,
            @RequestParam Long user2
    ) {
        var messages =
                messageService
                        .getMessageBetweenUsers(user1, user2)
                        .stream()
                        .map(this::toMessageView).toList();


        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @PostMapping
    @Operation(summary = "Cria uma nova mensagem", description = "Cria uma nova mensagem")
    public ResponseEntity<Object> createMessage(@RequestBody MessageDto message) {
        var createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma mensagem", description = "Atualiza uma mensagem existente")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(id, message);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma mensagem", description = "Deleta uma mensagem pelo seu ID")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private MessageViewDto toMessageView(Message m) {
        return new MessageViewDto(
                m.getTimestamp(),
                m.getSender().getId(),
                m.getReceiver().getId(),
                m.getContent()
        );
    }
}
