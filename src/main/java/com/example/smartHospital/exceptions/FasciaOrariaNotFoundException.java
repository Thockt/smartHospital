package com.example.smartHospital.exceptions;

public class FasciaOrariaNotFoundException extends Exception{

    public String getMessage() {
        return "Questa fascia oraria non esiste";
    }
}
