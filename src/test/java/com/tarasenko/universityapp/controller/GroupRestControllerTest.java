package com.tarasenko.universityapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarasenko.universityapp.dto.GroupDTO;
import com.tarasenko.universityapp.service.GroupService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GroupRestController.class)
class GroupRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static List<GroupDTO> actualGroups;


    @BeforeAll
    static void setUp() {
        GroupDTO group1 = new GroupDTO(1, "666111");
        GroupDTO group2 = new GroupDTO(2, "661116");
        GroupDTO group3 = new GroupDTO(3, "677111");

        actualGroups = new ArrayList<>(List.of(group1, group2, group3));
    }


    @Test
    void getAllGroups_shouldReturnAllGroups() throws Exception {
        Mockito.when(groupService.getAllGroups())
                .thenReturn(actualGroups);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(actualGroups.size())))
                .andExpect(jsonPath("$[0].name", is(actualGroups.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(actualGroups.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(actualGroups.get(2).getName())));

    }

    @Test
    void getGroupById_shouldReturnValidGroup_whenGroupIdIsValid() throws Exception {
        int testGroupIndex = 2;
        GroupDTO testGroup = actualGroups.get(testGroupIndex);
        Mockito.when(groupService.getGroupById(testGroup.getId()))
                .thenReturn(Optional.ofNullable(actualGroups.get(testGroupIndex)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/{id}", testGroup.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(testGroup.getId()))
                .andExpect(jsonPath("$.name").value(testGroup.getName()));
    }

    @Test
    void getGroupById_shouldReturnStatusBadRequest_whenGroupIdIsInvalid() throws Exception {
        int invalidGroupId = 200000;
        Mockito.when(groupService.getGroupById(invalidGroupId))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/{id}", invalidGroupId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addNewGroup() throws Exception {
        GroupDTO newGroup = new GroupDTO(0, "854000");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups")
                        .content(objectMapper.writeValueAsString(newGroup))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(newGroup.getName()));
    }

    @Test
    void updateGroup_shouldReturnGroup_whenNewGroupIsCorrect() throws Exception {
        GroupDTO newGroup = new GroupDTO(0, "854000");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups")
                        .content(objectMapper.writeValueAsString(newGroup))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(newGroup.getName()));
    }

    @Test
    void updateGroup_shouldReturnBadRequst_whenNewGroupIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteGroup_shouldDeleteGroup_whenIdIsValid_andGroupHasNoStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/groups/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }
}
