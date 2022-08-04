package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Integer> {
}
