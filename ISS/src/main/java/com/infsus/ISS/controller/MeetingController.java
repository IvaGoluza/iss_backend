package com.infsus.ISS.controller;

import com.infsus.ISS.model.DTO.MeetingResponseDTO;
import com.infsus.ISS.service.MeetingService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/meeting", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {
    final private MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MeetingResponseDTO>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }
}
