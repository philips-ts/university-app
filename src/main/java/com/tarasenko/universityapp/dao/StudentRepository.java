package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
