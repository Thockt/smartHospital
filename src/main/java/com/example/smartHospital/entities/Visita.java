package com.example.smartHospital.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Check(constraints = "prezzo > 0")
    private Double prezzo;
    @Column(nullable = false)
    private LocalDateTime orarioVisita;
    @Column(nullable = false)
    private LocalDateTime insertTime;
    @Column
    private LocalDateTime lastUpdate;
    @OneToOne
    @Check(constraints = "utente.role = PAZIENTE")
    private Utente paziente;
    @OneToOne
    @Check(constraints = "utente.role = MEDICO")
    private Utente medico;
    @Column
    private String referto;

}
