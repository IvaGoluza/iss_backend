package com.infsus.ISS.controller;

import com.infsus.ISS.model.DTO.AktivDTO;
import com.infsus.ISS.service.AktivService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/aktiv", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class AktivController {
    final private AktivService aktivService;

    public AktivController(AktivService aktivService) {
        this.aktivService = aktivService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AktivDTO>> getAllAktivs() {
        return ResponseEntity.ok(aktivService.getAllAktivs());
    }
}
