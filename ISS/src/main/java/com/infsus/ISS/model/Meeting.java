package com.infsus.ISS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_meeting")
    private Long idMeeting;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "leader_name", nullable = false)
    private String leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organized_by", nullable = false)
    private Principal organizedBy;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MeetingEmployee> meetingEmployees;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Record> records;

    @ManyToOne
    @JoinColumn(name = "id_year")
    private SchoolYear schoolYear;
}
