package com.infsus.ISS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee extends User{
    @Column(name = "position", nullable = false)
    private String position;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeSubject> employeeSubjects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aktiv")
    private Aktiv aktiv;


}
