package com.infsus.ISS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "preparation")
public class Preparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_preparation")
    private Long idPreparation;

    @Column(name = "teaching_unit", nullable = false)
    private String teachingUnit;

    @Column(name = "lesson_type", nullable = false)
    private String lessonType;

    @Column(name = "lesson_goal", nullable = false)
    private String lessonGoal;

    @Column(name = "lesson_introduction", nullable = false)
    private String lessonIntroduction;

    @Column(name = "lesson_conclusion", nullable = false)
    private String lessonConclusion;

    @Column(name = "main_part_of_lesson", nullable = false)
    private String mainPartOfLesson;

    @Column(name = "teaching_method")
    private String teachingMethod;

    @Column(name = "teaching_aids")
    private String teachingAids;

    @Column(name = "working_method")
    private String workingMethod;

    @Column(name = "correlation")
    private String correlation;

    @Column(name = "outcome", nullable = false)
    private String outcome;

    @ManyToOne
    @JoinColumn(name = "id_monthly_plan")
    private MonthlyPlan monthlyPlan;
}
