package com.infsus.ISS.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeSubjectUpdateDTO {
    private Long idEmployeeSubject;
    private int numberOfHours;
    private String status;
    private String subjectClass;
}
