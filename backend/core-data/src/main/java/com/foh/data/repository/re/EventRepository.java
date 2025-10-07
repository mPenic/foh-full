package com.foh.data.repository.re;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.data.entities.re.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    // Prilagođene metode ako su potrebne
}
