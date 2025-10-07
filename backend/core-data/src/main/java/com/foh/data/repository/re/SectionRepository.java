
package com.foh.re.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.re.entities.SectionEntity;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    // Možete dodati prilagođene metode ako su potrebne
}
