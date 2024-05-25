package com.infsus.ISS.repository;

import com.infsus.ISS.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s JOIN EmployeeSubject es ON s.idSubject = es.subject.idSubject WHERE es.employee.idUser = :employeeId")
    List<Subject> findAllByEmployeeId(@Param("employeeId") Long employeeId);

    Subject findBySubjectName(String subject);
}
