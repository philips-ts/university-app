package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.Timetable;
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
class TimetableRepositoryTest {
    @Autowired
    private TimetableRepository timetableRepository;

    @Test
    public void getAllTimetables_shouldReturnAllTimetables() {
        List<Timetable> expectedTimetables = timetableRepository.findAll();

        assertThat(expectedTimetables.size()).isEqualTo(9);
    }

    @Test
    public void getById_shouldReturnTimetable_whenIdIsCorrect() {
        int correctId = 5;
        Optional<Timetable> expectedTimetable = timetableRepository.findById(correctId);

        assertThat(expectedTimetable.isPresent()).isTrue();
    }

    @Test
    public void getById_shouldReturnTimetable_whenIdIsIncorrect() {
        int incorrectId = 99999;
        Optional<Timetable> expectedTimetable = timetableRepository.findById(incorrectId);

        assertThat(expectedTimetable.isPresent()).isFalse();
    }



    @Test
    @Rollback
    public void deleteById_shouldDeleteTimetableFromDB() {
        int idToDelete = 4;
        timetableRepository.deleteById(idToDelete);

        Optional<Timetable> expectedTimetable = timetableRepository.findById(idToDelete);
        assertThat(expectedTimetable.isPresent()).isFalse();
    }
}
