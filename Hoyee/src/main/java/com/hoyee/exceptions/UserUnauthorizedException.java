package com.hoyee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by andrei.maksimchanka on 8/15/2016.
 */

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="User Unauthorized") //401
public class UserUnauthorizedException extends Exception{
    public UserUnauthorizedException(){}
    public UserUnauthorizedException(String message){
        super(message);
    }
}
