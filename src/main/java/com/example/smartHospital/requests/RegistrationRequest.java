package com.example.smartHospital.requests;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    private String nome;
    private String cognome;
    private LocalDateTime dataNascita;
    private String indirizzo;
    private String telefono;
    private String email;
    private String password;
}
