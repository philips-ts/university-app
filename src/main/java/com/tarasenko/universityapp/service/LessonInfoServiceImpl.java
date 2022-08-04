package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.LessonInfoRepository;
import com.tarasenko.universityapp.dto.LessonInfoDTO;
import com.tarasenko.universityapp.entity.LessonInfo;
import com.tarasenko.universityapp.mapper.LessonInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonInfoServiceImpl implements LessonInfoService {

    private final LessonInfoRepository lessonInfoRepository;

    private final LessonInfoMapper lessonInfoMapper;

    @Autowired
    public LessonInfoServiceImpl(LessonInfoRepository lessonInfoRepository, LessonInfoMapper lessonInfoMapper) {
        this.lessonInfoRepository = lessonInfoRepository;
        this.lessonInfoMapper = lessonInfoMapper;
    }

    @Override
    public List<LessonInfoDTO> getAllLessonsInfo() {
        List<LessonInfo> allLessonsInfo = lessonInfoRepository.findAll();
        List<LessonInfoDTO> lessonInfoDTOS = lessonInfoMapper.toDtoList(allLessonsInfo);

        return lessonInfoDTOS;
    }

    @Override
    public Optional<LessonInfoDTO> getLessonById(int id) {
        Optional<LessonInfo> lessonInfoOptional = lessonInfoRepository.findById(id);
        if (lessonInfoOptional.isPresent()) {
            LessonInfoDTO lessonInfoDto = lessonInfoMapper.toDto(lessonInfoOptional.get());
            return Optional.of(lessonInfoDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void saveLesson(LessonInfoDTO lessonInfoDto) {
        if (lessonInfoDto != null) {
            LessonInfo lessonInfo = lessonInfoMapper.toEntity(lessonInfoDto);
            lessonInfoRepository.save(lessonInfo);
        }
    }

    @Override
    public void deleteLessonById(int id) {
        lessonInfoRepository.deleteById(id);
    }
}
