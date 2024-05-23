package com.infsus.ISS.repository;

import com.infsus.ISS.model.EmployeeSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSubjectRepository extends JpaRepository<EmployeeSubject, Long> {
}
