package com.hoyee.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.hoyee.JsonReponse;
import com.hoyee.domains.Token;
import com.hoyee.domains.User;
import com.hoyee.exceptions.BadRequestException;
import com.hoyee.exceptions.UserUnauthorizedException;
import com.hoyee.models.UserLoginModel;
import com.hoyee.services.IAuthenticationService;
import com.hoyee.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;

/**
 * Created by andrei.maksimchanka on 8/19/2016.
 */


@Controller
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "authenticationService")
    private IAuthenticationService authenticationService;

    @RequestMapping(value = "/getCurrent" , method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> getCurrentUser() {
        try{
            Token token = authenticationService.authenticate( RequestContextHolder.currentRequestAttributes() );
            User authUser = userService.getByName(token.getUsername());
            authUser.setId(null);
            authUser.setArticles(null);
            authUser.setPassword(null);
            return new ResponseEntity<User>(authUser, HttpStatus.OK);
        } catch(UserUnauthorizedException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/getById/{userId}" , method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        try{
            Token token = authenticationService.authenticate( RequestContextHolder.currentRequestAttributes() );
            User user = userService.getByUserId(userId);
            user.setId(null);
            user.setArticles(null);
            user.setPassword(null);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch(UserUnauthorizedException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
