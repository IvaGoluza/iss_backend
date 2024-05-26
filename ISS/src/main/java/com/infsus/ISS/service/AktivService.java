package com.infsus.ISS.service;

import com.infsus.ISS.model.DTO.AktivCreateDTO;
import com.infsus.ISS.model.DTO.AktivDTO;

import java.util.List;

public interface AktivService {
    List<AktivDTO> getAllAktivs();
    public AktivDTO createAktiv(AktivCreateDTO aktivCreateDTO);
}
