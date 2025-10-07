package com.foh.data.repository.re;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.data.entities.re.EventStatusDictionaryEntity;

@Repository
public interface EventStatusDictionaryRepository extends JpaRepository<EventStatusDictionaryEntity, Long> {
   
}
