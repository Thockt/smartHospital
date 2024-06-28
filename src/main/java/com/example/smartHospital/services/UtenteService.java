package com.example.smartHospital.services;

import com.example.smartHospital.entities.Utente;
import com.example.smartHospital.entities.Visita;
import com.example.smartHospital.enums.Role;
import com.example.smartHospital.exceptions.*;
import com.example.smartHospital.repositories.UtenteRepository;
import com.example.smartHospital.requests.FasciaOrariaRequest;
import com.example.smartHospital.requests.VisitaRequest;
import com.example.smartHospital.responses.UtenteResponse;
import com.example.smartHospital.responses.VisitaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private SpecializzazioneService specializzazioneService;
    @Autowired
    private FasciaOrariaService fasciaOrariaService;
    @Autowired
    private VisitaService visitaService;

    public Utente getUtenteById (Long id) throws UtenteNotFoundException {
        Optional<Utente> utenteOptional = utenteRepository.findById(id);
        if(utenteOptional.isEmpty()) throw new UtenteNotFoundException();
        return utenteOptional.get();
    }

    public UtenteResponse getUtenteResponseById (Long id) throws UtenteNotFoundException{
        Optional<Utente> utenteOptional = utenteRepository.findById(id);
        if(utenteOptional.isEmpty()) throw new UtenteNotFoundException();
        return convertFromEntity(utenteOptional.get());
    }

    public List<Utente> getAllUtenti (){
        return utenteRepository.findAll();
    }

    public UtenteResponse create (Utente utente) {
        utente.setInsertTime(LocalDateTime.now());
        utenteRepository.saveAndFlush(utente);
        return convertFromEntity(utente);
    }

    public UtenteResponse update (Long id, Utente newUtente) throws UtenteNotFoundException {
        getUtenteById(id);
        Utente utente = Utente.builder()
                .id(id)
                .nome(newUtente.getNome())
                .cognome(newUtente.getCognome())
                .dataNascita(newUtente.getDataNascita())
                .indirizzo(newUtente.getIndirizzo())
                .telefono(newUtente.getTelefono())
                .email(newUtente.getEmail())
                .password(newUtente.getPassword())
                .registrationToken(newUtente.getRegistrationToken())
                .role(newUtente.getRole())
                .specializzazione(newUtente.getSpecializzazione())
                .insertTime(newUtente.getInsertTime())
                .lastUpdate(LocalDateTime.now())
                .build();
        return convertFromEntity(utenteRepository.saveAndFlush(utente));
    }

    public void deleteById (Long id) throws UtenteNotFoundException{
        getUtenteById(id);
        utenteRepository.deleteById(id);
    }

    public void inserisciFasciaOraria (Long idMedico, Long idSpecializzazione, FasciaOrariaRequest request) throws UtenteNotFoundException, SpecializzazioneSbagliataException, SpecializzazioneNotFoundException, UtenteNonMedicoException {
        Utente medico = getUtenteById(idMedico);
        if (!medico.getRole().equals(Role.MEDICO)) throw new UtenteNonMedicoException();
        if(!medico.getSpecializzazione().getId().equals(specializzazioneService.getSpecializzazioneById(idSpecializzazione).getId())) throw new SpecializzazioneSbagliataException();
        specializzazioneService.addFasciaOraria(idSpecializzazione, request);
    }

    public VisitaResponse prenotaVisita (VisitaRequest request) throws UtenteNotFoundException, UtenteNonMedicoException, FasciaOrariaNotFoundException, OrarioNonValidoException {
        Utente medico = getUtenteById(request.getMedico());
        if(!medico.getRole().equals(Role.MEDICO)) throw new UtenteNonMedicoException();
        getUtenteById(request.getPaziente());
        if(!fasciaOrariaService.isOrarioValido(request)) throw new OrarioNonValidoException();
        return visitaService.create(request);
    }

    public List<Utente> getMediciBySpecializzazione (Long idSpecializzazione) {
        List<Utente> utenti = utenteRepository.findMediciBySpecializzazioneId(idSpecializzazione);
        return utenti;
    }

    public void terminaVisita (Long idVisita, Long idMedico, String path) throws UtenteNotFoundException, UtenteNonMedicoException, VisitaNotFoundException, MedicoSbagliatoException {
        Utente medico = getUtenteById(idMedico);
        if(medico.getRole()!=Role.MEDICO) throw new UtenteNonMedicoException();
        Visita visita = visitaService.getVisitaById(idVisita);
        if(!visita.getMedico().equals(medico)) throw new MedicoSbagliatoException();
        visita.setReferto(path);
        visitaService.salvaVisita(visita);
    }

    public String getRefertoFilePath (Long idVisita) {
        return visitaService.getPath(idVisita);
    }

    public String getRefertoFilePathByPaziente (Long idPaziente) {
        return visitaService.getPathByPaziente(idPaziente);
    }

    public List<Long> getVisiteConReferto () {
        return visitaService.getAllVisiteConReferto();
    }

    public List<Visita> getVisitePrenotate (Long idPaziente) {
        return visitaService.getAllVisitePrenotate(idPaziente);
    }

    public List<Visita> getVisitePassate (Long idPaziente) {
        return visitaService.getAllVisitePassate(idPaziente);
    }

    private UtenteResponse convertFromEntity (Utente utente) {
        return UtenteResponse.builder()
                .id(utente.getId())
                .nome(utente.getNome())
                .cognome(utente.getCognome())
                .dataNascita(utente.getDataNascita())
                .indirizzo(utente.getIndirizzo())
                .telefono(utente.getTelefono())
                .email(utente.getEmail())
                .role(utente.getRole())
                .specializzazione(utente.getSpecializzazione().getId())
                .insertTime(utente.getInsertTime())
                .lastUpdate(utente.getLastUpdate())
                .build();
    }
}
