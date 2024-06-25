package com.example.smartHospital.repositories;

import com.example.smartHospital.entities.Specializzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializzazioneRepository extends JpaRepository<Specializzazione, Long> {
}
