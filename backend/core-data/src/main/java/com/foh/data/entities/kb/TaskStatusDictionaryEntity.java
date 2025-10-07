package com.foh.data.entities.kb;

import jakarta.persistence.*;

@Entity
@Table(name = "TaskStatusDictionary", schema = "kanban")
public class TaskStatusDictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusid")
    private Integer statusId;

    @Column(name = "taskstatusname", unique = true, nullable = false)
    private String taskStatusName;

     // --- Getters and Setters ---
     public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
    }
}
