package com.tarasenko.universityapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarasenko.universityapp.dto.StudentDTO;
import com.tarasenko.universityapp.service.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StudentRestController.class)
class StudentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static List<StudentDTO> actualStudents;


    @BeforeAll
    static void setUp() {
        StudentDTO student1 = new StudentDTO(1, "First1", "Last1", null);
        StudentDTO student2 = new StudentDTO(2, "First2", "Last2", null);
        StudentDTO student3 = new StudentDTO(3, "First3", "Last3", null);

        actualStudents = new ArrayList<>(List.of(student1, student2, student3));
    }


    @Test
    void getAllStudents_shouldReturnAllStudents() throws Exception {
        Mockito.when(studentService.getAllStudents())
                .thenReturn(actualStudents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(actualStudents.size())))
                .andExpect(jsonPath("$[0].firstname", is(actualStudents.get(0).getFirstname())))
                .andExpect(jsonPath("$[0].lastname", is(actualStudents.get(0).getLastname())))
                .andExpect(jsonPath("$[1].firstname", is(actualStudents.get(1).getFirstname())))
                .andExpect(jsonPath("$[1].lastname", is(actualStudents.get(1).getLastname())))
                .andExpect(jsonPath("$[2].firstname", is(actualStudents.get(2).getFirstname())))
                .andExpect(jsonPath("$[2].lastname", is(actualStudents.get(2).getLastname())));
    }

    @Test
    void getStudentById_shouldReturnValidStudent_whenStudentIdIsValid() throws Exception {
        int testStudentIndex = 2;
        StudentDTO testStudent = actualStudents.get(testStudentIndex);
        Mockito.when(studentService.getStudentById(testStudent.getId()))
                .thenReturn(Optional.ofNullable(actualStudents.get(testStudentIndex)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/{id}", testStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(testStudent.getId()))
                .andExpect(jsonPath("$.firstname").value(testStudent.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(testStudent.getLastname()));
    }

    @Test
    void getStudentById_shouldReturnStatusBadRequest_whenStudentIdIsInvalid() throws Exception {
        int invalidStudentId = 200000;
        Mockito.when(studentService.getStudentById(invalidStudentId))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/{id}", invalidStudentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewStudent_shouldReturnStudentObject() throws Exception {
        StudentDTO newStudent = new StudentDTO(0, "First4", "Last4", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/students")
                        .content(objectMapper.writeValueAsString(newStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstname").value(newStudent.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(newStudent.getLastname()));
    }

    @Test
    void updateStudent_shouldReturnStudent_whenNewStudentIsCorrect() throws Exception {
        StudentDTO newStudent = new StudentDTO(0, "First4", "Last4", null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students")
                        .content(objectMapper.writeValueAsString(newStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstname").value(newStudent.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(newStudent.getLastname()));
    }

    @Test
    void updateStudent_shouldReturnBadRequst_whenNewStudentIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteStudent_shouldDeleteStudent_whenIdIsValid_andStudentHasNoStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/students/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }
}
