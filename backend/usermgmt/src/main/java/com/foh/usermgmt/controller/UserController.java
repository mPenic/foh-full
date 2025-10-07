package com.foh.usermgmt.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.foh.data.vo.UserVO;
import com.foh.usermgmt.services.UserService;
import com.foh.usermgmt.dto.UserSummaryDto;
import com.foh.security.CurrUserDetails;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Obrada forme za unos korisnika
    @PostMapping
     public UserSummaryDto createUser(@RequestBody UserVO userVO, @AuthenticationPrincipal CurrUserDetails currentUser) {
        // example logic: pass the current user's companyId to the creation service
        return userService.createUser(userVO, currentUser.getCompanyId());
    }

    @GetMapping
    public List<UserSummaryDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(params = "companyId")
    public List<UserSummaryDto> getByCompany(@RequestParam Long companyId) {
        return userService.getUsersByCompanyId(companyId);
    }

    @GetMapping("/{userId}")
    public UserSummaryDto getUserById(@PathVariable Long userId) {
        return userService.getUserSummaryById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, @AuthenticationPrincipal CurrUserDetails currentUser) {
        // optional: verify the user belongs to currentUser.getCompanyId()
        userService.deleteUser(userId, currentUser);
        return ResponseEntity.noContent().build();
    }
}
