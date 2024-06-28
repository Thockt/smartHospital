package com.example.smartHospital.services;

import com.example.smartHospital.entities.Visita;
import com.example.smartHospital.exceptions.UtenteNotFoundException;
import com.example.smartHospital.exceptions.VisitaNotFoundException;
import com.example.smartHospital.repositories.UtenteRepository;
import com.example.smartHospital.repositories.VisitaRepository;
import com.example.smartHospital.requests.VisitaRequest;
import com.example.smartHospital.responses.VisitaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Visita getVisitaById (Long id) throws VisitaNotFoundException {
        Optional<Visita> visitaOptional = visitaRepository.findById(id);
        if(visitaOptional.isEmpty()) throw new VisitaNotFoundException();
        return visitaOptional.get();
    }

    public VisitaResponse getVisitaResponseById (Long id) throws VisitaNotFoundException {
        Optional<Visita> visitaOptional = visitaRepository.findById(id);
        if(visitaOptional.isEmpty()) throw new VisitaNotFoundException();
        return convertFromEntity(visitaOptional.get());
    }

    public List<Visita> getAll () {
        return visitaRepository.findAll();
    }

    public VisitaResponse create (VisitaRequest request) throws UtenteNotFoundException {
        Visita visita = convertFromDTO(request);
        visita.setInsertTime(LocalDateTime.now());
        return convertFromEntity(visitaRepository.saveAndFlush(visita));
    }

    public VisitaResponse update (Long id,Visita newVisita) throws VisitaNotFoundException {
        getVisitaById(id);
        Visita visita = Visita.builder()
                .id(id)
                .prezzo(newVisita.getPrezzo())
                .orarioVisita(newVisita.getOrarioVisita())
                .insertTime(newVisita.getInsertTime())
                .lastUpdate(LocalDateTime.now())
                .paziente(newVisita.getPaziente())
                .medico(newVisita.getMedico())
                .build();
        return convertFromEntity(visitaRepository.saveAndFlush(visita));
    }

    public void deleteById (Long id) throws VisitaNotFoundException {
        getVisitaById(id);
        visitaRepository.deleteById(id);
    }

    public void  salvaVisita (Visita visita) {
        visitaRepository.saveAndFlush(visita);
    }

    public String getPath (Long idVisita) {
        return visitaRepository.getFilePath(idVisita);
    }

    public String getPathByPaziente (Long idPaziente) {
        return visitaRepository.getFilePathConIdPaziente(idPaziente);
    }

    public List<Long> getAllVisiteConReferto () {
        return visitaRepository.getAllVisiteConReferto();
    }

    public List<Visita> getAllVisitePrenotate (Long idPaziente) {
        return visitaRepository.getAllVisitePrenotate(idPaziente);
    }

    public List<Visita> getAllVisitePassate (Long idPaziente) {
        return visitaRepository.getAllVisitePassate(idPaziente);
    }

   private Visita convertFromDTO (VisitaRequest request) throws UtenteNotFoundException {
        return Visita.builder()
                .prezzo(request.getPrezzo())
                .orarioVisita(request.getOrarioVisita())
                .paziente(utenteRepository.getReferenceById(request.getPaziente()))
                .medico(utenteRepository.getReferenceById(request.getMedico()))
                .build();
   }

   private VisitaResponse convertFromEntity (Visita visita) {
        return VisitaResponse.builder()
                .id(visita.getId())
                .prezzo(visita.getPrezzo())
                .orarioVisita(visita.getOrarioVisita())
                .insertTime(visita.getInsertTime())
                .lastUpdate(visita.getLastUpdate())
                .paziente(visita.getPaziente().getId())
                .medico(visita.getMedico().getId())
                .referto(visita.getReferto())
                .build();
   }

}
