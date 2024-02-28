package com.jhs.animatomo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnimeNotFoundException extends AnimeException {
    public AnimeNotFoundException(String mensaje){
        super(mensaje);
    }

}