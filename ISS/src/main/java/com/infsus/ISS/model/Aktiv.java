package com.infsus.ISS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "aktiv")
public class Aktiv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aktiv")
    private Long idAktiv;

    @Column(name = "aktiv_name", nullable = false)
    private String aktivName;

    @ManyToMany(mappedBy = "aktiv", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
