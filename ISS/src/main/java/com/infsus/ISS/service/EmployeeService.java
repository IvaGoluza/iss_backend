package com.infsus.ISS.service;


import com.infsus.ISS.model.DTO.*;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeResponseDTO> getAllEmployees();

    Optional<EmployeeWithAktivDTO> getEmployeeWithAktivById(Long employeeId);

    void updateEmployee(EmployeeUpdateDTO employeeUpdateDTO);

    boolean deleteEmployee(Long idUser);

    EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO);

    List<SubjectDetailResponseDTO> getSubjectsByEmployeeId(Long employeeId);
    Optional<EmployeeWithAktivDTO> getNextEmployee(Long currentEmployeeId);

    Optional<EmployeeWithAktivDTO> getPrevEmployee(Long currentEmployeeId);
}
