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
@Table(name = "task")
public class Task {
    @EmbeddedId
    private TaskId id;

    @ManyToOne
    @MapsId("preparationId")
    @JoinColumn(name = "id_preparation")
    private Preparation preparation;

    @Column(name = "task_type", nullable = false)
    private String taskType;

    @Column(name = "task_description", nullable = false)
    private String taskDescription;
}
