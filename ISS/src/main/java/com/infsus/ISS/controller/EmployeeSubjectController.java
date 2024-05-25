package com.infsus.ISS.controller;

import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.model.EmployeeSubject;
import com.infsus.ISS.repository.EmployeeSubjectRepository;
import com.infsus.ISS.service.EmployeeSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employee/subject", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeSubjectController {
    private EmployeeSubjectService employeeSubjectService;
    private EmployeeSubjectRepository employeeSubjectRepository;
    @Autowired
    public EmployeeSubjectController(EmployeeSubjectService employeeSubjectService) {
        this.employeeSubjectService = employeeSubjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeSubject> createEmployee(@RequestBody EmployeeSubjectDTO employeeSubjectDTO) {
        EmployeeSubject createdEmployeeSubject = employeeSubjectService.createEmployeeSubject(employeeSubjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeeSubject);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEmployeeSubject(@RequestBody EmployeeSubjectUpdateDTO employeeSubjectUpdateDTO) {
        employeeSubjectService.updateEmployeeSubject(employeeSubjectUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{idEmployeeSubject}")
    public ResponseEntity<String> deleteEmployeeSubject(
            @PathVariable Long idEmployeeSubject) {
        try {
            employeeSubjectService.deleteEmployeeSubject(idEmployeeSubject);
            return ResponseEntity.ok("EmployeeSubject deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete EmployeeSubject: " + e.getMessage());
        }
    }
}
