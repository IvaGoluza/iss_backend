package com.infsus.ISS.repository;

import com.infsus.ISS.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository  extends JpaRepository<Meeting, Long> {
    @Query("SELECT m FROM Meeting m JOIN FETCH m.organizedBy")
    List<Meeting> findAllWithAssociations();
}
