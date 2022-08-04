package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.GroupRepository;
import com.tarasenko.universityapp.dto.GroupDTO;
import com.tarasenko.universityapp.entity.Group;
import com.tarasenko.universityapp.mapper.GroupMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupServiceImpl groupService;

    private static List<Group> actualGroups;
    private static List<GroupDTO> actualGroupDtos;

    @BeforeEach
    void setUp() {
        Group group1 = new Group("666111");
        group1.setId(1);
        Group group2 = new Group("661116");
        group2.setId(2);
        Group group3 = new Group("677111");
        group3.setId(3);

        actualGroups = new ArrayList<>(List.of(group1, group2, group3));

        GroupDTO groupDto1 = new GroupDTO(group1.getId(), group1.getName());
        GroupDTO groupDto2 = new GroupDTO(group2.getId(), group2.getName());
        GroupDTO groupDto3 = new GroupDTO(group3.getId(), group3.getName());

        actualGroups = new ArrayList<>(List.of(group1, group2, group3));
        actualGroupDtos = new ArrayList<>(List.of(groupDto1, groupDto2, groupDto3));
   }

    @Test
    void getAllGroups_shouldReturnListOfGroups() {
        Mockito.when(groupRepository.findAll())
                .thenReturn(actualGroups);
        Mockito.when(groupMapper.toDtoList(anyList()))
                .thenReturn(actualGroupDtos);

        List<GroupDTO> expectedGroupsDto = groupService.getAllGroups();

        assertThat(expectedGroupsDto).isNotNull();
        assertThat(expectedGroupsDto.size()).isEqualTo(actualGroups.size());
    }

    @Test
    void getGroupById_shouldReturnCorrectGroup_whenIdIsCorrect() {
        Group actualGroup = actualGroups.get(1);
        int testId = actualGroup.getId();
        GroupDTO groupDto = new GroupDTO(testId, actualGroup.getName());

        Mockito.when(groupRepository.findById(testId))
                .thenReturn(Optional.of(actualGroup));
        Mockito.when(groupMapper.toDto(actualGroup))
                .thenReturn(groupDto);

        Optional<GroupDTO> expectedGroupDto = groupService.getGroupById(testId);


        assertThat(expectedGroupDto.isPresent()).isTrue();
        assertThat(actualGroup.getName()).isEqualTo(expectedGroupDto.get().getName());
    }

    @Test
    void saveGroup_shouldBeInvokedRepoAndMapperMethods_whenInputGroupIsCorrect() {
        GroupDTO newGroupDTO = new GroupDTO(0, "333111");
        groupService.saveGroup(newGroupDTO);

        verify(groupRepository, times(1)).save(any());
    }

    @Test
    void saveGroup_shouldBeInvokedRepoMethod_whenInputGroupIsIncorrect() {
        groupService.saveGroup(null);
        verify(groupRepository, never()).save(any());
    }

    @Test
    void deleteGroupById_shouldBeInvokedRepoMethod() {
        int groupIdToDelete = 5;
        groupService.deleteGroupById(groupIdToDelete);

        verify(groupRepository, times(1)).deleteById(groupIdToDelete);
    }
}