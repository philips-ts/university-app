package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.SubjectRepository;
import com.tarasenko.universityapp.dto.SubjectDTO;
import com.tarasenko.universityapp.entity.Subject;
import com.tarasenko.universityapp.mapper.SubjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
class SubjectServiceImplTest {
    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @InjectMocks
    private SubjectServiceImpl subjectService;


    private static List<Subject> actualSubjects;
    private static List<SubjectDTO> actualSubjectDtos;

    @BeforeAll
    static void setUp() {
        Subject subject1 = new Subject("Math");
        subject1.setId(1);
        Subject subject2 = new Subject("Music");
        subject2.setId(2);
        Subject subject3 = new Subject("Economics");
        subject3.setId(3);

        SubjectDTO subjectDto1 = new SubjectDTO(subject1.getId(), subject1.getName());
        SubjectDTO subjectDto2 = new SubjectDTO(subject2.getId(), subject2.getName());
        SubjectDTO subjectDto3 = new SubjectDTO(subject3.getId(), subject3.getName());

        actualSubjects = new ArrayList<>(List.of(subject1, subject2, subject3));
        actualSubjectDtos = new ArrayList<>(List.of(subjectDto1, subjectDto2, subjectDto3));
    }

    @Test
    void getAllSubjectsInfo_shouldReturnListOfSubjects() {
        Mockito.when(subjectRepository.findAll())
                .thenReturn(actualSubjects);
        Mockito.when(subjectMapper.toDtoList(anyList()))
                .thenReturn(actualSubjectDtos);

        List<SubjectDTO> expectedSubjectsDto = subjectService.getAllSubjects();

        assertThat(expectedSubjectsDto).isNotNull();
        assertThat(expectedSubjectsDto.size()).isEqualTo(actualSubjects.size());
    }


    @Test
    void getSubjectById_shouldReturnCorrectSubject_whenIdIsCorrect() {
        Subject actualSubject = actualSubjects.get(1);
        int testId = actualSubject.getId();
        SubjectDTO subjectDto = new SubjectDTO(testId, actualSubject.getName());


        Mockito.when(subjectRepository.findById(testId))
                .thenReturn(Optional.of(actualSubject));
        Mockito.when(subjectMapper.toDto(actualSubject))
                .thenReturn(subjectDto);


        Optional<SubjectDTO> expectedSubject = subjectService.getSubjectById(testId);

        assertThat(expectedSubject.isPresent()).isTrue();
        assertThat(expectedSubject.get().getName()).isEqualTo(actualSubject.getName());
    }

    @Test
    void saveSubject_shouldBeInvokedRepoMethod_whenInputSubjectIsCorrect() {
        SubjectDTO newSubjectDTO = new SubjectDTO(0, "Literature");
        subjectService.saveSubject(newSubjectDTO);

        verify(subjectRepository, times(1)).save(any());
    }

    @Test
    void saveSubject_shouldBeInvokedRepoMethod_whenInputSubjectIsIncorrect() {
        subjectService.saveSubject(null);
        verify(subjectRepository, never()).save(any());
    }

    @Test
    void deleteSubjectById_shouldBeInvokedRepoMethod() {
        int subjectIdToDelete = 5;
        subjectService.deleteSubjectById(subjectIdToDelete);

        verify(subjectRepository, times(1)).deleteById(subjectIdToDelete);
    }
}