package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.StudentRepository;
import com.tarasenko.universityapp.dto.GroupDTO;
import com.tarasenko.universityapp.dto.StudentDTO;
import com.tarasenko.universityapp.entity.Group;
import com.tarasenko.universityapp.entity.Student;
import com.tarasenko.universityapp.mapper.StudentMapper;
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
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;


    private static List<Student> actualStudents;
    private static List<StudentDTO> actualStudentsDto;

    @BeforeAll
    static void setUp() {
        Student student1 = new Student("First1", "Last1", null);
        student1.setId(1);
        Student student2 = new Student("First2", "Last2", null);
        student2.setId(2);
        Student student3 = new Student("First3", "Last3", null);
        student3.setId(3);

        StudentDTO studentDto1 = new StudentDTO(student1.getId(), student1.getFirstname(),
                student1.getLastname(), null);
        StudentDTO studentDto2 = new StudentDTO(student2.getId(), student2.getFirstname(),
                student2.getLastname(), null);
        StudentDTO studentDto3 = new StudentDTO(student3.getId(), student3.getFirstname(),
                student3.getLastname(), null);

        actualStudents = new ArrayList<>(List.of(student1, student2, student3));
        actualStudentsDto = new ArrayList<>(List.of(studentDto1, studentDto2, studentDto3));
    }

    @Test
    void getAllStudentsInfo_shouldReturnListOfStudents() {
        Mockito.when(studentRepository.findAll())
                .thenReturn(actualStudents);
        Mockito.when(studentMapper.toDtoList(anyList()))
                .thenReturn(actualStudentsDto);

        List<StudentDTO> expectedStudents = studentService.getAllStudents();

        assertThat(expectedStudents).isNotNull();
        assertThat(expectedStudents.size()).isEqualTo(actualStudents.size());
    }


    @Test
    void getStudentById_shouldReturnCorrectStudent_whenIdIsCorrect() {
        Student actualStudent = actualStudents.get(1);
        StudentDTO actualStudentDto = new StudentDTO(actualStudent.getId(),
                actualStudent.getFirstname(),
                actualStudent.getLastname(), null);

        int testId = actualStudent.getId();
        Mockito.when(studentRepository.findById(testId))
                .thenReturn(Optional.of(actualStudent));
        Mockito.when(studentMapper.toDto(actualStudent))
                .thenReturn(actualStudentDto);

        Optional<StudentDTO> expectedStudent = studentService.getStudentById(testId);

        assertThat(expectedStudent.isPresent()).isTrue();
        assertThat(expectedStudent.get().getFirstname()).isEqualTo(actualStudent.getFirstname());
        assertThat(expectedStudent.get().getLastname()).isEqualTo(actualStudent.getLastname());
    }

    @Test
    void saveStudent_shouldBeInvokedRepoMethod_whenInputStudentIsCorrect() {
        StudentDTO newStudentDto = new StudentDTO(0, "First4", "Last4", null);
        Student newStudent = new Student(newStudentDto.getFirstname(),
                newStudentDto.getLastname(), null);

        Mockito.when(studentMapper.toEntity(newStudentDto))
                .thenReturn(newStudent);

        studentService.saveStudent(newStudentDto);

        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void saveStudent_shouldBeInvokedRepoMethod_whenInputStudentIsIncorrect() {
        studentService.saveStudent(null);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudentById_shouldBeInvokedRepoMethod() {
        int studentIdToDelete = 5;
        studentService.deleteStudentById(studentIdToDelete);

        verify(studentRepository, times(1)).deleteById(studentIdToDelete);
    }
}