package com.tarasenko.universityapp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "timetable")
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "class_date")
    private LocalDate classDate;

    @Column(name = "lesson_number")
    private int lessonNumber;

    @Column(name = "class_room")
    private int classRoom;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Timetable() {
    }

    public Timetable(Subject subject, LocalDate classDate, int lessonNumber, int classRoom, Group group) {
        this.subject = subject;
        this.classDate = classDate;
        this.lessonNumber = lessonNumber;
        this.classRoom = classRoom;
        this.group = group;
    }
}
