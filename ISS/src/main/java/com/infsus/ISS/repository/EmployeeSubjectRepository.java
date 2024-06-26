package com.infsus.ISS.repository;

import com.infsus.ISS.model.EmployeeSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeSubjectRepository extends JpaRepository<EmployeeSubject, Long> {
    @Query("SELECT es FROM EmployeeSubject es WHERE es.employee.id = :employeeId AND es.subject.id = :subjectId")
    List<EmployeeSubject> findByEmployeeAndSubject(@Param("employeeId") Long employeeId, @Param("subjectId") Long subjectId);

    Optional<EmployeeSubject> findById(Long idEmployeeSubject);

}
