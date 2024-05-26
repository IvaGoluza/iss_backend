package com.infsus.ISS.controller;

import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTests {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO employeeDTO;
    private EmployeeResponseDTO employeeResponseDTO;
    private EmployeeWithAktivDTO employeeWithAktivDTO;
    private EmployeeUpdateDTO employeeUpdateDTO;
    private List<EmployeeResponseDTO> employeeList;
    private List<SubjectDetailResponseDTO> subjectList;

    @BeforeEach
    public void setUp() {
        employeeDTO = new EmployeeDTO("John Doe", "john.doe@example.com", new Date(), "Teacher");
        employeeResponseDTO = new EmployeeResponseDTO(1L, "John Doe", "john.doe@example.com", null, new Date(), "Teacher", "Aktiv 1");

        employeeWithAktivDTO = new EmployeeWithAktivDTO(employeeResponseDTO, new ArrayList<>());
        employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setIdUser(1L);
        employeeUpdateDTO.setName("Updated Name");
        employeeUpdateDTO.setEmail("updated@example.com");
        employeeUpdateDTO.setDateStart(new Date());
        employeeUpdateDTO.setPosition("Updated Position");

        employeeList = new ArrayList<>();
        employeeList.add(employeeResponseDTO);

        subjectList = new ArrayList<>();
        subjectList.add(new SubjectDetailResponseDTO(1L, "Mathematics", "Pending", "2024 Plan", 1, 5, new ArrayList<>()));
    }

    @Test
    public void testCreateEmployee() {
        when(employeeService.createEmployee(employeeDTO)).thenReturn(employeeResponseDTO);

        ResponseEntity<EmployeeResponseDTO> response = employeeController.createEmployee(employeeDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeResponseDTO, response.getBody());

        verify(employeeService, times(1)).createEmployee(employeeDTO);
    }

    @Test
    public void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        ResponseEntity<List<EmployeeResponseDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeList, response.getBody());

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testGetEmployeeById_Found() {
        when(employeeService.getEmployeeWithAktivById(1L)).thenReturn(Optional.of(employeeWithAktivDTO));

        ResponseEntity<EmployeeWithAktivDTO> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeWithAktivDTO, response.getBody());

        verify(employeeService, times(1)).getEmployeeWithAktivById(1L);
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        when(employeeService.getEmployeeWithAktivById(1L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeWithAktivDTO> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(employeeService, times(1)).getEmployeeWithAktivById(1L);
    }

    @Test
    public void testGetNextEmployee_Found() {
        when(employeeService.getNextEmployee(1L)).thenReturn(Optional.of(employeeWithAktivDTO));

        ResponseEntity<EmployeeWithAktivDTO> response = employeeController.getNextEmployee(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeWithAktivDTO, response.getBody());

        verify(employeeService, times(1)).getNextEmployee(1L);
    }

    @Test
    public void testGetNextEmployee_NotFound() {
        when(employeeService.getNextEmployee(1L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeWithAktivDTO> response = employeeController.getNextEmployee(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(employeeService, times(1)).getNextEmployee(1L);
    }

    @Test
    public void testGetPrevEmployee_Found() {
        when(employeeService.getPrevEmployee(1L)).thenReturn(Optional.of(employeeWithAktivDTO));

        ResponseEntity<EmployeeWithAktivDTO> response = employeeController.getPrevEmployee(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeWithAktivDTO, response.getBody());

        verify(employeeService, times(1)).getPrevEmployee(1L);
    }

    @Test
    public void testGetPrevEmployee_NotFound() {
        when(employeeService.getPrevEmployee(1L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeWithAktivDTO> response = employeeController.getPrevEmployee(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(employeeService, times(1)).getPrevEmployee(1L);
    }

    @Test
    public void testUpdateEmployee() {
        doNothing().when(employeeService).updateEmployee(employeeUpdateDTO);

        ResponseEntity<Void> response = employeeController.updateEmployee(employeeUpdateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(employeeService, times(1)).updateEmployee(employeeUpdateDTO);
    }

    @Test
    public void testDeleteEmployee_Found() {
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testDeleteEmployee_NotFound() {
        when(employeeService.deleteEmployee(1L)).thenReturn(false);

        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testGetSubjectsByEmployeeId() {
        when(employeeService.getSubjectsByEmployeeId(1L)).thenReturn(subjectList);

        ResponseEntity<List<SubjectDetailResponseDTO>> response = employeeController.getSubjectsByEmployeeId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectList, response.getBody());

        verify(employeeService, times(1)).getSubjectsByEmployeeId(1L);
    }
}
