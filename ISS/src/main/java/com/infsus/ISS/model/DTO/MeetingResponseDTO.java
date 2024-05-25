package com.infsus.ISS.model.DTO;

import com.infsus.ISS.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MeetingResponseDTO {
    private Long idMeeting;
    private String type;
    private String status;
    private String theme;
    private Date date;
    private String leader;
    private String organizedBy;
    private List<String> meetingEmployees;
}
