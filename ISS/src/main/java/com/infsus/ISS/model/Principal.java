package com.infsus.ISS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "principal")
public class Principal extends User{
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<SchoolYear> createdSchoolYears;

}
