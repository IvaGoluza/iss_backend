package com.infsus.ISS.service;

import com.infsus.ISS.model.DTO.EmployeeSubjectDTO;
import com.infsus.ISS.model.DTO.EmployeeSubjectUpdateDTO;
import com.infsus.ISS.model.EmployeeSubject;

public interface EmployeeSubjectService {

    void deleteEmployeeSubject(Long idEmployeeSubject);

    void updateEmployeeSubject(EmployeeSubjectUpdateDTO employeeSubjectUpdateDTO);

    EmployeeSubject createEmployeeSubject(EmployeeSubjectDTO employeeSubjectDTO);
}
