package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.DTO.MeetingResponseDTO;
import com.infsus.ISS.model.Employee;
import com.infsus.ISS.model.Meeting;
import com.infsus.ISS.model.MeetingEmployee;
import com.infsus.ISS.repository.MeetingEmployeeRepository;
import com.infsus.ISS.repository.MeetingRepository;
import com.infsus.ISS.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingEmployeeRepository meetingEmployeeRepository;
    private final ModelMapper modelMapper;
    public List<MeetingResponseDTO> getAllMeetings() {
        List<MeetingResponseDTO> meetingResponseDTOList = new ArrayList<>();
        List<Meeting> meetings = meetingRepository.findAll();

        for (Meeting meeting : meetings) {
            MeetingResponseDTO meetingResponseDTO = new MeetingResponseDTO();
            meetingResponseDTO.setIdMeeting(meeting.getIdMeeting());
            meetingResponseDTO.setType(meeting.getType());
            meetingResponseDTO.setStatus(meeting.getStatus());
            meetingResponseDTO.setTheme(meeting.getTheme());
            meetingResponseDTO.setDate(meeting.getDate());
            meetingResponseDTO.setLeader(meeting.getLeader());
            meetingResponseDTO.setOrganizedBy(meeting.getOrganizedBy().getName());

           List<Employee> employees = meetingEmployeeRepository.getEmployeesByMeetingId(meeting.getIdMeeting());
            List<String> names = new ArrayList<>();
           for(Employee e: employees) {
              names.add(e.getName());
           }
            meetingResponseDTO.setMeetingEmployees(names);
            meetingResponseDTOList.add(meetingResponseDTO);
        }

        return meetingResponseDTOList;
    }

}
