package com.foh.data.repository.re;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.data.entities.re.ReservationStatusDictionaryEntity;

@Repository
public interface ReservationStatusDictionaryRepository extends JpaRepository<ReservationStatusDictionaryEntity, Long> {
}
