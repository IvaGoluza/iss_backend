package com.infsus.ISS.repository;


import com.infsus.ISS.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {
    /*@Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindNextEmployee() {
        Employee employee1 = new Employee();
        employee1.setPosition("Position1");
        Employee employee2 = new Employee();
        employee2.setPosition("Position2");
        Employee employee3 = new Employee();
        employee3.setPosition("Position3");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        // When
        Optional<Employee> nextEmployee = employeeRepository.findNextEmployee(employee1.getId());

        // Then
        assertThat(nextEmployee).isPresent();
        assertThat(nextEmployee.get().getId()).isEqualTo(employee2.getId());
    }

    @Test
    public void testFindPrevEmployee() {
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

        // When
        Optional<Employee> prevEmployee = employeeRepository.findPrevEmployee(employee2.getId());

        // Then
        assertThat(prevEmployee).isPresent();
        assertThat(prevEmployee.get().getId()).isEqualTo(employee1.getId());
    }

    @Test
    public void testFindNextEmployeeWhenLast() {
        // Given
        Employee employee1 = new Employee();
        employee1.setPosition("Position1");
        Employee employee2 = new Employee();
        employee2.setPosition("Position2");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // When
        Optional<Employee> nextEmployee = employeeRepository.findNextEmployee(employee2.getId());

        // Then
        assertThat(nextEmployee).isEmpty();
    }

    @Test
    public void testFindPrevEmployeeWhenFirst() {
        // Given
        Employee employee1 = new Employee();
        employee1.setPosition("Position1");
        Employee employee2 = new Employee();
        employee2.setPosition("Position2");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // When
        Optional<Employee> prevEmployee = employeeRepository.findPrevEmployee(employee1.getId());

        // Then
        assertThat(prevEmployee).isEmpty();
    }*/
}
