package com.example.smartHospital.repositories;

import com.example.smartHospital.entities.FasciaOraria;
import com.example.smartHospital.enums.Giorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FasciaOrariaRepository extends JpaRepository<FasciaOraria, Long> {

    FasciaOraria findByGiorno (Giorno day);
}
