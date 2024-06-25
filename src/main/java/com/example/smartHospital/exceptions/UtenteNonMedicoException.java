package com.example.smartHospital.exceptions;

public class UtenteNonMedicoException extends Exception{

    public String getMessage() {
        return "Questo utente non è un medico";
    }
}
