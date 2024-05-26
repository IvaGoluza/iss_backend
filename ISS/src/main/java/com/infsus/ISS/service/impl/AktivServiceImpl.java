package com.infsus.ISS.service.impl;

import com.infsus.ISS.model.Aktiv;
import com.infsus.ISS.model.DTO.AktivCreateDTO;
import com.infsus.ISS.model.DTO.AktivDTO;
import com.infsus.ISS.model.DTO.EmployeeResponseDTO;
import com.infsus.ISS.model.Employee;
import com.infsus.ISS.model.SchoolYear;
import com.infsus.ISS.repository.AktivRepository;
import com.infsus.ISS.repository.SchoolYearRepository;
import com.infsus.ISS.service.AktivService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AktivServiceImpl implements AktivService {
    private final AktivRepository aktivRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<AktivDTO> getAllAktivs() {
        return aktivRepository.findAll().stream().sorted(Comparator.comparing(Aktiv::getIdAktiv)).map(aktiv -> modelMapper.map(aktiv, AktivDTO.class)).toList();
    }

    public AktivDTO createAktiv(AktivCreateDTO aktivCreateDTO) {
        Optional<SchoolYear> schoolYearOptional = schoolYearRepository.findById(1L);
        if (!schoolYearOptional.isPresent()) {
            throw new RuntimeException("SchoolYear with ID 1 not found");
        }
        SchoolYear schoolYear = schoolYearOptional.get();

        Aktiv aktiv = new Aktiv();
        aktiv.setAktivName(aktivCreateDTO.getAktivName());
        aktiv.setSchoolYear(schoolYear);

        Aktiv savedAktiv = aktivRepository.save(aktiv);
        return convertToDTO(savedAktiv);
    }

    private AktivDTO convertToDTO(Aktiv aktiv) {
        AktivDTO aktivDTO = new AktivDTO();
        aktivDTO.setAktivName(aktiv.getAktivName());
        return aktivDTO;
    }
}
