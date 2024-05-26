package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.*;
import com.infsus.ISS.model.DTO.EmployeeResponseDTO;
import com.infsus.ISS.model.DTO.EmployeeSubjectDTO;
import com.infsus.ISS.model.DTO.EmployeeSubjectUpdateDTO;
import com.infsus.ISS.model.DTO.SubjectDetailResponseDTO;
import com.infsus.ISS.repository.*;
import com.infsus.ISS.service.EmployeeSubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeSubjectServiceImpl implements EmployeeSubjectService {
    @Autowired
    private EmployeeSubjectRepository employeeSubjectRepository;
    @Autowired
    private YearlyPlanRepository yearlyPlanRepository;
    @Autowired
    private StatusPlanRepository statusPlanRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;


    public void deleteEmployeeSubject(Long idEmployeeSubject) {
        Optional<EmployeeSubject> optionalEmployeeSubject = employeeSubjectRepository.findById(idEmployeeSubject);
        optionalEmployeeSubject.ifPresent(employeeSubject -> {
            YearlyPlan yearlyPlan = employeeSubject.getYearlyPlan();
            if (yearlyPlan != null) {
                employeeSubject.setYearlyPlan(null);
                employeeSubjectRepository.save(employeeSubject);
                yearlyPlanRepository.delete(yearlyPlan);
            }
            employeeSubjectRepository.delete(employeeSubject);
        });
    }

    @Override
    public void updateEmployeeSubject(EmployeeSubjectUpdateDTO employeeSubjectUpdateDTO) {
        EmployeeSubject existingEmployeeSubject = employeeSubjectRepository.findById(employeeSubjectUpdateDTO.getIdEmployeeSubject())
                .orElseThrow(() -> new RuntimeException("EmployeeSubject not found"));

        if(employeeSubjectUpdateDTO.getSubjectClass() != null) {
            existingEmployeeSubject.setSubjectClass(Integer.parseInt(employeeSubjectUpdateDTO.getSubjectClass()));
        }
        existingEmployeeSubject.setNumberOfHours(employeeSubjectUpdateDTO.getNumberOfHours());
        if (employeeSubjectUpdateDTO.getStatus() != null) {
            existingEmployeeSubject.getStatusPlan().setStatus(employeeSubjectUpdateDTO.getStatus());
        }
        employeeSubjectRepository.save(existingEmployeeSubject);
    }

    @Override
    public SubjectDetailResponseDTO createEmployeeSubject(EmployeeSubjectDTO employeeSubjectDTO) {
        // Create a new EmployeeSubject entity
        EmployeeSubject employeeSubject = new EmployeeSubject();

        // Set the status plan
        StatusPlan statusPlan = statusPlanRepository.getReferenceById(1L);
        employeeSubject.setStatusPlan(statusPlan);

        // Find the subject by name
        Subject subject = subjectRepository.findBySubjectName(employeeSubjectDTO.getSubjectName());
        employeeSubject.setSubject(subject);

        // Set the employee reference
        Employee employee = employeeRepository.getReferenceById(employeeSubjectDTO.getIdUser());
        employeeSubject.setEmployee(employee);

        // Set additional fields
        employeeSubject.setNumberOfHours(employeeSubjectDTO.getNumberOfHours());
        employeeSubject.setSubjectClass(employeeSubjectDTO.getSubjectClass());

        // Save the EmployeeSubject entity
        EmployeeSubject savedEmployeeSubject = employeeSubjectRepository.save(employeeSubject);

        // Create and return the SubjectDetailResponseDTO
        SubjectDetailResponseDTO responseDTO = new SubjectDetailResponseDTO();
        responseDTO.setIdEmployeeSubject(savedEmployeeSubject.getIdEmployeeSubject());
        responseDTO.setSubjectName(savedEmployeeSubject.getSubject().getSubjectName());
        responseDTO.setStatus(savedEmployeeSubject.getStatusPlan().getStatus());
        responseDTO.setYearlyPlan(savedEmployeeSubject.getYearlyPlan() != null ? savedEmployeeSubject.getYearlyPlan().getName() : null);
        responseDTO.setSubjectClass(savedEmployeeSubject.getSubjectClass());
        responseDTO.setNumberOfHours(savedEmployeeSubject.getNumberOfHours());

        // Retrieve and set all status plans
        List<StatusPlan> allStatus = statusPlanRepository.findAll();
        responseDTO.setAllStatus(allStatus);

        return responseDTO;
    }

}
