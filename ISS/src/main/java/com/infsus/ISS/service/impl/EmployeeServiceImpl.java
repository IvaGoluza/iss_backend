package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.*;
import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.repository.*;
import com.infsus.ISS.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
    private final SubjectRepository subjectRepository;
    private final EmployeeSubjectRepository employeeSubjectRepository;
    private final ModelMapper modelMapper;
    private static final int PASSWORD_LENGTH = 8;
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AktivRepository aktivRepository, SubjectRepository subjectRepository, EmployeeSubjectRepository employeeSubjectRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.aktivRepository = aktivRepository;
        this.subjectRepository = subjectRepository;
        this.employeeSubjectRepository = employeeSubjectRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

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

        BeanUtils.copyProperties(employeeUpdateDTO, employee);

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
        String randomPassword = generateRandomPassword();
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
       /* Aktiv aktiv = aktivRepository.findById(employeeDTO.getIdAktiv())
                .orElseThrow(() -> new EntityNotFoundException("Aktiv not found"));

        employee.setAktiv(aktiv);*/
        String hashedPassword = passwordEncoder.encode(randomPassword);
        employee.setPassword(hashedPassword);
        Employee savedEmployee = employeeRepository.save(employee);

        return modelMapper.map(savedEmployee, EmployeeResponseDTO.class);
    }
    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(PASSWORD_CHARS.length());
            sb.append(PASSWORD_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
    @Override
    public List<SubjectDetailResponseDTO> getSubjectsByEmployeeId(Long employeeId) {
        List<Subject> subjects = subjectRepository.findAllByEmployeeId(employeeId);
        List<SubjectDetailResponseDTO> listToSend = new ArrayList<>();
        for (Subject s : subjects) {
            List<EmployeeSubject> employeeSubjects = employeeSubjectRepository.findByEmployeeAndSubject(employeeId, s.getIdSubject());
            for (EmployeeSubject employeeSubject : employeeSubjects) {
                SubjectDetailResponseDTO subjectDTO = modelMapper.map(s, SubjectDetailResponseDTO.class); // Create a new DTO for each EmployeeSubject
                StatusPlan statusPlan = employeeSubject.getStatusPlan();
                YearlyPlan yearlyPlan = employeeSubject.getYearlyPlan();
                subjectDTO.setStatus(statusPlan != null ? statusPlan.getStatus() : null);
                subjectDTO.setYearlyPlan(yearlyPlan != null && yearlyPlan.getName() != null ? yearlyPlan.getName() : null);
                subjectDTO.setSubjectClass(employeeSubject.getSubjectClass());
                subjectDTO.setNumberOfHours(employeeSubject.getNumberOfHours());
                subjectDTO.setIdEmployeeSubject(employeeSubject.getIdEmployeeSubject());
                listToSend.add(subjectDTO);
            }
        }
        return listToSend;
    }


}
