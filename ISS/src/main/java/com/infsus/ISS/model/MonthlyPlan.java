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
@Table(name = "monthly_plan")
public class MonthlyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_monthly_plan")
    private Long idMonthlyPlan;

    @ManyToOne
    @JoinColumn(name = "id_yearly_plan")
    private YearlyPlan yearlyPlan;

    @ManyToOne
    @JoinColumn(name = "id_monthly_theme")
    private MonthlyTheme monthlyTheme;

}
