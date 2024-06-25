package com.example.smartHospital.exceptions;

public class UtenteNotFoundException extends Exception{

    public String getMessage(){
        return "Questo utente non esiste";
    }
}
