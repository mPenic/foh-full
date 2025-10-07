package com.foh.data.repository.usermgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;

import java.util.Optional;

@Repository
public interface CompanyDictionaryRepository extends JpaRepository<CompanyDictionaryEntity, Long> {
    // Additional custom queries can be defined here if needed
    Optional<CompanyDictionaryEntity> findByCompanyName(String companyName);
}