package com.example.smartHospital.responses;

import com.example.smartHospital.enums.Giorno;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FasciaOrariaResponse {

    private Long id;
    private Giorno giorno;
    private LocalTime inizioServizio;
    private LocalTime fineServizio;
    private LocalDateTime insertTime;
    private LocalDateTime lastUpdate;
}
