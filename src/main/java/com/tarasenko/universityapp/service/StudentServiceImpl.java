package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.StudentRepository;
import com.tarasenko.universityapp.dto.StudentDTO;
import com.tarasenko.universityapp.entity.Student;
import com.tarasenko.universityapp.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> allGroups = studentRepository.findAll();
        List<StudentDTO> studentDTOs = studentMapper.toDtoList(allGroups);

        return studentDTOs;
    }

    @Override
    public Optional<StudentDTO> getStudentById(int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            StudentDTO studentDto = studentMapper.toDto(studentOptional.get());
            return Optional.of(studentDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void saveStudent(StudentDTO studentDto) {
        if (studentDto != null) {
            Student student = studentMapper.toEntity(studentDto);
            studentRepository.save(student);
        }
    }

    @Override
    public void deleteStudentById(int id) {
        studentRepository.deleteById(id);
    }
}
