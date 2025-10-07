package com.foh.usermgmt.controller;

import com.foh.usermgmt.services.FeatureFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FeatureFlagController {

    @Autowired
    private FeatureFlagService featureFlagService;

    @GetMapping("/api/feature-flags")
    public Map<String, Boolean> getFeatureFlags(HttpSession session) {
        // 1) Retrieve the companyId from the session (adjust as needed).
        //    For example, maybe you store it under "companyId" during login:
        Long companyId = (Long) session.getAttribute("companyId");
        
        // If there's no companyId, you might handle it differently or throw an error.
        if (companyId == null) {
            // Return all false or throw an exception
            // Here we just return all false in a map:
            Map<String, Boolean> flags = new HashMap<>();
            flags.put("featCh", false);
            flags.put("featKb", false);
            flags.put("featRe", false);
            return flags;
        }

        // 2) Check each feature from the service
        boolean featCh = featureFlagService.isChatEnabled(companyId);
        boolean featKb = featureFlagService.isKanbanEnabled(companyId);
        boolean featRe = featureFlagService.isReservationsEnabled(companyId);

        // 3) Return a JSON object with the flags for easy consumption in frontend
        Map<String, Boolean> flags = new HashMap<>();
        flags.put("featCh", featCh);
        flags.put("featKb", featKb);
        flags.put("featRe", featRe);

        return flags;
    }
}
