package com.tarasenko.universityapp.controller;

import com.tarasenko.universityapp.dto.TimetableDTO;
import com.tarasenko.universityapp.exception_handling.IncorrectDataException;
import com.tarasenko.universityapp.service.TimetableService;
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

@RestController
@RequestMapping("/timetable")
public class TimetableRestController {
    private final TimetableService timetableService;

    @Autowired
    public TimetableRestController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping
    public List<TimetableDTO> getAllTimetables() {
        return timetableService.getAllTimetable();
    }


    @GetMapping("/{id}")
    public TimetableDTO getTimetableById(@PathVariable int id) {
        Optional<TimetableDTO> timetableDtoOptional = timetableService.getTimetableById(id);
        if (timetableDtoOptional.isPresent()) {
            return timetableDtoOptional.get();
        } else {
            throw new IncorrectDataException("There is no data with ID = " + id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimetableDTO addNewTimetable(@RequestBody TimetableDTO newTimetableDTO) {
        timetableService.saveTimetable(newTimetableDTO);

        return newTimetableDTO;
    }

    @PutMapping
    public TimetableDTO updateTimetable(@RequestBody TimetableDTO timetableDto) {
        timetableService.saveTimetable(timetableDto);
        return timetableDto;
    }

    @DeleteMapping("/{id}")
    public String deleteTimetable(@PathVariable int id) {
        timetableService.deleteTimetableById(id);

        return "Timetable with ID " + id + " was deleted.";
    }
}
