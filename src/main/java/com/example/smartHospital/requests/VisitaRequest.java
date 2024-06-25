package com.example.smartHospital.requests;

import com.example.smartHospital.enums.Giorno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitaRequest {

    private Double prezzo;
    private Giorno giorno;
    private LocalDateTime orarioVisita;
    private Long paziente;
    private Long medico;

}
