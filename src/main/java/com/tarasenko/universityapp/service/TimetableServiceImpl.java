package com.tarasenko.universityapp.service;

import com.tarasenko.universityapp.dao.TimetableRepository;
import com.tarasenko.universityapp.dto.TimetableDTO;
import com.tarasenko.universityapp.entity.Timetable;
import com.tarasenko.universityapp.mapper.TimetableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;

    private final TimetableMapper timetableMapper;

    @Autowired
    public TimetableServiceImpl(TimetableRepository timetableRepository,
                                TimetableMapper timetableMapper) {
        this.timetableRepository = timetableRepository;
        this.timetableMapper = timetableMapper;
    }

    @Override
    public List<TimetableDTO> getAllTimetable() {
        List<Timetable> allTimetable = timetableRepository.findAll();
        List<TimetableDTO> timetableDTOs = timetableMapper.toDtoList(allTimetable);

        return timetableDTOs;
    }

    @Override
    public Optional<TimetableDTO> getTimetableById(int id) {
        Optional<Timetable> timetableOptional = timetableRepository.findById(id);
        if (timetableOptional.isPresent()) {
            TimetableDTO timetableDto = timetableMapper.toDto(timetableOptional.get());
            return Optional.of(timetableDto);
        } else {
            return Optional.empty();
        }
    }
    @Override
    public void saveTimetable(TimetableDTO timetableDto) {
        if (timetableDto != null) {
            Timetable timetable = timetableMapper.toEntity(timetableDto);
            timetableRepository.save(timetable);
        }
    }

    @Override
    public void deleteTimetableById(int id) {
        timetableRepository.deleteById(id);
    }
}
