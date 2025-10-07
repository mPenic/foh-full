package com.foh.kb.dto;

public class TaskUpdateDto {
    private Integer statusId;
    private Long assignedToUserId;   // only if admin is assigning directly
    private Integer priority;           // when you add priority later

    public Integer getStatusId() {
        return statusId;
    }
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }
    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}