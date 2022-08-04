package com.tarasenko.universityapp.controller;

import com.tarasenko.universityapp.dto.LessonInfoDTO;
import com.tarasenko.universityapp.exception_handling.IncorrectDataException;
import com.tarasenko.universityapp.service.LessonInfoService;
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
@RequestMapping("/lessons_info")
public class LessonInfoRestController {
    private final LessonInfoService lessonInfoService;

    @Autowired
    public LessonInfoRestController(LessonInfoService lessonInfoService) {
        this.lessonInfoService = lessonInfoService;
    }

    @GetMapping
    public List<LessonInfoDTO> getAllLessonInfo() {
        return lessonInfoService.getAllLessonsInfo();
    }

    @GetMapping("/{id}")
    public LessonInfoDTO getLessonInfoById(@PathVariable int id) {
        Optional<LessonInfoDTO> lessonInfoDtoOptional = lessonInfoService.getLessonById(id);
        if (lessonInfoDtoOptional.isPresent()) {
            return lessonInfoDtoOptional.get();
        } else {
            throw new IncorrectDataException("There is no student with ID = " + id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonInfoDTO addNewLessonInfo(@RequestBody LessonInfoDTO newLessonInfoDTO) {
        lessonInfoService.saveLesson(newLessonInfoDTO);

        return newLessonInfoDTO;
    }

    @PutMapping
    public LessonInfoDTO updateLessonInfo(@RequestBody LessonInfoDTO lessonInfoDto) {
        lessonInfoService.saveLesson(lessonInfoDto);

        return lessonInfoDto;
    }

    @DeleteMapping("/{id}")
    public String deleteLessonInfo(@PathVariable int id) {
        lessonInfoService.deleteLessonById(id);

        return "LessonInfo with ID " + id + " was deleted.";
    }
}
