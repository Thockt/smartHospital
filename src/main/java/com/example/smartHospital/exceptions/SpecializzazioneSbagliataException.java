package com.example.smartHospital.exceptions;

public class SpecializzazioneSbagliataException extends Exception{

    public String getMessage () {
        return "Questo medico non ha questa specializzazione";
    }
}
