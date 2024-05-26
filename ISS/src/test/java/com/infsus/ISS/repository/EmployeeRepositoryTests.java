package com.infsus.ISS.repository;

import com.infsus.ISS.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();

        Employee employee1 = new Employee();
        employee1.setName("Pero Perić");
        employee1.setEmail("pero@example.com");
        employee1.setPassword("pass");
        employee1.setDateStart(new Date());
        employee1.setPosition("Učitelj");

        Employee employee2 = new Employee();
        employee2.setName("Ana Anić");
        employee2.setEmail("ana@example.com");
        employee2.setPassword("password2");
        employee2.setDateStart(new Date());
        employee2.setPosition("Učitelj");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
    }

    @Test
    public void testFindNextEmployee() {
        Employee employee1 = employeeRepository.findAll().get(0);
        Optional<Employee> nextEmployee = employeeRepository.findNextEmployee(employee1.getIdUser());
        assertThat(nextEmployee).isPresent();
        assertThat(nextEmployee.get().getName()).isEqualTo("Ana Anić");
    }

    @Test
    public void testFindPrevEmployee() {
        Employee employee2 = employeeRepository.findAll().get(1);
        Optional<Employee> prevEmployee = employeeRepository.findPrevEmployee(employee2.getIdUser());
        assertThat(prevEmployee).isPresent();
        assertThat(prevEmployee.get().getName()).isEqualTo("Pero Perić");
    }

    @Test
    public void testFindById() {
        Employee employee1 = employeeRepository.findAll().get(0);
        Optional<Employee> foundEmployee = employeeRepository.findById(employee1.getIdUser());
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo("Pero Perić");
    }

    @Test
    public void testCreateEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setName("Marta Martić");
        newEmployee.setEmail("marta@example.com");
        newEmployee.setPassword("password2");
        newEmployee.setDateStart(new Date());
        newEmployee.setPosition("Učitelj");
        Employee createdEmployee = employeeRepository.save(newEmployee);
        assertThat(createdEmployee).isNotNull();
        assertThat(createdEmployee.getIdUser()).isNotNull();
        assertThat(createdEmployee.getName()).isEqualTo("Marta Martić");
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee1 = employeeRepository.findAll().get(0);
        employee1.setEmail("pero123@example.com");
        Employee updatedEmployee = employeeRepository.save(employee1);
        assertThat(updatedEmployee.getEmail()).isEqualTo("pero123@example.com");
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee1 = employeeRepository.findAll().get(0);
        Long employee1Id = employee1.getIdUser();
        employeeRepository.delete(employee1);
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee1Id);
        assertThat(deletedEmployee).isEmpty();
    }
}
