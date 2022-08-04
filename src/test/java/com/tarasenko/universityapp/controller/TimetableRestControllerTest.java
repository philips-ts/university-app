package com.tarasenko.universityapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarasenko.universityapp.dto.TimetableDTO;
import com.tarasenko.universityapp.service.TimetableService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TimetableRestController.class)
class TimetableRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimetableService timetableService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static List<TimetableDTO> actualTimetableList;


    @BeforeAll
    static void setUp() {
        TimetableDTO timetable1 = new TimetableDTO(1, null,
                LocalDate.of(2022, 10, 1),1, 55, null);
        TimetableDTO timetable2 = new TimetableDTO(2, null,
                LocalDate.of(2022, 10, 2),2, 66, null);
        TimetableDTO timetable3 = new TimetableDTO(3, null,
                LocalDate.of(2022, 10, 3),3, 77, null);

        actualTimetableList = new ArrayList<>(List.of(timetable1, timetable2, timetable3));

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }


    @Test
    void getAllTimetables_shouldReturnAllTimetables() throws Exception {
        Mockito.when(timetableService.getAllTimetable())
                .thenReturn(actualTimetableList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/timetable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(actualTimetableList.size())));
    }

    @Test
    void getTimetableById_shouldReturnValidTimetable_whenTimetableIdIsValid() throws Exception {
        int testTimetableIndex = 2;
        TimetableDTO testTimetable = actualTimetableList.get(testTimetableIndex);
        Mockito.when(timetableService.getTimetableById(testTimetable.getId()))
                .thenReturn(Optional.ofNullable(actualTimetableList.get(testTimetableIndex)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/timetable/{id}", testTimetable.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(testTimetable.getId()));
    }

    @Test
    void getTimetableById_shouldReturnStatusBadRequest_whenTimetableIdIsInvalid() throws Exception {
        int invalidTimetableId = 200000;
        Mockito.when(timetableService.getTimetableById(invalidTimetableId))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/timetable/{id}", invalidTimetableId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewTimetable_shouldReturnTimetableObject() throws Exception {
        TimetableDTO newTimetable = new TimetableDTO(0, null,
                LocalDate.of(2022, 11, 10),
                2, 11, null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/timetable")
                        .content(objectMapper.writeValueAsString(newTimetable))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateTimetable_shouldReturnTimetable_whenNewTimetableIsCorrect() throws Exception {
        TimetableDTO newTimetable = new TimetableDTO(0, null,
                LocalDate.of(2022, 12, 8),
                1, 93, null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/timetable")
                        .content(objectMapper.writeValueAsString(newTimetable))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateTimetable_shouldReturnBadRequst_whenNewTimetableIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/timetable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteTimetable_shouldDeleteTimetable_whenIdIsValid_andTimetableHasNoTimetables() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/timetable/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }
}