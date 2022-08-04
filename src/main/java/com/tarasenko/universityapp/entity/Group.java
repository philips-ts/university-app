package com.tarasenko.universityapp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public void addNewStudent(Student student) {
        if (student != null) {
            if (students == null) {
                students = new ArrayList<>();
            }
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        if (student != null) {
            if (students != null) {
                students.remove(student);
            }
        }
    }
}
