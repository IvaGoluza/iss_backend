package com.infsus.ISS.repository;

import com.infsus.ISS.model.SchoolDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDirectorRepository extends JpaRepository<SchoolDirector, Long> {
}
