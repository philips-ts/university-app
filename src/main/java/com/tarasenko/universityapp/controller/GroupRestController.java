package com.tarasenko.universityapp.controller;

import com.tarasenko.universityapp.dto.GroupDTO;
import com.tarasenko.universityapp.exception_handling.IncorrectDataException;
import com.tarasenko.universityapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/groups")
@RestController
public class GroupRestController {
    private final GroupService groupService;

    @Autowired
    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDTO> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public GroupDTO getGroupById(@PathVariable int id) {
        Optional<GroupDTO> groupDtoOptional = groupService.getGroupById(id);
        if (groupDtoOptional.isPresent()) {
            return groupDtoOptional.get();
        } else {
            throw new IncorrectDataException("There is no group with ID = " + id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO addNewGroup(@RequestBody GroupDTO newGroupDTO) {
        groupService.saveGroup(newGroupDTO);

        return newGroupDTO;
    }

    @PutMapping
    public GroupDTO updateGroup(@RequestBody GroupDTO groupDto) {
        groupService.saveGroup(groupDto);

        return groupDto;
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable int id) {
        groupService.deleteGroupById(id);

        return "Group with ID " + id + " was deleted.";
    }
}
