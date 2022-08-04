package com.tarasenko.universityapp.service;


import com.tarasenko.universityapp.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    Optional<StudentDTO> getStudentById(int id);
    void saveStudent(StudentDTO studentDto);
    void deleteStudentById(int id);
}
