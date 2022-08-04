package com.tarasenko.universityapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableDTO {
    private int id;

    private SubjectDTO subject;

    private LocalDate classDate;

    private int lessonNumber;

    private int classRoom;

    private GroupDTO group;
}
