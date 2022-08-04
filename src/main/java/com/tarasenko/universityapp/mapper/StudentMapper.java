package com.tarasenko.universityapp.mapper;

import com.tarasenko.universityapp.dto.StudentDTO;
import com.tarasenko.universityapp.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentDTO groupDTO);
    StudentDTO toDto(Student group);
    List<Student> toEntityList(List<StudentDTO> groupDTO);
    List<StudentDTO> toDtoList(List<Student> group);
}
