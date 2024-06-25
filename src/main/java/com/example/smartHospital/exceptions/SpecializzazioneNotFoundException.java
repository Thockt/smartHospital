package com.example.smartHospital.exceptions;

public class SpecializzazioneNotFoundException extends Exception{

    public String getMessage (){
        return "Questa specializzazione non esiste";
    }
}
