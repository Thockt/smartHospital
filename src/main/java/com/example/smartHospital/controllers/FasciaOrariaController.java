package com.example.smartHospital.controllers;

import com.example.smartHospital.entities.FasciaOraria;
import com.example.smartHospital.exceptions.FasciaOrariaNotFoundException;
import com.example.smartHospital.requests.FasciaOrariaRequest;
import com.example.smartHospital.responses.GenericResponse;
import com.example.smartHospital.services.FasciaOrariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fasciaOraria")
public class FasciaOrariaController {

    @Autowired
    private FasciaOrariaService fasciaOrariaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getFasciaOrariaById (@PathVariable Long id) throws FasciaOrariaNotFoundException {
        return new ResponseEntity<>(fasciaOrariaService.getFasciaOrariaById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFasceOrarie () {
        return new ResponseEntity<>(fasciaOrariaService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFasciaOraria (@RequestBody FasciaOrariaRequest request) {
        return new ResponseEntity<>(fasciaOrariaService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFasciaOraria (@PathVariable Long id, @RequestBody FasciaOraria newFasciaOraria) throws FasciaOrariaNotFoundException {
        return new ResponseEntity<>(fasciaOrariaService.update(id, newFasciaOraria), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteFasciaOrariaById (@PathVariable Long id) throws FasciaOrariaNotFoundException {
        fasciaOrariaService.deleteById(id);
        return new ResponseEntity<>(new GenericResponse("Fascia oraria eliminata con successo"), HttpStatus.OK);
    }
}
