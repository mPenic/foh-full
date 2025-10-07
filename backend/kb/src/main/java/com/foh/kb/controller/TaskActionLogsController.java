package com.foh.kb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.foh.data.entities.kb.TaskActionLogsEntity;
import com.foh.kb.services.TaskActionLogsService;

import java.util.List;

@RestController
@RequestMapping("/api/task-logs")
public class TaskActionLogsController {

    @Autowired
    private TaskActionLogsService taskActionLogsService;

    @GetMapping("/task-logs/{taskId}")
    public List<TaskActionLogsEntity> getLogsForTask(@PathVariable("taskId") Integer taskId) {
        return taskActionLogsService.getLogsByTaskId(taskId);
    }

    @PostMapping
    public TaskActionLogsEntity createLog(@RequestBody TaskActionLogsEntity log) {
        return taskActionLogsService.createLog(log);
    }
}
