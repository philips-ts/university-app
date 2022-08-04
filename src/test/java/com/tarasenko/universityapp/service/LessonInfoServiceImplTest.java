package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.LessonInfoRepository;
import com.tarasenko.universityapp.dto.LessonInfoDTO;
import com.tarasenko.universityapp.entity.LessonInfo;
import com.tarasenko.universityapp.mapper.LessonInfoMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class LessonInfoServiceImplTest {

    @Mock
    private LessonInfoRepository lessonInfoRepository;

    @Mock
    private LessonInfoMapper lessonInfoMapper;

    @InjectMocks
    private LessonInfoServiceImpl lessonInfoService;
    

    private static List<LessonInfo> actualLessons;
    private static List<LessonInfoDTO> actualLessonDtos;


    @BeforeAll
    static void setUp() {
        LessonInfo lesson1 = new LessonInfo(LocalTime.of(9, 0),
                LocalTime.of(10, 45));
        lesson1.setId(1);
        LessonInfo lesson2 = new LessonInfo(LocalTime.of(11, 0),
                LocalTime.of(12, 45));
        lesson2.setId(2);
        LessonInfo lesson3 = new LessonInfo(LocalTime.of(13, 0),
                LocalTime.of(14, 45));
        lesson3.setId(3);

        LessonInfoDTO lessonDto1 = new LessonInfoDTO(lesson1.getId(),
                lesson1.getStartTime(), lesson1.getEndTime());
        LessonInfoDTO lessonDto2 = new LessonInfoDTO(lesson2.getId(),
                lesson2.getStartTime(), lesson2.getEndTime());
        LessonInfoDTO lessonDto3 = new LessonInfoDTO(lesson3.getId(),
                lesson3.getStartTime(), lesson3.getEndTime());


        actualLessons = new ArrayList<>(List.of(lesson1, lesson2, lesson3));
        actualLessonDtos = new ArrayList<>(List.of(lessonDto1, lessonDto2, lessonDto3));
    }

    @Test
    void getAllLessonsInfo_shouldReturnListOfLessons() {
        Mockito.when(lessonInfoRepository.findAll())
                .thenReturn(actualLessons);
        Mockito.when(lessonInfoMapper.toDtoList(anyList()))
                .thenReturn(actualLessonDtos);

        List<LessonInfoDTO> expectedLessons = lessonInfoService.getAllLessonsInfo();

        assertThat(expectedLessons).isNotNull();
        assertThat(expectedLessons.size()).isEqualTo(actualLessons.size());
    }   
    
    
    @Test
    void getLessonInfoById_shouldReturnCorrectLesson_whenIdIsCorrect() {
        LessonInfo actualLesson = actualLessons.get(1);
        LessonInfoDTO actualLessonInfoDto = new LessonInfoDTO(actualLesson.getId(),
                actualLesson.getStartTime(), actualLesson.getEndTime());

        int testId = actualLesson.getId();
        Mockito.when(lessonInfoRepository.findById(testId))
                .thenReturn(Optional.of(actualLesson));
        Mockito.when(lessonInfoMapper.toDto(actualLesson))
                .thenReturn(actualLessonInfoDto);

        Optional<LessonInfoDTO> expectedLessonInfo = lessonInfoService.getLessonById(testId);

        assertThat(expectedLessonInfo.isPresent()).isTrue();
        assertThat(expectedLessonInfo.get().getStartTime()).isEqualTo(actualLesson.getStartTime());
        assertThat(expectedLessonInfo.get().getEndTime()).isEqualTo(actualLesson.getEndTime());
    }

    @Test
    void saveLessonInfo_shouldBeInvokedRepoMethod_whenInputLessonInfoIsCorrect() {
        LessonInfoDTO newLessonInfoDTO = new LessonInfoDTO(0, LocalTime.of(15, 0),
                LocalTime.of(16, 45));
        lessonInfoService.saveLesson(newLessonInfoDTO);

        verify(lessonInfoRepository, times(1)).save(any());
    }

    @Test
    void saveLessonInfo_shouldBeInvokedRepoMethod_whenInputLessonInfoIsIncorrect() {
        lessonInfoService.saveLesson(null);
        verify(lessonInfoRepository, never()).save(any());
    }

    @Test
    void deleteLessonInfoById_shouldBeInvokedRepoMethod() {
        int lessonInfoIdToDelete = 5;
        lessonInfoService.deleteLessonById(lessonInfoIdToDelete);

        verify(lessonInfoRepository, times(1)).deleteById(lessonInfoIdToDelete);
    }
}
