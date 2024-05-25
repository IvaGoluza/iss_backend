package com.infsus.ISS.service;

import com.infsus.ISS.model.DTO.MeetingResponseDTO;

import java.util.List;

public interface MeetingService {
    List<MeetingResponseDTO> getAllMeetings();
}
