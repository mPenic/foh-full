package com.foh.data.repository.kb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.data.entities.kb.TaskActionLogsEntity;

import java.util.List;

@Repository
public interface TaskActionLogsRepository extends JpaRepository<TaskActionLogsEntity, Integer> {

    // Example custom method to find logs by TaskId
    List<TaskActionLogsEntity> findByTaskTaskId(Integer taskId);
}
