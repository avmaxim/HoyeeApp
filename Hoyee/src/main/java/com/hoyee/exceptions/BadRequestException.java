package com.hoyee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by andrei.maksimchanka on 8/17/2016.
 */

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad request") //400
public class BadRequestException extends Exception{
    public BadRequestException(){}
    public BadRequestException(String message){
        super(message);
    }
}
