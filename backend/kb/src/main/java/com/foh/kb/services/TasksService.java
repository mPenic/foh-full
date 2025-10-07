package com.foh.kb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foh.data.repository.usermgmt.RoleDictionaryRepository;
import com.foh.data.repository.usermgmt.UserRepository;
import com.foh.security.CurrUserDetails;

import jakarta.persistence.EntityNotFoundException;

import com.foh.messaging.WebSocketMessagingService;
import com.foh.kb.dto.TaskUpdateDto;
import com.foh.kb.dto.TaskWithRolesDto;
import com.foh.data.entities.kb.TaskStatusDictionaryEntity;
import com.foh.data.entities.kb.TasksEntity;
import com.foh.data.entities.usermgmt.UserEntity;
import com.foh.data.repository.kb.TaskStatusDictionaryRepository;
import com.foh.data.repository.kb.TasksRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Collections;


@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskStatusDictionaryRepository statusRepo;
    @Autowired
    private RoleDictionaryRepository roleRepo;
    @Autowired
    private WebSocketMessagingService wsService;

    private static final Logger log = LoggerFactory.getLogger(TasksService.class);
    /**
     * Create a new task.  If assignedTo is null => status = "Open" (ID=1).
     * Else => status = "Pending" (ID=2).
     * Also store the roles allowed to see/take the task (in TaskRolesEntity).
     */
    
    @Autowired
    public TasksService(
        UserRepository userRepository,
        TaskStatusDictionaryRepository statusRepo,
        TasksRepository tasksRepository
    ) {
      this.userRepository = userRepository;
      this.statusRepo = statusRepo;
      this.tasksRepository = tasksRepository;
    }

    @Transactional
    public TasksEntity createTask(TasksEntity task, Long companyId, List<Integer> roleIds, Integer priority) {
        task.setCompanyId(companyId);

        task.setPriority(priority != null ? priority : 3);

        task.setCreatedAt(ZonedDateTime.now());
        task.setUpdatedAt(ZonedDateTime.now());

         if (task.getAssignedTo() == null) {
          // 🔒 SAFELY HANDLE NULL roleIds
          if (roleIds == null) {
              roleIds = Collections.emptyList();
          }

          // Open task → Status=1, RoleIds from DTO (or empty)
          TaskStatusDictionaryEntity open = statusRepo.findById(1)
              .orElseThrow(() -> new RuntimeException("Open status not found"));
          task.setStatus(open);
          task.setRoleIds(roleIds.toArray(new Integer[0])); // ← now safe
        } else {
            // Assigned task → Status=2, RoleIds = assigned user’s role
            TaskStatusDictionaryEntity pend = statusRepo.findById(2)
                .orElseThrow(() -> new RuntimeException("Pending status not found"));
            task.setStatus(pend);
            Long r = task.getAssignedTo().getRole().getRoleId();
            task.setRoleIds(new Integer[]{ r.intValue() });
        }

        TasksEntity saved = tasksRepository.save(task);

        // Notify each relevant role channel
        for (Integer rid : saved.getRoleIds()) {
            wsService.sendTaskUpdate(companyId, "kanban", rid.longValue(), saved);
        }
        return saved;
    }

    @Transactional
    public TasksEntity updateTaskFields(Integer taskId, TaskUpdateDto dto, Long companyId) {

    log.debug("updateTaskFields called with DTO = {}", dto);
            
    TasksEntity existing = tasksRepository.findById(taskId)
        .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    boolean changed = false;
    if (dto.getStatusId() != null) {
      existing.setStatus(
        statusRepo.findById(dto.getStatusId())
                  .orElseThrow(() -> new RuntimeException("Invalid status"))
      );
      changed = true;
    }
    if (dto.getAssignedToUserId() != null) {
      UserEntity u = userRepository.findById(dto.getAssignedToUserId())
          .orElseThrow(() -> new RuntimeException("Invalid user"));
      existing.setAssignedTo(u);
      changed = true;
    }
    if (dto.getPriority() != null) {
      existing.setPriority(dto.getPriority());
      changed = true;
    }
    return changed
      ? tasksRepository.save(existing)   // will emit an UPDATE
      : existing;                        // no DB call if nothing changed
    }

    /**
     * Returns tasks for the entire system (only Admin can do this).
    public List<TasksEntity> getAllTasks() {
        return tasksRepository.findAll();
    }
    
     */
    /**
     * Returns a single task by ID, if found.
     */
    public Optional<TasksEntity> getTaskById(Integer taskId) {
        return tasksRepository.findById(taskId);
    }

    public List<TasksEntity> getTasksByCompany(Long companyId) {
      return tasksRepository.findAllByCompanyId(companyId);
    }
    /**
     * For non-admin users: find tasks either assigned to them or "open" to their role + same company.
     * You’ll implement custom repository queries for this.
     */
    public List<TasksEntity> findTasksForUser(Long userId, Long roleId, Long companyId) {
        // e.g., tasks assigned specifically to userId, plus tasks that belong to that user’s role & company
        List<TasksEntity> assigned = tasksRepository.findAssignedTasks(companyId, userId);
        List<TasksEntity> openToMyRole = tasksRepository.findOpenTasksForRole(companyId, roleId);

        // Merge them. You could do a union in a single query if you prefer.
        assigned.addAll(openToMyRole);
        return assigned;
    }

    /**
     * Delete a task by ID.  Typically only an Admin user can do this.
     */
    @Transactional
    public void deleteTask(Integer taskId, Long companyId) {
      TasksEntity task = tasksRepository.findById(taskId)
          .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));
    
      // grab the roleIds before we delete
      Integer[] roleIds = task.getRoleIds();
    
      // delete
      tasksRepository.delete(task);
    
      // notify everyone watching those role‐channels that this ID was removed
      for (Integer rid : roleIds) {
        wsService.sendTaskDeletion(companyId, "kanban", rid.longValue(), taskId);
      }
    }
}
