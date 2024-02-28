package com.jhs.animatomo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AnimeBadRequestException extends AnimeException{
    public AnimeBadRequestException(String mensaje){
        super(mensaje);
    }
}