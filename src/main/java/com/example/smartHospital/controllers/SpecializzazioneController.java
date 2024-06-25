package com.example.smartHospital.controllers;

import com.example.smartHospital.entities.Specializzazione;
import com.example.smartHospital.exceptions.SpecializzazioneNotFoundException;
import com.example.smartHospital.requests.SpecializzazioneRequest;
import com.example.smartHospital.responses.GenericResponse;
import com.example.smartHospital.services.SpecializzazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/specializzazione")
public class SpecializzazioneController {

    @Autowired
    private SpecializzazioneService specializzazioneService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUtenteById (@PathVariable Long id) throws SpecializzazioneNotFoundException {
        return new ResponseEntity<>(specializzazioneService.getSpecializzazioneById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSpecializzazioni () {
        return new ResponseEntity<>(specializzazioneService.getAllSpecializzazioni(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSpecializzazione (@RequestBody SpecializzazioneRequest request) {
        return new ResponseEntity<>(specializzazioneService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSpecializzazione (@PathVariable Long id, @RequestBody Specializzazione specializzazione) throws SpecializzazioneNotFoundException {
        return new ResponseEntity<>(specializzazioneService.update(id, specializzazione), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteById (@PathVariable Long id) throws SpecializzazioneNotFoundException {
        specializzazioneService.deleteById(id);
        return new ResponseEntity<>(new GenericResponse("Specializzazione rimossa con successo"), HttpStatus.OK);
    }

}
