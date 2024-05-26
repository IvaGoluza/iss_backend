package com.infsus.ISS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infsus.ISS.model.DTO.*;
import com.infsus.ISS.model.StatusPlan;
import com.infsus.ISS.repository.EmployeeRepository;
import com.infsus.ISS.repository.MeetingEmployeeRepository;
import com.infsus.ISS.repository.MeetingRecordRepository;
import com.infsus.ISS.repository.MeetingRepository;
import com.infsus.ISS.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MeetingRecordRepository recordRepository;

    @Autowired
    private MeetingEmployeeRepository meetingEmployeeRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setUp() {
        recordRepository.deleteAll();
        meetingEmployeeRepository.deleteAll();
        meetingRepository.deleteAll();
        employeeRepository.deleteAll();

        employeeDTO = new EmployeeDTO("Marta Martić", "marta@example.com", new Date(), "Učitelj");
    }

    @Test
    public void testCreateEmployee() throws Exception {
        String employeeFormJson = objectMapper.writeValueAsString(employeeDTO);

        ResultActions resultActions = mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeFormJson))
                .andExpect(status().isCreated());

        resultActions.andExpect(jsonPath("$.name").value("Marta Martić"))
                .andExpect(jsonPath("$.email").value("marta@example.com"))
                .andExpect(jsonPath("$.position").value("Učitelj"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        employeeService.createEmployee(employeeDTO);

        ResultActions resultActions = mockMvc.perform(get("/employee/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$[0].name").value("Marta Martić"))
                .andExpect(jsonPath("$[0].email").value("marta@example.com"))
                .andExpect(jsonPath("$[0].position").value("Učitelj"));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeDTO);

        ResultActions resultActions = mockMvc.perform(get("/employee/getEmployee/{id}", employeeResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.employee.name").value("Marta Martić"))
                .andExpect(jsonPath("$.employee.email").value("marta@example.com"))
                .andExpect(jsonPath("$.employee.position").value("Učitelj"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeDTO);

        EmployeeUpdateDTO updatedEmployeeDTO = new EmployeeUpdateDTO();
        updatedEmployeeDTO.setIdUser(employeeResponseDTO.getId());
        updatedEmployeeDTO.setName("Martica Martić");
        updatedEmployeeDTO.setEmail("marta123@example.com");
        updatedEmployeeDTO.setDateStart(new Date());
        updatedEmployeeDTO.setPosition("Učitelj");

        String updatedEmployeeFormJson = objectMapper.writeValueAsString(updatedEmployeeDTO);

        ResultActions resultActions = mockMvc.perform(put("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeFormJson))
                .andExpect(status().isOk());

        // Fetch updated employee to verify the update
        ResultActions verifyActions = mockMvc.perform(get("/employee/getEmployee/{id}", employeeResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verifyActions.andExpect(jsonPath("$.employee.name").value("Martica Martić"))
                .andExpect(jsonPath("$.employee.email").value("marta123@example.com"))
                .andExpect(jsonPath("$.employee.position").value("Učitelj"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeDTO);

        mockMvc.perform(delete("/employee/delete/{id}", employeeResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
