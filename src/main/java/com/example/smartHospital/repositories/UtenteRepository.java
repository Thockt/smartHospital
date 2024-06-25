package com.example.smartHospital.repositories;

import com.example.smartHospital.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findUtenteByEmail (String email);
}
