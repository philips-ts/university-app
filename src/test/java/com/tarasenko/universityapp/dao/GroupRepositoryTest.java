package com.tarasenko.universityapp.dao;


import com.tarasenko.universityapp.entity.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void getAllGroups_shouldReturnAllGroups() {
        List<Group> expectedGroups = groupRepository.findAll();

        assertThat(expectedGroups.size()).isEqualTo(4);
    }

    @Test
    public void getById_shouldReturnGroup_whenIdIsCorrect() {
        int correctId = 2;
        Optional<Group> expectedGroup = groupRepository.findById(correctId);

        assertThat(expectedGroup.isPresent()).isTrue();
    }

    @Test
    public void getById_shouldReturnGroup_whenIdIsIncorrect() {
        int incorrectId = 99999;
        Optional<Group> expectedGroup = groupRepository.findById(incorrectId);

        assertThat(expectedGroup.isPresent()).isFalse();
    }



    @Test
    @Rollback
    public void deleteById_shouldDeleteGroupFromDB() {
        int idToDelete = 4;
        groupRepository.deleteById(idToDelete);

        Optional<Group> expectedGroup = groupRepository.findById(idToDelete);
        assertThat(expectedGroup.isPresent()).isFalse();
    }
}
