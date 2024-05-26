package com.infsus.ISS.repository;

import com.infsus.ISS.model.MeetingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRecordRepository extends JpaRepository<MeetingRecord, Long> {
}
