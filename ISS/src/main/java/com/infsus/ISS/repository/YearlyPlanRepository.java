package com.infsus.ISS.repository;

import com.infsus.ISS.model.EmployeeSubject;
import com.infsus.ISS.model.YearlyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyPlanRepository extends JpaRepository<YearlyPlan, Long> {
}
