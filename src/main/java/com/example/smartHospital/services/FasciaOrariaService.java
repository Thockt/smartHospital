package com.example.smartHospital.services;

import com.example.smartHospital.entities.FasciaOraria;
import com.example.smartHospital.exceptions.FasciaOrariaNotFoundException;
import com.example.smartHospital.repositories.FasciaOrariaRepository;
import com.example.smartHospital.requests.FasciaOrariaRequest;
import com.example.smartHospital.requests.VisitaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FasciaOrariaService {

    @Autowired
    private FasciaOrariaRepository fasciaOrariaRepository;

    public FasciaOraria getFasciaOrariaById (Long id) throws FasciaOrariaNotFoundException {
        Optional<FasciaOraria> fasciaOrariaOptional = fasciaOrariaRepository.findById(id);
        if (fasciaOrariaOptional.isEmpty()) throw new FasciaOrariaNotFoundException();
        return fasciaOrariaOptional.get();
    }

    public List<FasciaOraria> getAll () {
        return fasciaOrariaRepository.findAll();
    }

    public FasciaOraria create (FasciaOrariaRequest request) {
        FasciaOraria fasciaOraria = convertFromDTO(request);
        fasciaOraria.setInsertTime(LocalDateTime.now());
        return fasciaOrariaRepository.saveAndFlush(fasciaOraria);
    }

    public FasciaOraria update (Long id, FasciaOraria newFasciaOraria) throws FasciaOrariaNotFoundException {
        getFasciaOrariaById(id);
        FasciaOraria fasciaOraria = FasciaOraria.builder()
                .id(id)
                .giorno(newFasciaOraria.getGiorno())
                .inizioServizio(newFasciaOraria.getInizioServizio())
                .fineServizio(newFasciaOraria.getFineServizio())
                .insertTime(newFasciaOraria.getInsertTime())
                .lastUpdate(LocalDateTime.now())
                .build();
        return fasciaOrariaRepository.saveAndFlush(fasciaOraria);
    }

    public void deleteById (Long id) throws FasciaOrariaNotFoundException {
        getFasciaOrariaById(id);
        fasciaOrariaRepository.deleteById(id);
    }

    /*public boolean isOrarioValido (VisitaRequest request) {

    }*/

    private FasciaOraria convertFromDTO (FasciaOrariaRequest request) {
        return FasciaOraria.builder()
                .giorno(request.getGiorno())
                .inizioServizio(request.getInizioServizio().toLocalTime())
                .fineServizio(request.getFineServizio().toLocalTime())
                .build();
    }
}
