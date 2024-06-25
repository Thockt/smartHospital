package com.example.smartHospital.controllers;

import com.example.smartHospital.entities.Utente;
import com.example.smartHospital.exceptions.SpecializzazioneNotFoundException;
import com.example.smartHospital.exceptions.SpecializzazioneSbagliataException;
import com.example.smartHospital.exceptions.UtenteNonMedicoException;
import com.example.smartHospital.exceptions.UtenteNotFoundException;
import com.example.smartHospital.requests.FasciaOrariaRequest;
import com.example.smartHospital.responses.GenericResponse;
import com.example.smartHospital.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUtenteById (@PathVariable Long id) throws UtenteNotFoundException {
        return new ResponseEntity<>(utenteService.getUtenteResponseById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUtenti (){
        return new ResponseEntity<>(utenteService.getAllUtenti(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUtente (@RequestBody Utente utente) {
        return new ResponseEntity<>(utenteService.create(utente), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUtente (@PathVariable Long id, @RequestBody Utente newUtente)throws UtenteNotFoundException{
        return new ResponseEntity<>(utenteService.update(id, newUtente), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteUtenteById (@PathVariable Long id) throws UtenteNotFoundException {
        utenteService.deleteById(id);
        return new ResponseEntity<>(new GenericResponse("Utente rimosso con successo"), HttpStatus.OK);
    }

    @PutMapping("/addFasciaOraria/{idMedico}/{idSpecializzazione}")
    public ResponseEntity<GenericResponse> aggiungiFasciaOraria (@PathVariable Long idMedico, @PathVariable Long idSpecializzazione, @RequestBody FasciaOrariaRequest request) throws SpecializzazioneSbagliataException, SpecializzazioneNotFoundException, UtenteNonMedicoException, UtenteNotFoundException {
        utenteService.inserisciFasciaOraria(idMedico, idSpecializzazione, request);
        return new ResponseEntity<>(new GenericResponse("Fascia oraria inserita con successo"), HttpStatus.OK);
    }

}
