package com.infsus.ISS.repository;

import com.infsus.ISS.model.Aktiv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AktivRepository extends JpaRepository<Aktiv, Long> {
}
