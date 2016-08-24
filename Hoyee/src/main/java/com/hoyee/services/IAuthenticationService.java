package com.hoyee.services;

import com.hoyee.domains.Token;
import com.hoyee.exceptions.UserUnauthorizedException;
import org.springframework.web.context.request.RequestAttributes;

/**
 * Created by andrei.maksimchanka on 8/12/2016.
 */
public interface IAuthenticationService {
    Token authenticate(String username, String password, RequestAttributes requestAttributes) throws UserUnauthorizedException;
    Token authenticate(RequestAttributes requestAttributes) throws UserUnauthorizedException;
}
