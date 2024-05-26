package com.infsus.ISS.Service;

import com.infsus.ISS.model.*;
import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.repository.*;
import com.infsus.ISS.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AktivRepository aktivRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private EmployeeSubjectRepository employeeSubjectRepository;

    @Mock
    private StatusPlanRepository statusPlanRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private EmployeeUpdateDTO employeeUpdateDTO;
    private List<Employee> employeeList;

    private EmployeeResponseDTO responseDTO;

    private static final int PASSWORD_LENGTH = 10;
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setIdUser(1L);
        employee.setName("Marta Martić");
        employee.setEmail("marta@example.com");
        employee.setDateStart(new Date());
        employee.setPosition("Učitelj");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Marta Martić");
        employeeDTO.setEmail("marta@example.com");
        employeeDTO.setDateStart(new Date());
        employeeDTO.setPosition("Učitelj");

        employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setIdUser(1L);
        employeeUpdateDTO.setName("Marta Martić");
        employeeUpdateDTO.setEmail("marta@example.com");
        employeeUpdateDTO.setDateStart(new Date());
        employeeUpdateDTO.setPosition("Učitelj");

        responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Marta Martić");
        responseDTO.setEmail("marta@example.com");
        responseDTO.setDateStart(employee.getDateStart());
        responseDTO.setDateEnd(null);
        responseDTO.setPosition("Učitelj");


        employeeList = new ArrayList<>();
        employeeList.add(employee);
    }

    @Test
    public void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(modelMapper.map(employee, EmployeeResponseDTO.class)).thenReturn(responseDTO);

        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("Marta Martić", result.get(0).getName());
    }

    @Test
    public void testGetEmployeeWithAktivById() {
        Long employeeId = 1L;

        Aktiv aktiv = new Aktiv();
        aktiv.setIdAktiv(1L);
        aktiv.setAktivName("Aktiv 1");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        when(aktivRepository.findAll()).thenReturn(List.of(aktiv));

        EmployeeResponseDTO employeeDTO = new EmployeeResponseDTO();
        employeeDTO.setId(employee.getIdUser());
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDateStart(employee.getDateStart());
        employeeDTO.setDateEnd(null);
        employeeDTO.setPosition(employee.getPosition());
        employeeDTO.setAktivName(aktiv.getAktivName());

        AktivDTO aktivDTO = new AktivDTO(aktiv.getIdAktiv(), aktiv.getAktivName());

        when(modelMapper.map(employee, EmployeeResponseDTO.class)).thenReturn(employeeDTO);
        when(modelMapper.map(aktiv, AktivDTO.class)).thenReturn(aktivDTO);

        EmployeeWithAktivDTO expectedEmployeeWithAktivDTO = new EmployeeWithAktivDTO(employeeDTO, List.of(aktivDTO));

        Optional<EmployeeWithAktivDTO> result = employeeService.getEmployeeWithAktivById(employeeId);
        assertTrue(result.isPresent());
        assertEquals(expectedEmployeeWithAktivDTO.getEmployee(), result.get().getEmployee());
        assertEquals(expectedEmployeeWithAktivDTO.getAktivList(), result.get().getAktivList());
    }
    @Test
    public void testGetEmployeeWithAktivById_EmployeeNotFound() {
        Long employeeId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        Optional<EmployeeWithAktivDTO> result = employeeService.getEmployeeWithAktivById(employeeId);

        assertFalse(result.isPresent());
    }


    @Test
    public void testUpdateEmployee() {
        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setIdUser(1L);
        employeeUpdateDTO.setName("Updated Name");
        employeeUpdateDTO.setEmail("updated@example.com");
        employeeUpdateDTO.setDateStart(new Date());
        employeeUpdateDTO.setPosition("Updated Position");
        employeeUpdateDTO.setIdAktiv(2L);

        Aktiv aktiv = new Aktiv();
        aktiv.setIdAktiv(2L);
        aktiv.setAktivName("Updated Aktiv");

        when(employeeRepository.findById(employeeUpdateDTO.getIdUser())).thenReturn(Optional.of(employee));
        when(aktivRepository.findById(employeeUpdateDTO.getIdAktiv())).thenReturn(Optional.of(aktiv));

        employeeService.updateEmployee(employeeUpdateDTO);

        verify(employeeRepository, times(1)).findById(employeeUpdateDTO.getIdUser());
        verify(aktivRepository, times(1)).findById(employeeUpdateDTO.getIdAktiv());

        assertEquals("Updated Name", employee.getName());
        assertEquals("updated@example.com", employee.getEmail());
        assertEquals("Updated Position", employee.getPosition());
        assertEquals(aktiv, employee.getAktiv());

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testGetSubjectsByEmployeeId() {
        Long employeeId = 1L;

        Subject subject = new Subject();
        subject.setIdSubject(1L);
        subject.setSubjectName("Matematika");

        List<Subject> subjects = List.of(subject);

        EmployeeSubject employeeSubject = new EmployeeSubject();
        employeeSubject.setIdEmployeeSubject(1L);
        employeeSubject.setEmployee(employee);
        employeeSubject.setSubject(subject);
        employeeSubject.setSubjectClass(1);
        employeeSubject.setNumberOfHours(10);

        StatusPlan statusPlan = new StatusPlan();
        statusPlan.setStatus("Čeka se");

        employeeSubject.setStatusPlan(statusPlan);

        YearlyPlan yearlyPlan = new YearlyPlan();
        yearlyPlan.setName("2024 Plan");

        employeeSubject.setYearlyPlan(yearlyPlan);

        List<EmployeeSubject> employeeSubjects = List.of(employeeSubject);

        List<StatusPlan> allStatus = List.of(statusPlan);

        when(subjectRepository.findAllByEmployeeId(employeeId)).thenReturn(subjects);
        when(employeeSubjectRepository.findByEmployeeAndSubject(employeeId, subject.getIdSubject())).thenReturn(employeeSubjects);
        when(statusPlanRepository.findAll()).thenReturn(allStatus);

        List<SubjectDetailResponseDTO> result = employeeService.getSubjectsByEmployeeId(employeeId);

        SubjectDetailResponseDTO expectedDTO = new SubjectDetailResponseDTO();
        expectedDTO.setSubjectName(subject.getSubjectName());
        expectedDTO.setStatus(statusPlan.getStatus());
        expectedDTO.setYearlyPlan(yearlyPlan.getName());
        expectedDTO.setSubjectClass(employeeSubject.getSubjectClass());
        expectedDTO.setNumberOfHours(employeeSubject.getNumberOfHours());
        expectedDTO.setIdEmployeeSubject(employeeSubject.getIdEmployeeSubject());
        expectedDTO.setAllStatus(allStatus);

        assertEquals(1, result.size());
        assertEquals(expectedDTO.getSubjectName(), result.get(0).getSubjectName());
        assertEquals(expectedDTO.getStatus(), result.get(0).getStatus());
        assertEquals(expectedDTO.getYearlyPlan(), result.get(0).getYearlyPlan());
        assertEquals(expectedDTO.getSubjectClass(), result.get(0).getSubjectClass());
        assertEquals(expectedDTO.getNumberOfHours(), result.get(0).getNumberOfHours());
        assertEquals(expectedDTO.getIdEmployeeSubject(), result.get(0).getIdEmployeeSubject());
        assertEquals(expectedDTO.getAllStatus(), result.get(0).getAllStatus());
    }

    @Test
    public void testCreateEmployee() {
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(employee);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeResponseDTO.class)).thenReturn(responseDTO);

        // Call the method
        EmployeeResponseDTO result = employeeService.createEmployee(employeeDTO);

        // Verify the interactions and result
        verify(modelMapper).map(employeeDTO, Employee.class);
        verify(passwordEncoder).encode(anyString());
        verify(employeeRepository).save(employee);
        verify(modelMapper).map(employee, EmployeeResponseDTO.class);

        assertEquals(responseDTO, result);
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


    @Test
    public void testGetNextEmployee() {
        Long currentEmployeeId = 1L;
        Employee nextEmployee = new Employee();
        nextEmployee.setIdUser(2L);
        nextEmployee.setName("Pero Perić");
        nextEmployee.setEmail("pero@example.com");
        nextEmployee.setDateStart(new Date());
        nextEmployee.setPosition("Učitelj");

        when(employeeRepository.findNextEmployee(currentEmployeeId)).thenReturn(Optional.of(nextEmployee));
        when(employeeRepository.findById(nextEmployee.getIdUser())).thenReturn(Optional.of(nextEmployee));

        EmployeeResponseDTO nextEmployeeDTO = new EmployeeResponseDTO();
        nextEmployeeDTO.setId(nextEmployee.getIdUser());
        nextEmployeeDTO.setName(nextEmployee.getName());
        nextEmployeeDTO.setEmail(nextEmployee.getEmail());
        nextEmployeeDTO.setDateStart(nextEmployee.getDateStart());
        nextEmployeeDTO.setPosition(nextEmployee.getPosition());

        when(modelMapper.map(nextEmployee, EmployeeResponseDTO.class)).thenReturn(nextEmployeeDTO);

        Optional<EmployeeWithAktivDTO> result = employeeService.getNextEmployee(currentEmployeeId);

        assertTrue(result.isPresent());
        assertEquals(nextEmployeeDTO, result.get().getEmployee());
    }

    @Test
    public void testGetNextEmployee_NotFound() {
        Long currentEmployeeId = 1L;

        when(employeeRepository.findNextEmployee(currentEmployeeId)).thenReturn(Optional.empty());

        Optional<EmployeeWithAktivDTO> result = employeeService.getNextEmployee(currentEmployeeId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetPrevEmployee() {
        Long currentEmployeeId = 2L;
        Employee prevEmployee = new Employee();
        prevEmployee.setIdUser(1L);
        prevEmployee.setName("Karla Karlić");
        prevEmployee.setEmail("karla@example.com");
        prevEmployee.setDateStart(new Date());
        prevEmployee.setPosition("Učitelj");

        when(employeeRepository.findPrevEmployee(currentEmployeeId)).thenReturn(Optional.of(prevEmployee));
        when(employeeRepository.findById(prevEmployee.getIdUser())).thenReturn(Optional.of(prevEmployee));

        EmployeeResponseDTO prevEmployeeDTO = new EmployeeResponseDTO();
        prevEmployeeDTO.setId(prevEmployee.getIdUser());
        prevEmployeeDTO.setName(prevEmployee.getName());
        prevEmployeeDTO.setEmail(prevEmployee.getEmail());
        prevEmployeeDTO.setDateStart(prevEmployee.getDateStart());
        prevEmployeeDTO.setPosition(prevEmployee.getPosition());

        when(modelMapper.map(prevEmployee, EmployeeResponseDTO.class)).thenReturn(prevEmployeeDTO);

        Optional<EmployeeWithAktivDTO> result = employeeService.getPrevEmployee(currentEmployeeId);

        assertTrue(result.isPresent());
        assertEquals(prevEmployeeDTO, result.get().getEmployee());
    }

    @Test
    public void testGetPrevEmployee_NotFound() {
        Long currentEmployeeId = 2L;

        when(employeeRepository.findPrevEmployee(currentEmployeeId)).thenReturn(Optional.empty());

        Optional<EmployeeWithAktivDTO> result = employeeService.getPrevEmployee(currentEmployeeId);

        assertFalse(result.isPresent());
    }



    @Test
    public void testDeleteEmployee_Exists() {
        Long employeeId = 1L;

        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        boolean result = employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);

        assertTrue(result);
    }

    @Test
    public void testDeleteEmployee_NotExists() {
        Long employeeId = 1L;

        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        boolean result = employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).existsById(employeeId);

        verify(employeeRepository, times(0)).deleteById(employeeId);

        assertFalse(result);
    }



}
