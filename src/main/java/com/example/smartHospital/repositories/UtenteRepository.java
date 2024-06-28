package com.example.smartHospital.repositories;

import com.example.smartHospital.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findUtenteByEmail (String email);
    List<Utente> findMediciBySpecializzazioneId (Long specializzazione_id);
    Utente findMedicoBySpecializzazioneId (Long specializzazione_id);
}
