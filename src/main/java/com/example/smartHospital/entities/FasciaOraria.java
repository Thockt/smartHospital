package com.example.smartHospital.entities;

import com.example.smartHospital.enums.Giorno;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FasciaOraria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Giorno giorno;
    @Column(nullable = false)
    private LocalTime inizioServizio;
    @Column(nullable = false)
    @Check(constraints = "fineServizio AFTER inizioServizio")
    private LocalTime fineServizio;
    @Column(nullable = false)
    private LocalDateTime insertTime;
    @Column
    private LocalDateTime lastUpdate;
}
