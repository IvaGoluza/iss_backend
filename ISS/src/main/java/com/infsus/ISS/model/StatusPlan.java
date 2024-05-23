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
@Table(name = "status_plan")
public class StatusPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_plan")
    private Long idStatusPlan;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "approved", nullable = false)
    private boolean approved;
}
