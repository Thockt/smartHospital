package com.example.smartHospital.responses;

import com.example.smartHospital.entities.Specializzazione;
import com.example.smartHospital.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtenteResponse {


    private Long id;
    private String nome;
    private String cognome;
    private LocalDateTime dataNascita;
    private String indirizzo;
    private String telefono;
    private String email;
    private Role role;
    private Long specializzazione;
    private LocalDateTime insertTime;
    private LocalDateTime lastUpdate;

}
