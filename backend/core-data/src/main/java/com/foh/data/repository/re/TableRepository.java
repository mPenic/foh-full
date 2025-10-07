
package com.foh.re.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foh.re.entities.TableEntity;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
}
