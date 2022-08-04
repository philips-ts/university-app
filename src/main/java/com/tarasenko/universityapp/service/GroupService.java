package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dto.GroupDTO;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupDTO> getAllGroups();
    Optional<GroupDTO> getGroupById(int id);
    void saveGroup(GroupDTO groupDto);
    void deleteGroupById(int id);
}
