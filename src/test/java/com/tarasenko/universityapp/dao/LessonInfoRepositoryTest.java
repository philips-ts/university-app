package com.tarasenko.universityapp.dao;

import com.tarasenko.universityapp.entity.LessonInfo;
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
class LessonInfoRepositoryTest {
    @Autowired
    private LessonInfoRepository lessonInfoRepository;

    @Test
    public void getAllLessonInfo_shouldReturnAllLessonInfo() {
        List<LessonInfo> expectedLessonInfo = lessonInfoRepository.findAll();

        assertThat(expectedLessonInfo.size()).isEqualTo(7);
    }

    @Test
    public void getById_shouldReturnLessonInfo_whenIdIsCorrect() {
        int correctId = 2;
        Optional<LessonInfo> expectedLessonInfo = lessonInfoRepository.findById(correctId);

        assertThat(expectedLessonInfo.isPresent()).isTrue();
    }

    @Test
    public void getById_shouldReturnLessonInfo_whenIdIsIncorrect() {
        int incorrectId = 99999;
        Optional<LessonInfo> expectedLessonInfo = lessonInfoRepository.findById(incorrectId);

        assertThat(expectedLessonInfo.isPresent()).isFalse();
    }



    @Test
    @Rollback
    public void deleteById_shouldDeleteLessonInfoFromDB() {
        int idToDelete = 4;
        lessonInfoRepository.deleteById(idToDelete);

        Optional<LessonInfo> expectedLessonInfo = lessonInfoRepository.findById(idToDelete);
        assertThat(expectedLessonInfo.isPresent()).isFalse();
    }
}
