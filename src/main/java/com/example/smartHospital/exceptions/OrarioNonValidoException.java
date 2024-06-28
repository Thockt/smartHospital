package com.example.smartHospital.exceptions;

public class OrarioNonValidoException extends Exception {

    public String getMessage(){
        return "Orario non valido, non è presente nessuna fascia oraria disponibile";
    }
}
