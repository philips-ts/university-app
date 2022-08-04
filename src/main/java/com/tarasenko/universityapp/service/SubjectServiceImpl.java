package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.SubjectRepository;
import com.tarasenko.universityapp.dto.SubjectDTO;
import com.tarasenko.universityapp.entity.Subject;
import com.tarasenko.universityapp.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> allSubjects = subjectRepository.findAll();
        List<SubjectDTO> subjectDTOs = subjectMapper.toDtoList(allSubjects);

        return subjectDTOs;
    }

    @Override
    public Optional<SubjectDTO> getSubjectById(int id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if (subjectOptional.isPresent()) {
            SubjectDTO subjectDto = subjectMapper.toDto(subjectOptional.get());
            return Optional.of(subjectDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void saveSubject(SubjectDTO subjectDto) {
        if (subjectDto != null) {
            Subject subject = subjectMapper.toEntity(subjectDto);
            subjectRepository.save(subject);
        }
    }

    @Override
    public void deleteSubjectById(int id) {
        subjectRepository.deleteById(id);
    }
}
