package com.foh.kb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.foh.data.repository.usermgmt.RoleDictionaryRepository;
import com.foh.data.repository.usermgmt.UserRepository;
import com.foh.security.CurrUserDetails;
import com.foh.kb.dto.TaskWithRolesDto;
import com.foh.kb.dto.TaskUpdateDto;
import com.foh.data.entities.kb.TasksEntity;
import com.foh.data.entities.usermgmt.RoleDictionaryEntity;
import com.foh.kb.services.TasksService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TasksController {

    @Autowired
    private TasksService tasksService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final RoleDictionaryRepository roleRepo;

    @Autowired
    public TasksController(TasksService tasksService,
                           UserRepository userRepository, RoleDictionaryRepository roleRepo) {
        this.tasksService = tasksService;
        this.userRepository = userRepository;
        this.roleRepo = roleRepo;
    }
    /**
     * GET tasks for current user. If Admin => getAll. Otherwise => tasks assigned to or open for user’s role.
     */
    @GetMapping("/tasks")
    public List<TasksEntity> getTasksForCurrentUser(@AuthenticationPrincipal CurrUserDetails user) {
        // look up the Admin role id by name
        Long adminRoleId = roleRepo.findByRoleName("Admin")
            .map(RoleDictionaryEntity::getRoleId)
            .orElseThrow(() -> new IllegalStateException("No Admin role configured"));

        if (adminRoleId.equals(user.getRoleId())) {
            // Admin only sees tasks for their own company
            return tasksService.getTasksByCompany(user.getCompanyId());
        }

        // everybody else sees only their user‐+role‐scoped tasks
        return tasksService.findTasksForUser(
            user.getUserId(),
            user.getRoleId(),
            user.getCompanyId()
        );
    }

    /**
     * POST create a new task. Admin only, for example. (Or you might allow other roles.)
     * Uses data from TaskWithRolesDto to build the Task.
     */
    @PostMapping("/tasks")
    @PreAuthorize("hasRole('Admin')") // only Admin can create tasks
    public TasksEntity createTask(@RequestBody TaskWithRolesDto dto,
                                  @AuthenticationPrincipal CurrUserDetails userDetails) {
        // build the entity
        TasksEntity entity = new TasksEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPriority(dto.getPriority());
        
        // set createdBy
        entity.setCreatedBy(
            userRepository.findById(userDetails.getUserId())
                          .orElseThrow(() -> new RuntimeException("User not found"))
        );

        // assignedTo if provided
        if (dto.getAssignedToUserId() != null) {
            entity.setAssignedTo(
                userRepository.findById(dto.getAssignedToUserId())
                              .orElseThrow(() -> new RuntimeException("Assigned user not found"))
            );
        }

        // pass in user’s companyId so the new task is bound to that tenant
        return tasksService.createTask(entity, userDetails.getCompanyId(), dto.getRoleIds(), dto.getPriority());
    }

     /**
     * PUT  /api/tasks/{id}
     * DELETE /api/tasks/{id}
     */
    @PutMapping("/tasks/{id}")
    @PreAuthorize("hasRole('Admin') or #dto.assignedToUserId == principal.userId") 
    public TasksEntity updateTask(
            @PathVariable("id") Integer taskId,
            @RequestBody TaskUpdateDto dto,
            @AuthenticationPrincipal CurrUserDetails userDetails
    ) {
        // fetch, mutate only the fields we care about, and save
        return tasksService.updateTaskFields(taskId, dto, userDetails.getCompanyId());
    }

    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteTask(
        @PathVariable("id") Integer taskId,
        @AuthenticationPrincipal CurrUserDetails userDetails
    ) {
      // optional: verify the task belongs to their company
      tasksService.deleteTask(taskId, userDetails.getCompanyId());
      return ResponseEntity.ok().build();
    }
}
