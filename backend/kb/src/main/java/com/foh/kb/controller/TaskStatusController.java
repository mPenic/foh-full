package com.foh.kb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.foh.data.entities.kb.TaskStatusDictionaryEntity;
import com.foh.data.repository.kb.TaskStatusDictionaryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/task-statuses")
public class TaskStatusController {

    private final TaskStatusDictionaryRepository statusRepo;

    @Autowired
    public TaskStatusController(TaskStatusDictionaryRepository statusRepo) {
        this.statusRepo = statusRepo;
    }

    @GetMapping
    public List<TaskStatusDictionaryEntity> getAllStatuses() {
        return statusRepo.findAll();
    }
}
