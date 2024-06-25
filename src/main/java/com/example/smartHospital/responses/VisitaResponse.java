package com.example.smartHospital.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitaResponse {

    private Long id;
    private Double prezzo;
    private LocalDateTime orarioVisita;
    private LocalDateTime insertTime;
    private LocalDateTime lastUpdate;
    private Long paziente;
    private Long medico;
    private String referto;

}
