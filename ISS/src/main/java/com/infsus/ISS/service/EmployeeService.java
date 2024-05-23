package com.infsus.ISS.service;


import com.infsus.ISS.model.DTO.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDTO> getAllEmployees();

}
