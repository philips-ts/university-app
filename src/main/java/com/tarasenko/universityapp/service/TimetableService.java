package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dto.TimetableDTO;

import java.util.List;
import java.util.Optional;

public interface TimetableService {
    List<TimetableDTO> getAllTimetable();
    Optional<TimetableDTO> getTimetableById(int id);
    void saveTimetable(TimetableDTO timetableDto);
    void deleteTimetableById(int id);
}
