package com.infsus.ISS.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeSubjectDTO {
    private Long idUser;
    private String subjectName;
    private int numberOfHours;
    private int subjectClass;
}
