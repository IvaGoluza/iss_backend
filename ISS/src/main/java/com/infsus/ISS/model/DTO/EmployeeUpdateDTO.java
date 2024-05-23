package com.infsus.ISS.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeUpdateDTO {
    private Long idUser;
    private String name;
    private String email;
    private Date dateEnd;
    private Date dateStart;
    private String position;
    private Long idAktiv;
}
