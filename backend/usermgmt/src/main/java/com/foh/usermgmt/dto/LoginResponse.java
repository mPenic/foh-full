package com.foh.usermgmt.dto;

public class LoginResponse {
    private String message;
    private String sessionId;
    private Long companyId;
    private Long roleId;


      // getters and setters for all fields
      public String getMessage() { return message; }
      public void setMessage(String message) { this.message = message; }
  
      public String getSessionId() { return sessionId; }
      public void setSessionId(String sessionId) { this.sessionId = sessionId; }
  
      public Long getCompanyId() { return companyId; }
      public void setCompanyId(Long companyId) { this.companyId = companyId; }
  
      public Long getRoleId() { return roleId; }
      public void setRoleId(Long roleId) { this.roleId = roleId; }
}
