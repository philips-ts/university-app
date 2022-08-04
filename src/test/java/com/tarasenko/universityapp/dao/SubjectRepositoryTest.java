package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Subject;
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
class SubjectRepositoryTest {
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void getAllSubjects_shouldReturnAllSubjects() {
        List<Subject> expectedSubjects = subjectRepository.findAll();

        assertThat(expectedSubjects.size()).isEqualTo(4);
    }

    @Test
    public void getById_shouldReturnSubject_whenIdIsCorrect() {
        int correctId = 3;
        Optional<Subject> expectedSubject = subjectRepository.findById(correctId);

        assertThat(expectedSubject.isPresent()).isTrue();
    }

    @Test
    public void getById_shouldReturnSubject_whenIdIsIncorrect() {
        int incorrectId = 99999;
        Optional<Subject> expectedSubject = subjectRepository.findById(incorrectId);

        assertThat(expectedSubject.isPresent()).isFalse();
    }

    @Test
    @Rollback
    public void deleteById_shouldDeleteSubjectFromDB() {
        int idToDelete = 4;
        subjectRepository.deleteById(idToDelete);

        Optional<Subject> expectedSubject = subjectRepository.findById(idToDelete);
        assertThat(expectedSubject.isPresent()).isFalse();
    }
}
