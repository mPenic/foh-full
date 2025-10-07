package com.foh.data.repository.usermgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.foh.data.entities.usermgmt.RoleDictionaryEntity;

@Repository
public interface RoleDictionaryRepository extends JpaRepository<RoleDictionaryEntity, Long> {
    // Prilagođene metode ako su potrebne
    Optional<RoleDictionaryEntity> findByRoleName(String roleName);
}
