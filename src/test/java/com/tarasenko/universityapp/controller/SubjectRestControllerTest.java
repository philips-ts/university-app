package com.tarasenko.universityapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarasenko.universityapp.dto.SubjectDTO;
import com.tarasenko.universityapp.service.SubjectService;
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
@WebMvcTest(value = SubjectRestController.class)
class SubjectRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static List<SubjectDTO> actualSubjects;


    @BeforeAll
    static void setUp() {
        SubjectDTO subject1 = new SubjectDTO(1, "Math");
        SubjectDTO subject2 = new SubjectDTO(2, "Music");
        SubjectDTO subject3 = new SubjectDTO(3, "Economics");

        actualSubjects = new ArrayList<>(List.of(subject1, subject2, subject3));
    }


    @Test
    void getAllSubjects_shouldReturnAllSubjects() throws Exception {
        Mockito.when(subjectService.getAllSubjects())
                .thenReturn(actualSubjects);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/subjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(actualSubjects.size())))
                .andExpect(jsonPath("$[0].name", is(actualSubjects.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(actualSubjects.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(actualSubjects.get(2).getName())));
    }

    @Test
    void getSubjectById_shouldReturnValidSubject_whenSubjectIdIsValid() throws Exception {
        int testSubjectIndex = 2;
        SubjectDTO testSubject = actualSubjects.get(testSubjectIndex);
        Mockito.when(subjectService.getSubjectById(testSubject.getId()))
                .thenReturn(Optional.ofNullable(actualSubjects.get(testSubjectIndex)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/subjects/{id}", testSubject.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(testSubject.getId()))
                .andExpect(jsonPath("$.name").value(testSubject.getName()));
    }

    @Test
    void getSubjectById_shouldReturnStatusBadRequest_whenSubjectIdIsInvalid() throws Exception {
        int invalidSubjectId = 200000;
        Mockito.when(subjectService.getSubjectById(invalidSubjectId))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/subjects/{id}", invalidSubjectId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewSubject_shouldReturnSubjectObject() throws Exception {
        SubjectDTO newSubject = new SubjectDTO(0, "Physics");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/subjects")
                        .content(objectMapper.writeValueAsString(newSubject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(newSubject.getName()));
    }

    @Test
    void updateSubject_shouldReturnSubject_whenNewSubjectIsCorrect() throws Exception {
        SubjectDTO newSubject = new SubjectDTO(0, "Physics");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/subjects")
                        .content(objectMapper.writeValueAsString(newSubject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(newSubject.getName()));
    }

    @Test
    void updateSubject_shouldReturnBadRequst_whenNewSubjectIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteSubject_shouldDeleteSubject_whenIdIsValid_andSubjectHasNoSubjects() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/subjects/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }
}