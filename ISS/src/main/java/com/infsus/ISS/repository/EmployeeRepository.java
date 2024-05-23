package com.infsus.ISS.repository;

import com.infsus.ISS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = """ 
            SELECT a
            FROM Employee a
            WHERE (:name IS NULL OR LOWER(CAST(a.name AS string)) LIKE CONCAT('%', LOWER(CAST(:name AS string)), '%'))""")
    List<Employee> search(@Param("name") String name);
}
