package com.infsus.ISS.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeWithAktivDTO {
    private EmployeeResponseDTO employee;
    private List<AktivDTO> aktivList;
}
