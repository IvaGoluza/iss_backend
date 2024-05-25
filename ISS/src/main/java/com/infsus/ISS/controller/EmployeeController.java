package com.infsus.ISS.controller;

import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.service.EmployeeService;
import com.infsus.ISS.service.EmployeeSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<EmployeeWithAktivDTO> getEmployeeById(@PathVariable("id") Long employeeId) {
        Optional<EmployeeWithAktivDTO> employeeOptional = employeeService.getEmployeeWithAktivById(employeeId);
        return employeeOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        employeeService.updateEmployee(employeeUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long employeeId) {
        boolean isRemoved = employeeService.deleteEmployee(employeeId);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectDetailResponseDTO>> getSubjectsByEmployeeId(@PathVariable Long id) {
        List<SubjectDetailResponseDTO> subjects = employeeService.getSubjectsByEmployeeId(id);
        return ResponseEntity.ok(subjects);
    }


}
