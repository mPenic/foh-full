package com.foh.data.repository.kb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.data.entities.kb.TaskStatusDictionaryEntity;

@Repository
public interface TaskStatusDictionaryRepository extends JpaRepository<TaskStatusDictionaryEntity, Integer> {
    // Additional query methods if needed
}