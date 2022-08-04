package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.GroupRepository;
import com.tarasenko.universityapp.dto.GroupDTO;
import com.tarasenko.universityapp.entity.Group;
import com.tarasenko.universityapp.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> allGroups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = groupMapper.toDtoList(allGroups);
        return groupDTOs;
    }


    @Override
    public Optional<GroupDTO> getGroupById(int id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            GroupDTO groupDto = groupMapper.toDto(groupOptional.get());
            return Optional.of(groupDto);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public void saveGroup(GroupDTO groupDto){
        if (groupDto != null) {
            Group group = groupMapper.toEntity(groupDto);
            groupRepository.save(group);
        }
    }

    @Override
    public void deleteGroupById(int id) {
        groupRepository.deleteById(id);
    }
}
