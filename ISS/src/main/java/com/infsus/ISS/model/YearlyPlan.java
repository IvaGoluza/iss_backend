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

    @OneToMany(mappedBy = "yearlyPlan", cascade = CascadeType.ALL)
    private Set<MonthlyTheme> monthlyThemes;
}
