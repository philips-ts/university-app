package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.LessonInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonInfoRepository extends JpaRepository<LessonInfo, Integer> {
}
