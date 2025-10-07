package com.foh.data.vo;

/*
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access; 
 */

public class UserVO {
    private Long userId;
    private String username;
    private String password;   
    private Long roleId;
    private final String roleName; 
    private Long  companyId;  

    public UserVO(Long userId, String username, String password, Long roleId, String roleName,Long  companyId) {
        this.userId =userId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
        this.companyId = companyId;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() { return roleName; }

    public Long getCompanyId(){
        return companyId;
    }
    
    public void setCompanyId(Long companyId){
        this.companyId = companyId;
    }
    
}
