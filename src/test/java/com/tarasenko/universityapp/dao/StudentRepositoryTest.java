package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void getAllStudents_shouldReturnAllStudents() {
        List<Student> expectedStudents = studentRepository.findAll();

        assertThat(expectedStudents.size()).isEqualTo(10);
    }

    @Test
    public void getById_shouldReturnStudent_whenIdIsCorrect() {
        int correctId = 5;
        Optional<Student> expectedStudent = studentRepository.findById(correctId);

        assertThat(expectedStudent.isPresent()).isTrue();
    }

    @Test
    public void getById_shouldReturnStudent_whenIdIsIncorrect() {
        int incorrectId = 99999;
        Optional<Student> expectedStudent = studentRepository.findById(incorrectId);

        assertThat(expectedStudent.isPresent()).isFalse();
    }



    @Test
    @Rollback
    public void deleteById_shouldDeleteStudentFromDB() {
        int idToDelete = 4;
        studentRepository.deleteById(idToDelete);

        Optional<Student> expectedStudent = studentRepository.findById(idToDelete);
        assertThat(expectedStudent.isPresent()).isFalse();
    }
}