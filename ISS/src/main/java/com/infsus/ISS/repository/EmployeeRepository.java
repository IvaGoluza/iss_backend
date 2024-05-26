package com.infsus.ISS.repository;

import com.infsus.ISS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.id > :currentEmployeeId ORDER BY e.id ASC")
    Optional<Employee> findNextEmployee(@Param("currentEmployeeId") Long currentEmployeeId);

    @Query("SELECT e FROM Employee e WHERE e.id < :currentEmployeeId ORDER BY e.id DESC")
    Optional<Employee> findPrevEmployee(@Param("currentEmployeeId") Long currentEmployeeId);
}
