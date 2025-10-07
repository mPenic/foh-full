package com.foh.ch.vo;

import com.foh.data.entities.ch.Message;

public class MessageVO {
    private Long groupId;
    private Long senderId;
    private String messageText;

    public MessageVO() {}

    public MessageVO(Message message) {
        this.groupId = message.getGroup().getGroupId();
        this.senderId = message.getSender().getUserId();
        this.messageText = message.getMessageText();
    }

    // Getteri i setteri
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
