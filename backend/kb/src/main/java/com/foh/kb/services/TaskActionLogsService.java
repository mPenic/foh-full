package com.foh.kb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foh.data.entities.kb.TaskActionLogsEntity;
import com.foh.data.repository.kb.TaskActionLogsRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TaskActionLogsService {

    @Autowired
    private TaskActionLogsRepository taskActionLogsRepository;

    public TaskActionLogsEntity createLog(TaskActionLogsEntity log) {
        log.setChangedAt(ZonedDateTime.now());
        return taskActionLogsRepository.save(log);
    }

    public List<TaskActionLogsEntity> getLogsByTaskId(Integer taskId) {
        return taskActionLogsRepository.findByTaskTaskId(taskId);
    }
    
    // Additional methods as needed
}
