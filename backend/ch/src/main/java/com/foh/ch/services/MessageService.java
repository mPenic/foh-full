package com.foh.ch.services;

import org.springframework.stereotype.Service;

import com.foh.data.entities.ch.Group;
import com.foh.data.entities.ch.Message;
import com.foh.data.repository.ch.GroupRepository;
import com.foh.data.repository.ch.MessageRepository;
import com.foh.data.repository.usermgmt.UserRepository;
import com.foh.ch.vo.MessageVO;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public MessageVO saveMessage(MessageVO messageVO) {
        Message message = new Message();
        Group group = groupRepository.findById(messageVO.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        message.setGroup(group);
        message.setSender(userRepository.findById(messageVO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        message.setMessageText(messageVO.getMessageText());
        message.setSentAt(Instant.now());
        message = messageRepository.save(message);
        return new MessageVO(message);
    }

    public List<MessageVO> getMessagesByGroup(Long groupId) {
        return messageRepository.findByGroupId(groupId).stream()
                .map(MessageVO::new)
                .collect(Collectors.toList());
    }
}
