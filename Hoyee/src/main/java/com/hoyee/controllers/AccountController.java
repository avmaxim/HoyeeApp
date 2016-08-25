package com.hoyee.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.hoyee.domains.Token;
import com.hoyee.exceptions.UserUnauthorizedException;
import com.hoyee.JsonReponse;
import com.hoyee.models.UserLoginModel;
import com.hoyee.models.UserRegisterModel;
import com.hoyee.services.IAuthenticationService;
import com.hoyee.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;


@Controller
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "authenticationService")
    private IAuthenticationService authenticationService;

    @RequestMapping(value = "/login" , method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody  ResponseEntity<?> loginUser(@RequestBody UserLoginModel person) {

        try {
            Token token = authenticationService.authenticate(person.getUsername(), person.getPassword(), RequestContextHolder.currentRequestAttributes());
            return new ResponseEntity<>(new Gson().toJson(token.getToken()), HttpStatus.OK);
        } catch(UserUnauthorizedException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public @ResponseBody  ResponseEntity<String> registerUser(@RequestBody UserRegisterModel person) {
        try {
            String userId = userService.save(person);
            return new ResponseEntity<>(new Gson().toJson(userId), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
