package com.example.library_api.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException (String messeage){
        super(messeage);
    }
}
