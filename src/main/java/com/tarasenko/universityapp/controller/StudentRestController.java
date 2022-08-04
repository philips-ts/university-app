package com.tarasenko.universityapp.controller;


import com.tarasenko.universityapp.dto.StudentDTO;
import com.tarasenko.universityapp.exception_handling.IncorrectDataException;
import com.tarasenko.universityapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/students")
@RestController
public class StudentRestController {
    private final StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> studentDTOs = studentService.getAllStudents();
        return studentDTOs;
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable int id) {
        Optional<StudentDTO> studentDtoOptional = studentService.getStudentById(id);
        if (studentDtoOptional.isPresent()) {
            return studentDtoOptional.get();
        } else {
            throw new IncorrectDataException("There is no student with ID = " + id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO addNewStudent(@RequestBody StudentDTO newStudentDTO) {
        studentService.saveStudent(newStudentDTO);

        return newStudentDTO;
    }

    @PutMapping
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDto) {
        studentService.saveStudent(studentDto);
        return studentDto;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentService.deleteStudentById(id);

        return "Student with ID " + id + " was deleted.";
    }
}
