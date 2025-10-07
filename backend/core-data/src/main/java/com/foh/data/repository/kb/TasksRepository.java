package com.foh.data.repository.kb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.foh.data.entities.kb.TasksEntity;

import java.util.List;

public interface TasksRepository extends JpaRepository<TasksEntity, Integer> {

    // tasks assigned to this user in this company
    @Query("""
      SELECT t
        FROM TasksEntity t
       WHERE t.companyId            = :companyId
         AND t.assignedTo.userId    = :assignedToId
    """)
    List<TasksEntity> findAssignedTasks(
      @Param("companyId")   Long companyId,
      @Param("assignedToId") Long assignedToId
    );

    // tasks that are "open" to a role => i.e. there's a row in TaskRoles linking t to that role,
    // and assignedTo is null or status is "Open" (depending on your logic).
    // Possibly status=1 means "Open." Adjust to your table definitions.
    @Query(value = """
        SELECT *
         FROM Tasks t
        WHERE t.CompanyId = :companyId
          AND t.StatusId = 1
          AND :roleId = ANY(t.RoleIds)
    """, nativeQuery = true)
    List<TasksEntity> findOpenTasksForRole(@Param("companyId") Long companyId,
                                           @Param("roleId") Long roleId);
    List<TasksEntity> findAllByCompanyId(Long companyId);
    // If you want an alternative approach, you can also define a single union query that returns
    // tasks assigned to the user or open for the user's role, but it might get more complex in JPQL
    // so we typically do it in the service by calling two queries and merging results.
}
