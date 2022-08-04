package com.tarasenko.universityapp.mapper;


import com.tarasenko.universityapp.dto.SubjectDTO;
import com.tarasenko.universityapp.entity.Subject;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    Subject toEntity(SubjectDTO groupDTO);
    SubjectDTO toDto(Subject group);
    List<Subject> toEntityList(List<SubjectDTO> groupDTO);
    List<SubjectDTO> toDtoList(List<Subject> group);
}
