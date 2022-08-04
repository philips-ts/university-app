package com.tarasenko.universityapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarasenko.universityapp.dto.LessonInfoDTO;
import com.tarasenko.universityapp.service.LessonInfoService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = LessonInfoRestController.class)
class LessonInfoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonInfoService lessonInfoService;

    private static ObjectMapper objectMapper;

    private static List<LessonInfoDTO> actualLessonsInfo;


    @BeforeAll
    static void setUp() {
        LessonInfoDTO lessonInfo1 = new LessonInfoDTO(1, LocalTime.of(9, 0),
                LocalTime.of(10, 45));
        LessonInfoDTO lessonInfo2 = new LessonInfoDTO(2, LocalTime.of(11, 0),
                LocalTime.of(12, 45));
        LessonInfoDTO lessonInfo3 = new LessonInfoDTO(3, LocalTime.of(13, 0),
                LocalTime.of(14, 45));

        actualLessonsInfo = new ArrayList<>(List.of(lessonInfo1, lessonInfo2, lessonInfo3));

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }


    @Test
    void getAllLessonsInfo_shouldReturnAllLessonsInfo() throws Exception {
        Mockito.when(lessonInfoService.getAllLessonsInfo())
                .thenReturn(actualLessonsInfo);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/lessons_info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(actualLessonsInfo.size())))
                .andReturn();
    }

    @Test
    void getLessonInfoById_shouldReturnValidLessonInfo_whenIdIsValid() throws Exception {
        int testLessonInfoIndex = 2;
        LessonInfoDTO testLessonInfo = actualLessonsInfo.get(testLessonInfoIndex);
        Mockito.when(lessonInfoService.getLessonById(testLessonInfo.getId()))
                .thenReturn(Optional.ofNullable(actualLessonsInfo.get(testLessonInfoIndex)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lessons_info/{id}", testLessonInfo.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(testLessonInfo.getId()));
    }

    @Test
    void getLessonInfoById_shouldReturnStatusBadRequest_whenLessonInfoIdIsInvalid() throws Exception {
        int invalidLessonInfoId = 200000;
        Mockito.when(lessonInfoService.getLessonById(invalidLessonInfoId))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/lessons_info/{id}", invalidLessonInfoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewLessonInfo() throws Exception {
        LessonInfoDTO newLessonInfo = new LessonInfoDTO(0, LocalTime.of(19, 0),
                LocalTime.of(20, 45));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/lessons_info")
                        .content(objectMapper.writeValueAsString(newLessonInfo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateLessonInfo_shouldReturnLessonInfo_whenNewLessonInfoIsCorrect() throws Exception {
        LessonInfoDTO newLessonInfo = new LessonInfoDTO(0, LocalTime.of(19, 0),
                LocalTime.of(20, 45));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/lessons_info")
                        .content(objectMapper.writeValueAsString(newLessonInfo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(lessonInfoService, Mockito.times(1))
                .saveLesson(newLessonInfo);
    }

    @Test
    void updateLessonInfo_shouldReturnBadRequest_whenNewLessonInfoIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/lessons_info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteLessonInfo_shouldDeleteLessonInfo_whenIdIsValid_andLessonInfoHasNoStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/lessons_info/{id}", 1))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(lessonInfoService, Mockito.times(0))
                .saveLesson(Mockito.any());
    }
}
