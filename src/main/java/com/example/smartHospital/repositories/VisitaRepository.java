package com.example.smartHospital.repositories;

import com.example.smartHospital.entities.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {

    @Query(value = "SELECT referto FROM visita WHERE id =:id_visita", nativeQuery = true)
    String getFilePath (@Param("id_visita") Long id_visita);

    @Query(value = "SELECT id FROM visita WHERE referto != null", nativeQuery = true)
    List<Long> getAllVisiteConReferto ();

    @Query(value = "SELECT * FROM visita WHERE paziente_id =:id_Paziente AND referto = null", nativeQuery = true)
    List<Visita> getAllVisitePrenotate (@Param ("id_Paziente") Long id_Paziente);

    @Query(value = "SELECT * FROM visita WHERE paziente_id =:id_Paziente AND referto != null", nativeQuery = true)
    List<Visita> getAllVisitePassate (@Param ("id_Paziente") Long id_Paziente);

    @Query(value = "SELECT referto FROM visita WHERE paziente_id =:id_paziente", nativeQuery = true)
    String getFilePathConIdPaziente (@Param("id_paziente") Long id_paziente);
}
