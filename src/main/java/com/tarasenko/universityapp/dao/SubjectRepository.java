package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
