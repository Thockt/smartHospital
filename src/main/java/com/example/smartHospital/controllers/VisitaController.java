package com.example.smartHospital.controllers;

import com.example.smartHospital.entities.Visita;
import com.example.smartHospital.exceptions.UtenteNotFoundException;
import com.example.smartHospital.exceptions.VisitaNotFoundException;
import com.example.smartHospital.requests.VisitaRequest;
import com.example.smartHospital.responses.GenericResponse;
import com.example.smartHospital.services.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visita")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getVisitaById (@PathVariable Long id) throws VisitaNotFoundException {
        return new ResponseEntity<>(visitaService.getVisitaResponseById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllVisite () {
        return new ResponseEntity<>(visitaService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVisita (@RequestBody VisitaRequest request) throws UtenteNotFoundException {
        return new ResponseEntity<>(visitaService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVisita (@PathVariable Long id, @RequestBody Visita newVisita) throws VisitaNotFoundException {
        return new ResponseEntity<>(visitaService.update(id, newVisita), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteVisitaById (@PathVariable Long id) throws VisitaNotFoundException {
        visitaService.deleteById(id);
        return new ResponseEntity<>(new GenericResponse("Visita cancellata con successo"), HttpStatus.OK);
    }

}
