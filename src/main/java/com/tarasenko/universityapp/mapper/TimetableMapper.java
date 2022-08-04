package com.tarasenko.universityapp.mapper;


import com.tarasenko.universityapp.dto.TimetableDTO;
import com.tarasenko.universityapp.entity.Timetable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimetableMapper {
    Timetable toEntity(TimetableDTO groupDTO);
    TimetableDTO toDto(Timetable group);
    List<Timetable> toEntityList(List<TimetableDTO> groupDTO);
    List<TimetableDTO> toDtoList(List<Timetable> group);
}
