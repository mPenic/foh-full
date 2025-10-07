package com.foh.usermgmt.controller;

import com.foh.security.CurrUserDetails;
import com.foh.usermgmt.dto.LoginRequest;
import com.foh.usermgmt.dto.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            // Create auth token from username/password
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    );

            // Perform the authentication
            Authentication authentication = authenticationManager.authenticate(authToken);

            // Attach Authentication to SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Extract custom user details
            CurrUserDetails userDetails = (CurrUserDetails) authentication.getPrincipal();

            // Create (or get existing) HTTP session
            HttpSession session = request.getSession(true);

            // Save the SecurityContext for next requests
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // ✅ Store your custom session attributes
            session.setAttribute("companyId", userDetails.getCompanyId());
            session.setAttribute("roleId", userDetails.getRoleId());
            // Add any others you need here, e.g. userDetails.getUserId(), etc.

            // Build a response for the frontend
            LoginResponse resp = new LoginResponse();
            resp.setMessage("Login successful");
            resp.setSessionId(session.getId());
            resp.setCompanyId(userDetails.getCompanyId());
            resp.setRoleId(userDetails.getRoleId());

            return ResponseEntity.ok(resp);

        } catch (AuthenticationException e) {
            // Authentication failed
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Clear SecurityContext
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            return ResponseEntity.ok(true); // User is authenticated
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false); // Not authenticated
    }

}
