package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.*;
import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.repository.*;
import com.infsus.ISS.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AktivRepository aktivRepository;
    private final StatusPlanRepository statusPlanRepository;
    private final YearlyPlanRepository yearlyPlanRepository;
    private final SubjectRepository subjectRepository;
    private final EmployeeSubjectRepository employeeSubjectRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getIdUser)).map(user -> modelMapper.map(user, EmployeeResponseDTO.class)).toList();
    }
    @Override
    public Optional<EmployeeWithAktivDTO> getEmployeeWithAktivById(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeResponseDTO employeeDTO = modelMapper.map(employee, EmployeeResponseDTO.class);
            if (employee.getAktiv() != null) {
                employeeDTO.setAktivName(employee.getAktiv().getAktivName());
            }

            List<AktivDTO> aktivDTOList = aktivRepository.findAll().stream()
                    .map(aktiv -> modelMapper.map(aktiv, AktivDTO.class))
                    .collect(Collectors.toList());

            return Optional.of(new EmployeeWithAktivDTO(employeeDTO, aktivDTOList));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void updateEmployee(EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = employeeRepository.findById(employeeUpdateDTO.getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Copy properties from DTO to entity, excluding ID fields
        BeanUtils.copyProperties(employeeUpdateDTO, employee);

        // Set the Aktiv, StatusPlan, and YearlyPlan if they are provided
        if (employeeUpdateDTO.getIdAktiv() != null) {
            Aktiv aktiv = aktivRepository.findById(employeeUpdateDTO.getIdAktiv())
                    .orElseThrow(() -> new IllegalArgumentException("Aktiv not found"));
            employee.setAktiv(aktiv);
        }
        employeeRepository.save(employee);
    }

    @Override
    public boolean deleteEmployee(Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Aktiv aktiv = aktivRepository.findById(employeeDTO.getIdAktiv())
                .orElseThrow(() -> new EntityNotFoundException("Aktiv not found"));

        employee.setAktiv(aktiv);
        Employee savedEmployee = employeeRepository.save(employee);

        return modelMapper.map(savedEmployee, EmployeeResponseDTO.class);
    }


}
