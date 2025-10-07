package com.foh.data.entities.kb;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.foh.data.entities.usermgmt.UserEntity;

@Entity
@Table(name = "tasks", schema = "kanban")
public class TasksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskid")
    private Integer taskId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "statusid", referencedColumnName = "StatusId", nullable = false)
    private TaskStatusDictionaryEntity status;

    @ManyToOne
    @JoinColumn(name = "createdby", referencedColumnName = "UserId", nullable = false)
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "assignedto", referencedColumnName = "UserId")
    private UserEntity assignedTo;

    @Column(name = "createdat")
    private java.time.ZonedDateTime createdAt;

    @Column(name = "updatedat")
    private java.time.ZonedDateTime updatedAt;
    
    @Column(name = "companyid", nullable = false)
    private Long companyId;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(
      name             = "roleids",
      columnDefinition = "integer[]"  // ensures DDL and metadata align
    )
    private Integer[] roleIds;

    // --- Getters and Setters ---
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public TaskStatusDictionaryEntity getStatus() {
        return status;
    }

    public void setStatus(TaskStatusDictionaryEntity status) {
        this.status = status;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserEntity getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserEntity assignedTo) {
        this.assignedTo = assignedTo;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getCompanyId() { 
        return companyId;
    }
    public void setCompanyId(Long companyId) { 
        this.companyId = companyId;
    }

    public Integer[] getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }
}
