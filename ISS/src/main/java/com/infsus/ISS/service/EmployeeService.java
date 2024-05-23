package com.infsus.ISS.service;


import com.infsus.ISS.model.DTO.EmployeeDTO;
import com.infsus.ISS.model.DTO.EmployeeResponseDTO;
import com.infsus.ISS.model.DTO.EmployeeUpdateDTO;
import com.infsus.ISS.model.DTO.EmployeeWithAktivDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeResponseDTO> getAllEmployees();

    Optional<EmployeeWithAktivDTO> getEmployeeWithAktivById(Long employeeId);

    void updateEmployee(EmployeeUpdateDTO employeeUpdateDTO);

    boolean deleteEmployee(Long idUser);

    EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO);
}
