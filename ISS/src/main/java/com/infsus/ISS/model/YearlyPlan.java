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
@Table(name = "yearly_plan")
public class YearlyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_yearly_plan")
    private Long idYearlyPlan;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "approved", nullable = false)
    private boolean approved;
}
