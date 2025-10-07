package com.foh.data.repository.ch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foh.data.entities.ch.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByGroupId(Long groupId);
}
