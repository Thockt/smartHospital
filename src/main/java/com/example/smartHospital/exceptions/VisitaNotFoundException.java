package com.example.smartHospital.exceptions;

public class VisitaNotFoundException extends Exception {

    public String getMessage() {
        return "Questa visita non esiste";
    }
}
