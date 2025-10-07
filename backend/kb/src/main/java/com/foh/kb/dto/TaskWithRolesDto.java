package com.foh.kb.dto;  // or wherever you keep DTOs

import java.util.List;

public class TaskWithRolesDto {
    private String title;
    private String description;
    private Integer priority;
    private Long assignedToUserId;      
    private List<Integer> roleIds;        

    // Constructors
    public TaskWithRolesDto() {}

    // Getters / Setters

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

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }
    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
