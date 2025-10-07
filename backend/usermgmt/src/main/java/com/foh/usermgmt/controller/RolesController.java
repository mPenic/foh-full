package com.foh.usermgmt.controller;

import com.foh.data.entities.usermgmt.RoleDictionaryEntity;
import com.foh.data.repository.usermgmt.RoleDictionaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RoleDictionaryRepository roleRepo;

    @Autowired
    public RolesController(RoleDictionaryRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public List<RoleDictionaryEntity> getAllRoles() {
        return roleRepo.findAll();
    }
}