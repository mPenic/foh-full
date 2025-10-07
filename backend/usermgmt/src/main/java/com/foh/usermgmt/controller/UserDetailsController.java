package com.foh.usermgmt.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.Map;

import com.foh.security.CurrUserDetails;


@RestController
@RequestMapping("/api")
public class UserDetailsController {

    @GetMapping("/user-details")
    public Map<String, Object> userDetails(@AuthenticationPrincipal CurrUserDetails userDetails) {
        return Map.of(
            "companyId", userDetails.getCompanyId(),
            "roleId", userDetails.getRoleId(),
            "userId", userDetails.getUserId(),
            "username", userDetails.getUsername()
        );
    }
}