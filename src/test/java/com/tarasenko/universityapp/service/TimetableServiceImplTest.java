package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.TimetableRepository;
import com.tarasenko.universityapp.dto.TimetableDTO;
import com.tarasenko.universityapp.entity.Timetable;
import com.tarasenko.universityapp.mapper.TimetableMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class TimetableServiceImplTest {
    @Mock
    private TimetableRepository timetableRepository;

    @Mock
    private TimetableMapper timetableMapper;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    private static List<Timetable> actualTimetableList;
    private static List<TimetableDTO> actualTimetableDtos;

    @BeforeAll
    static void setUp() {
        Timetable timetable1 = new Timetable(null, LocalDate.of(2022, 10, 1),
                1, 55, null);
        timetable1.setId(1);
        Timetable timetable2 = new Timetable(null, LocalDate.of(2022, 10, 2),
                2, 66, null);
        timetable2.setId(2);
        Timetable timetable3 = new Timetable(null, LocalDate.of(2022, 10, 3),
                3, 77, null);
        timetable3.setId(3);

        TimetableDTO timetableDto1 = new TimetableDTO(timetable1.getId(), null,
                timetable1.getClassDate(), timetable1.getLessonNumber(),
                timetable1.getClassRoom(), null);
        TimetableDTO timetableDto2 = new TimetableDTO(timetable2.getId(), null,
                timetable2.getClassDate(), timetable2.getLessonNumber(),
                timetable2.getClassRoom(), null);
        TimetableDTO timetableDto3 = new TimetableDTO(timetable3.getId(), null,
                timetable3.getClassDate(), timetable3.getLessonNumber(),
                timetable3.getClassRoom(), null);


        actualTimetableList = new ArrayList<>(List.of(timetable1, timetable2, timetable3));
        actualTimetableDtos = new ArrayList<>(List.of(timetableDto1, timetableDto2,
                timetableDto3));
    }

    @Test
    void getAllTimetablesInfo_shouldReturnListOfTimetables() {
        Mockito.when(timetableRepository.findAll())
                .thenReturn(actualTimetableList);
        Mockito.when(timetableMapper.toDtoList(anyList()))
                .thenReturn(actualTimetableDtos);

        List<TimetableDTO> expectedTimetable = timetableService.getAllTimetable();

        assertThat(expectedTimetable).isNotNull();
        assertThat(expectedTimetable.size()).isEqualTo(actualTimetableList.size());
    }


    @Test
    void getTimetableById_shouldReturnCorrectTimetable_whenIdIsCorrect() {
        Timetable actualTimetable = actualTimetableList.get(1);
        int testId = actualTimetable.getId();
        TimetableDTO timetableDto = new TimetableDTO(actualTimetable.getId(), null,
                actualTimetable.getClassDate(),  actualTimetable.getLessonNumber(),
                actualTimetable.getClassRoom(), null);


        Mockito.when(timetableRepository.findById(testId))
                .thenReturn(Optional.of(actualTimetable));
        Mockito.when(timetableMapper.toDto(actualTimetable))
                .thenReturn(timetableDto);

        Optional<TimetableDTO> expectedTimetable = timetableService.getTimetableById(testId);

        assertThat(expectedTimetable.isPresent()).isTrue();
        assertThat(expectedTimetable.get().getClassDate()).isEqualTo(actualTimetable.getClassDate());
        assertThat(expectedTimetable.get().getClassRoom()).isEqualTo(actualTimetable.getClassRoom());
        assertThat(expectedTimetable.get().getLessonNumber()).isEqualTo(actualTimetable.getLessonNumber());
    }

    @Test
    void saveTimetable_shouldBeInvokedRepoMethod_whenInputTimetableIsCorrect() {
        TimetableDTO newTimetableDao = new TimetableDTO(0, null,
                LocalDate.of(2022, 10, 4),4, 88, null);
        timetableService.saveTimetable(newTimetableDao);

        verify(timetableRepository, times(1)).save(any());
    }

    @Test
    void saveTimetable_shouldBeInvokedRepoMethod_whenInputTimetableIsIncorrect() {
        timetableService.saveTimetable(null);
        verify(timetableRepository, never()).save(any());
    }

    @Test
    void deleteTimetableById_shouldBeInvokedRepoMethod() {
        int timetableIdToDelete = 5;
        timetableService.deleteTimetableById(timetableIdToDelete);

        verify(timetableRepository, times(1)).deleteById(timetableIdToDelete);
    }
}