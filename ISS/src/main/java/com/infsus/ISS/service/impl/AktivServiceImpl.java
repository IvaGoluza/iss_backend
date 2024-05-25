package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.Aktiv;
import com.infsus.ISS.model.DTO.AktivDTO;
import com.infsus.ISS.model.DTO.EmployeeResponseDTO;
import com.infsus.ISS.model.Employee;
import com.infsus.ISS.repository.AktivRepository;
import com.infsus.ISS.service.AktivService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AktivServiceImpl implements AktivService {
    private final AktivRepository aktivRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<AktivDTO> getAllAktivs() {
        return aktivRepository.findAll().stream().sorted(Comparator.comparing(Aktiv::getIdAktiv)).map(aktiv -> modelMapper.map(aktiv, AktivDTO.class)).toList();
    }
}
