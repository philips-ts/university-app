package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dto.SubjectDTO;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<SubjectDTO> getAllSubjects();
    Optional<SubjectDTO> getSubjectById(int id);
    void saveSubject(SubjectDTO subjectDto);
    void deleteSubjectById(int id);
}
