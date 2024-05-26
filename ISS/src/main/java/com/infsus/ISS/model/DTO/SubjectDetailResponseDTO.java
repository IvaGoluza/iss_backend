package com.infsus.ISS.model.DTO;

import com.infsus.ISS.model.StatusPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDetailResponseDTO {
    private Long idEmployeeSubject;
    private SubjectResponseDTO subject;
    private String status;
    private String yearlyPlan;
    private int subjectClass;
    private int numberOfHours;
    private List<StatusPlan> allStatus;
}
