package com.foh.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketMessagingService {
    private final SimpMessagingTemplate template;

    public WebSocketMessagingService(SimpMessagingTemplate template) {
        this.template = template;
    }
    public void sendTaskUpdate(Long companyId, String feature, Long roleId, Object payload) {
        String destination = String.format("/topic/%d/%s/%d", companyId, feature, roleId);
        template.convertAndSend(destination, payload);
    }
    public void sendTaskDeletion(Long companyId, String feature, Long roleId, Integer taskId) {
        String destination = String.format("/topic/%d/%s/%d", companyId, feature, roleId);
        template.convertAndSend(destination, 
            // payload is simply an integer id, but you could also do:
            // new TaskDeletionDto(taskId)
            taskId
        );
    }
}
