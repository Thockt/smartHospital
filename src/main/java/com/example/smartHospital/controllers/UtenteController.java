package com.example.smartHospital.controllers;

import com.example.smartHospital.entities.Utente;
import com.example.smartHospital.exceptions.*;
import com.example.smartHospital.requests.FasciaOrariaRequest;
import com.example.smartHospital.requests.VisitaRequest;
import com.example.smartHospital.responses.GenericResponse;
import com.example.smartHospital.services.UtenteService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Secured({"ADMIN","MEDICO"})
    @PutMapping("/addFasciaOraria/{idMedico}/{idSpecializzazione}")
    public ResponseEntity<GenericResponse> aggiungiFasciaOraria (@PathVariable Long idMedico, @PathVariable Long idSpecializzazione, @RequestBody FasciaOrariaRequest request) throws SpecializzazioneSbagliataException, SpecializzazioneNotFoundException, UtenteNonMedicoException, UtenteNotFoundException {
        utenteService.inserisciFasciaOraria(idMedico, idSpecializzazione, request);
        return new ResponseEntity<>(new GenericResponse("Fascia oraria inserita con successo"), HttpStatus.OK);
    }

    @Secured({"ADMIN", "PAZIENTE"})
    @PostMapping("/prenotaVisita")
    public ResponseEntity<?> prenotaVisita (@RequestBody VisitaRequest request) throws UtenteNonMedicoException, UtenteNotFoundException, OrarioNonValidoException, FasciaOrariaNotFoundException {
        return new ResponseEntity<>(utenteService.prenotaVisita(request), HttpStatus.CREATED);
    }

    @GetMapping("/getSpecialisti/{idSpecializzazione}")
    public ResponseEntity<?> getMediciBySpecializzazione (@PathVariable Long idSpecializzazione) {
        return new ResponseEntity<>(utenteService.getMediciBySpecializzazione(idSpecializzazione), HttpStatus.OK);
    }

    @Secured({"ADMIN", "MEDICO"})
    @PutMapping("/terminaVisita/{idVisita}/{idMedico}")
    public ResponseEntity<GenericResponse> terminaVisita (@PathVariable Long idVisita, @PathVariable Long idMedico, @RequestParam("file") MultipartFile file) throws IOException, VisitaNotFoundException, UtenteNonMedicoException, UtenteNotFoundException, MedicoSbagliatoException {
        Path filePath = Paths.get("src/main/resources/referti" +idVisita +"_"+ file.getOriginalFilename());
        Path pathFile = Path.of(filePath.toUri());
        String mimeType = Files.probeContentType(pathFile);
        Files.copy(file.getInputStream(), filePath);
        utenteService.terminaVisita(idVisita, idMedico, filePath.toString());
        return new ResponseEntity<>(new GenericResponse("Referto caricato con successo"), HttpStatus.OK);
    }

    @Secured({"ADMIN, MEDICO"})
    @GetMapping("/getReferti")
    public ResponseEntity<?> getVisiteConReferto () {
        return new ResponseEntity<>(utenteService.getVisiteConReferto(), HttpStatus.OK);
    }

    @Secured({"ADMIN", "MEDICO"})
    @GetMapping("/download_referto/{idVisita}")
    public ResponseEntity<GenericResponse> downloadRefertoPerMedico (@PathVariable Long idVisita, HttpServletResponse response) throws IOException {
        String pathFile = utenteService.getRefertoFilePath(idVisita);
        Path filePath = Path.of(pathFile);
        String mimeType =Files.probeContentType(filePath);
        if(mimeType == null){
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"");
        response.setContentLength((int) Files.size(filePath));
        Files.copy(filePath, response.getOutputStream());
        return new ResponseEntity<>(new GenericResponse("File scaricato con successo"), HttpStatus.OK);
    }

    @Secured({"ADMIN", "PAZIENTE"})
    @GetMapping("/getVisitePrenotate/{idPaziente}")
    public ResponseEntity<?> getVisitePrenotate (@PathVariable Long idPaziente) {
        return new ResponseEntity<>(utenteService.getVisitePrenotate(idPaziente), HttpStatus.OK);
    }

    @Secured({"ADMIN", "PAZIENTE"})
    @GetMapping("/getVisitePassate/{idPaziente}")
    public ResponseEntity<?> getVisitePassate (@PathVariable Long idPaziente) {
        return new ResponseEntity<>(utenteService.getVisitePassate(idPaziente), HttpStatus.OK);
    }

    @Secured({"ADMIN", "PAZIENTE"})
    @GetMapping("/download_refertoPaziente/{idPaziente}")
    public ResponseEntity<GenericResponse> downloadRefertoPerPaziente (@PathVariable Long idPaziente, HttpServletResponse response) throws IOException {
        String pathFile = utenteService.getRefertoFilePathByPaziente(idPaziente);
        Path filePath = Path.of(pathFile);
        String mimeType =Files.probeContentType(filePath);
        if(mimeType == null){
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"");
        response.setContentLength((int) Files.size(filePath));
        Files.copy(filePath, response.getOutputStream());
        return new ResponseEntity<>(new GenericResponse("File scaricato con successo"), HttpStatus.OK);
    }


}
