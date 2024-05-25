package com.infsus.ISS.repository;

import com.infsus.ISS.model.Employee;
import com.infsus.ISS.model.MeetingEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingEmployeeRepository extends JpaRepository<MeetingEmployee, Long> {
    @Query("SELECT me.employee FROM MeetingEmployee me WHERE me.meeting.idMeeting = ?1")
    List<Employee> getEmployeesByMeetingId(Long meetingId);
}
