package com.infsus.ISS.repository;

import com.infsus.ISS.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the database before each test
        employeeRepository.deleteAll();

        // Given
        Employee employee1 = new Employee();
        employee1.setPosition("Position1");
        Employee employee2 = new Employee();
        employee2.setPosition("Position2");
        Employee employee3 = new Employee();
        employee3.setPosition("Position3");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
    }

    @Test
    public void testFindNextEmployee() {
        // Fetch the first employee to use its ID
        Employee employee1 = employeeRepository.findAll().get(0);

        // When
        Optional<Employee> nextEmployee = employeeRepository.findNextEmployee(employee1.getIdUser());

        // Then
        assertThat(nextEmployee).isPresent();
        assertThat(nextEmployee.get().getPosition()).isEqualTo("Position2");
    }

    @Test
    public void testFindPrevEmployee() {
        // Fetch the second employee to use its ID
        Employee employee2 = employeeRepository.findAll().get(1);

        // When
        Optional<Employee> prevEmployee = employeeRepository.findPrevEmployee(employee2.getIdUser());

        // Then
        assertThat(prevEmployee).isPresent();
        assertThat(prevEmployee.get().getPosition()).isEqualTo("Position1");
    }

    @Test
    public void testFindNextEmployeeWhenLast() {
        // Fetch the last employee to use its ID
        Employee employee3 = employeeRepository.findAll().get(2);

        // When
        Optional<Employee> nextEmployee = employeeRepository.findNextEmployee(employee3.getIdUser());

        // Then
        assertThat(nextEmployee).isEmpty();
    }

    @Test
    public void testFindPrevEmployeeWhenFirst() {
        // Fetch the first employee to use its ID
        Employee employee1 = employeeRepository.findAll().get(0);

        // When
        Optional<Employee> prevEmployee = employeeRepository.findPrevEmployee(employee1.getIdUser());

        // Then
        assertThat(prevEmployee).isEmpty();
    }

    @Test
    public void testFindById() {
        Employee employee1 = employeeRepository.findAll().get(0);
        Optional<Employee> foundEmployee = employeeRepository.findById(employee1.getIdUser());
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getPosition()).isEqualTo("Position1");
    }

    @Test
    public void testCreateEmployee() {
        // Given
        Employee newEmployee = new Employee();
        newEmployee.setPosition("NewPosition");

        // When
        Employee createdEmployee = employeeRepository.save(newEmployee);

        // Then
        assertThat(createdEmployee).isNotNull();
        assertThat(createdEmployee.getIdUser()).isNotNull();
        assertThat(createdEmployee.getPosition()).isEqualTo("NewPosition");
    }

    @Test
    public void testUpdateEmployee() {
        // Fetch the first employee to use its ID
        Employee employee1 = employeeRepository.findAll().get(0);
        employee1.setPosition("UpdatedPosition");

        // When
        Employee updatedEmployee = employeeRepository.save(employee1);

        // Then
        assertThat(updatedEmployee.getPosition()).isEqualTo("UpdatedPosition");
    }

    @Test
    public void testDeleteEmployee() {
        // Fetch the first employee to use its ID
        Employee employee1 = employeeRepository.findAll().get(0);
        Long employee1Id = employee1.getIdUser();

        // When
        employeeRepository.delete(employee1);
        Optional<Employee> deletedEmployee = employeeRepository.findById(employee1Id);

        // Then
        assertThat(deletedEmployee).isEmpty();
    }
}
