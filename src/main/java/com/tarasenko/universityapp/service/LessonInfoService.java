package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dto.LessonInfoDTO;

import java.util.List;
import java.util.Optional;

public interface LessonInfoService {
    List<LessonInfoDTO> getAllLessonsInfo();
    Optional<LessonInfoDTO> getLessonById(int id);
    void saveLesson(LessonInfoDTO lessonInfoDto);
    void deleteLessonById(int id);
}
