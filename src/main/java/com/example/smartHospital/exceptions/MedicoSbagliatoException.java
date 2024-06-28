package com.example.smartHospital.exceptions;

public class MedicoSbagliatoException extends Exception{

    public String getMessage () {
        return "Questo non il medico che ha fatto questa visita";
    }
}
