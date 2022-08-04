package com.tarasenko.universityapp.mapper;

import com.tarasenko.universityapp.dto.GroupDTO;
import com.tarasenko.universityapp.entity.Group;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    Group toEntity(GroupDTO groupDTO);
    GroupDTO toDto(Group group);
    List<Group> toEntityList(List<GroupDTO> groupDTO);
    List<GroupDTO> toDtoList(List<Group> group);
}
