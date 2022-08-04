package com.tarasenko.universityapp.mapper;

import com.tarasenko.universityapp.dto.LessonInfoDTO;
import com.tarasenko.universityapp.entity.LessonInfo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface LessonInfoMapper {
    LessonInfo toEntity(LessonInfoDTO groupDTO);
    LessonInfoDTO toDto(LessonInfo group);
    List<LessonInfo> toEntityList(List<LessonInfoDTO> groupDTO);
    List<LessonInfoDTO> toDtoList(List<LessonInfo> group);
}
