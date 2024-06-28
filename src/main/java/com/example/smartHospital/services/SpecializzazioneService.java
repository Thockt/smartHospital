package com.example.smartHospital.services;

import com.example.smartHospital.entities.FasciaOraria;
import com.example.smartHospital.entities.Specializzazione;
import com.example.smartHospital.entities.Utente;
import com.example.smartHospital.exceptions.SpecializzazioneNotFoundException;
import com.example.smartHospital.repositories.SpecializzazioneRepository;
import com.example.smartHospital.repositories.UtenteRepository;
import com.example.smartHospital.requests.FasciaOrariaRequest;
import com.example.smartHospital.requests.SpecializzazioneRequest;
import com.example.smartHospital.requests.VisitaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpecializzazioneService {

    @Autowired
    private SpecializzazioneRepository specializzazioneRepository;
    @Autowired
    private FasciaOrariaService fasciaOrariaService;
    @Autowired
    private UtenteRepository utenteRepository;

    public Specializzazione getSpecializzazioneById (Long id) throws SpecializzazioneNotFoundException {
        Optional<Specializzazione> specializzazioneOptional = specializzazioneRepository.findById(id);
        if(specializzazioneOptional.isEmpty()) throw new SpecializzazioneNotFoundException();
        return specializzazioneOptional.get();
    }

    public List<Specializzazione> getAllSpecializzazioni (){
        return specializzazioneRepository.findAll();
    }

    public Specializzazione create (SpecializzazioneRequest request) {
        Specializzazione specializzazione = convertFromDTO(request);
        specializzazione.setInsertTime(LocalDateTime.now());
        return specializzazioneRepository.saveAndFlush(specializzazione);
    }

    public Specializzazione update (Long id, Specializzazione newSpecializzazione) throws SpecializzazioneNotFoundException {
        getSpecializzazioneById(id);
        Specializzazione specializzazione = Specializzazione.builder()
                .id(id)
                .titolo(newSpecializzazione.getTitolo())
                .fasceOrarie(newSpecializzazione.getFasceOrarie())
                .insertTime(newSpecializzazione.getInsertTime())
                .lastUpdate(LocalDateTime.now())
                .build();
        return specializzazioneRepository.saveAndFlush(specializzazione);
    }

    public void deleteById (Long id) throws SpecializzazioneNotFoundException {
        getSpecializzazioneById(id);
        specializzazioneRepository.deleteById(id);
    }

    public void addFasciaOraria (Long id, FasciaOrariaRequest request) throws SpecializzazioneNotFoundException {
        Specializzazione specializzazione = getSpecializzazioneById(id);
        List<FasciaOraria> fascieOrarie = specializzazione.getFasceOrarie();
        fascieOrarie.add(fasciaOrariaService.create(request));
        specializzazione.setFasceOrarie(fascieOrarie);
        specializzazioneRepository.saveAndFlush(specializzazione);
    }

    private Specializzazione convertFromDTO (SpecializzazioneRequest request) {
        return Specializzazione.builder()
                .titolo(request.getTitolo())
                .build();
    }
}
