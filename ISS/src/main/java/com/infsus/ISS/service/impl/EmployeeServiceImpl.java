package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.DTO.EmployeeResponseDTO;
import com.infsus.ISS.model.Employee;
import com.infsus.ISS.model.User;
import com.infsus.ISS.repository.EmployeeRepository;
import com.infsus.ISS.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getIdUser)).map(user -> modelMapper.map(user, EmployeeResponseDTO.class)).toList();
    }

}
