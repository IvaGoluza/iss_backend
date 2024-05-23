package com.infsus.ISS.repository;

import com.infsus.ISS.model.StatusPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPlanRepository extends JpaRepository<StatusPlan, Long> {
}
