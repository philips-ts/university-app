package com.tarasenko.universityapp.controller;

import com.tarasenko.universityapp.dto.SubjectDTO;
import com.tarasenko.universityapp.exception_handling.IncorrectDataException;
import com.tarasenko.universityapp.service.SubjectService;
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
@RequestMapping("/subjects")
public class SubjectRestController {
    
    private final SubjectService subjectService;

    @Autowired
    public SubjectRestController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }    


    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        List<SubjectDTO> subjectDTOs = subjectService.getAllSubjects();
        return subjectDTOs;
    }

    @GetMapping("/{id}")
    public SubjectDTO getSubjectById(@PathVariable int id) {
        Optional<SubjectDTO> subjectDtoOptional = subjectService.getSubjectById(id);
        if (subjectDtoOptional.isPresent()) {
            return subjectDtoOptional.get();
        } else {
            throw new IncorrectDataException("There is no subject with ID = " + id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDTO addNewSubject(@RequestBody SubjectDTO newSubject) {
        subjectService.saveSubject(newSubject);

        return newSubject;
    }

    @PutMapping
    public SubjectDTO updateSubject(@RequestBody SubjectDTO subjectDto) {
        subjectService.saveSubject(subjectDto);
        return subjectDto;
    }

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable int id) {
        subjectService.deleteSubjectById(id);

        return "Subject with ID " + id + " was deleted.";
    }
}
