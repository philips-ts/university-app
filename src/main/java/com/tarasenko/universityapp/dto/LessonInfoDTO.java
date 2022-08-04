package com.tarasenko.universityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonInfoDTO implements Serializable {
    private int id;

    private LocalTime startTime;

    private LocalTime endTime;
}
