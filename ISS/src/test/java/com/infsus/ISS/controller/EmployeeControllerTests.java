package com.infsus.ISS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.model.StatusPlan;
import com.infsus.ISS.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO employeeDTO;
    private EmployeeResponseDTO employeeResponseDTO;
    private EmployeeWithAktivDTO employeeWithAktivDTO;
    private EmployeeUpdateDTO employeeUpdateDTO;

    @BeforeEach
    public void setUp() {
        employeeDTO = new EmployeeDTO("Marta Martić", "marta@example.com", new Date(), "Učitelj");
        employeeResponseDTO = new EmployeeResponseDTO(1L, "Marta Martić", "marta@example.com", null, new Date(), "Učitelj", "Aktiv 1");

        employeeWithAktivDTO = new EmployeeWithAktivDTO(employeeResponseDTO, Arrays.asList(new AktivDTO(1L, "Aktiv 1")));
        employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setIdUser(1L);
        employeeUpdateDTO.setName("Martica Martić");
        employeeUpdateDTO.setEmail("marta123@example.com");
        employeeUpdateDTO.setDateStart(new Date());
        employeeUpdateDTO.setPosition("Učitelj");
    }

    @Test
    @WithMockUser
    public void testCreateEmployee() throws Exception {
        given(employeeService.createEmployee(ArgumentMatchers.any(EmployeeDTO.class))).willReturn(employeeResponseDTO);

        ResultActions response = mockMvc.perform(post("/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO))
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Marta Martić")))
                .andExpect(jsonPath("$.email", is("marta@example.com")))
                .andExpect(jsonPath("$.position", is("Učitelj")));
    }

    @Test
    @WithMockUser
    public void testGetAllEmployees() throws Exception {
        List<EmployeeResponseDTO> employeeList = Arrays.asList(employeeResponseDTO);

        given(employeeService.getAllEmployees()).willReturn(employeeList);

        ResultActions response = mockMvc.perform(get("/employee/getAll")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Marta Martić")))
                .andExpect(jsonPath("$[0].email", is("marta@example.com")))
                .andExpect(jsonPath("$[0].position", is("Učitelj")));
    }

    @Test
    @WithMockUser
    public void testGetEmployeeById_Found() throws Exception {
        given(employeeService.getEmployeeWithAktivById(1L)).willReturn(Optional.of(employeeWithAktivDTO));

        ResultActions response = mockMvc.perform(get("/employee/getEmployee/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.employee.id", is(1)))
                .andExpect(jsonPath("$.employee.name", is("Marta Martić")))
                .andExpect(jsonPath("$.employee.email", is("marta@example.com")))
                .andExpect(jsonPath("$.employee.position", is("Učitelj")))
                .andExpect(jsonPath("$.employee.aktivName", is("Aktiv 1")))
                .andExpect(jsonPath("$.aktivList[0].idAktiv", is(1)))  // Corrected JSON path for aktivList
                .andExpect(jsonPath("$.aktivList[0].aktivName", is("Aktiv 1")));  // Corrected JSON path for aktivList
    }

    @Test
    @WithMockUser
    public void testGetEmployeeById_NotFound() throws Exception {
        given(employeeService.getEmployeeWithAktivById(1L)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/employee/getEmployee/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testGetNextEmployee_Found() throws Exception {
        given(employeeService.getNextEmployee(1L)).willReturn(Optional.of(employeeWithAktivDTO));

        ResultActions response = mockMvc.perform(get("/employee/getNext/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.employee.id", is(1)))
                .andExpect(jsonPath("$.employee.name", is("Marta Martić")))
                .andExpect(jsonPath("$.employee.email", is("marta@example.com")))
                .andExpect(jsonPath("$.employee.position", is("Učitelj")))
                .andExpect(jsonPath("$.employee.aktivName", is("Aktiv 1")))
                .andExpect(jsonPath("$.aktivList[0].idAktiv", is(1)))
                .andExpect(jsonPath("$.aktivList[0].aktivName", is("Aktiv 1")));
    }

    @Test
    @WithMockUser
    public void testGetNextEmployee_NotFound() throws Exception {
        given(employeeService.getNextEmployee(1L)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/employee/getNext/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser
    public void testGetPrevEmployee_Found() throws Exception {
        given(employeeService.getPrevEmployee(1L)).willReturn(Optional.of(employeeWithAktivDTO));

        ResultActions response = mockMvc.perform(get("/employee/getPrev/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.employee.id", is(1)))
                .andExpect(jsonPath("$.employee.name", is("Marta Martić")))
                .andExpect(jsonPath("$.employee.email", is("marta@example.com")))
                .andExpect(jsonPath("$.employee.position", is("Učitelj")))
                .andExpect(jsonPath("$.employee.aktivName", is("Aktiv 1")))
                .andExpect(jsonPath("$.aktivList[0].idAktiv", is(1)))
                .andExpect(jsonPath("$.aktivList[0].aktivName", is("Aktiv 1")));
    }

    @Test
    @WithMockUser
    public void testGetPrevEmployee_NotFound() throws Exception {
        given(employeeService.getPrevEmployee(1L)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/employee/getPrev/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser
    public void testUpdateEmployee() throws Exception {
        doNothing().when(employeeService).updateEmployee(ArgumentMatchers.any(EmployeeUpdateDTO.class));

        ResultActions response = mockMvc.perform(put("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeUpdateDTO))
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteEmployee_Found() throws Exception {
        given(employeeService.deleteEmployee(1L)).willReturn(true);

        ResultActions response = mockMvc.perform(delete("/employee/delete/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void testDeleteEmployee_NotFound() throws Exception {
        given(employeeService.deleteEmployee(1L)).willReturn(false);

        ResultActions response = mockMvc.perform(delete("/employee/delete/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        response.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testGetSubjectsByEmployeeId() throws Exception {
        List<StatusPlan> allStatus = Arrays.asList(
                new StatusPlan(1L, "Čeka se"),
                new StatusPlan(2L, "Odbijeno"),
                new StatusPlan(3L, "Prihvaćeno")
        );

        List<SubjectDetailResponseDTO> subjectList = Arrays.asList(
                new SubjectDetailResponseDTO(1L, "Matematika", "Čeka se", "2024 Plan", 1, 5, allStatus)
        );

        given(employeeService.getSubjectsByEmployeeId(1L)).willReturn(subjectList);

        ResultActions response = mockMvc.perform(get("/employee/{id}/subjects", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEmployeeSubject", is(1)))
                .andExpect(jsonPath("$[0].subjectName", is("Matematika")))
                .andExpect(jsonPath("$[0].status", is("Čeka se")))
                .andExpect(jsonPath("$[0].yearlyPlan", is("2024 Plan")))
                .andExpect(jsonPath("$[0].subjectClass", is(1)))
                .andExpect(jsonPath("$[0].numberOfHours", is(5)))
                .andExpect(jsonPath("$[0].allStatus[0].status", is("Čeka se")))
                .andExpect(jsonPath("$[0].allStatus[1].idStatusPlan", is(2)))
                .andExpect(jsonPath("$[0].allStatus[1].status", is("Odbijeno")));
    }

}
