package com.foh.ch.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.foh.ch.services.MessageService;
import com.foh.ch.vo.MessageVO;

import java.util.List;

@RestController
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/send") // Prima poruke od klijenta
    @SendTo("/topic/messages") // Distribuira poruke svim pretplatnicima
    public MessageVO sendMessage(@Payload MessageVO messageVO) {
        return messageService.saveMessage(messageVO);
    }

    @GetMapping("/api/chat/messages/{groupId}") // REST endpoint za istoriju poruka
    public List<MessageVO> getMessages(@PathVariable Long groupId) {
        return messageService.getMessagesByGroup(groupId);
    }
}
