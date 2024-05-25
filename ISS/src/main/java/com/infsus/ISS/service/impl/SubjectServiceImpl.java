package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.Aktiv;
import com.infsus.ISS.model.DTO.AktivDTO;
import com.infsus.ISS.model.DTO.SubjectResponseDTO;
import com.infsus.ISS.model.Subject;
import com.infsus.ISS.repository.SubjectRepository;
import com.infsus.ISS.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<SubjectResponseDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().sorted(Comparator.comparing(Subject::getIdSubject)).map(subject -> modelMapper.map(subject, SubjectResponseDTO.class)).toList();
    }
}
