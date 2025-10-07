package com.foh.data.entities.kb;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

import com.foh.data.entities.usermgmt.UserEntity;

@Entity
@Table(name = "taskactionlogs", schema = "kanban")
public class TaskActionLogsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logid")
    private Integer logId;

    @ManyToOne
    @JoinColumn(name = "taskid", referencedColumnName = "taskid", nullable = false)
    private TasksEntity task;

    @ManyToOne
    @JoinColumn(name = "actionby", referencedColumnName = "userid", nullable = false)
    private UserEntity actionBy;

    @Column(name = "actiontype", nullable = false)
    private String actionType;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "changedby", referencedColumnName = "userid", nullable = false)
    private UserEntity changedBy;

    @Column(name = "changedat")
    private ZonedDateTime changedAt;

    // --- Getters and Setters ---
    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public TasksEntity getTask() {
        return task;
    }

    public void setTask(TasksEntity task) {
        this.task = task;
    }

    public UserEntity getActionBy() {
        return actionBy;
    }

    public void setActionBy(UserEntity actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UserEntity changedBy) {
        this.changedBy = changedBy;
    }

    public ZonedDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(ZonedDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
