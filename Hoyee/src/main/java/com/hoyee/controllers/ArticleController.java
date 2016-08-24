package com.hoyee.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.hoyee.JsonReponse;
import com.hoyee.domains.Article;
import com.hoyee.domains.Token;
import com.hoyee.domains.User;
import com.hoyee.exceptions.BadRequestException;
import com.hoyee.exceptions.UserUnauthorizedException;
import com.hoyee.models.ArticleModel;
import com.hoyee.services.IAuthenticationService;
import com.hoyee.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@Controller
@RequestMapping("/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    @Resource(name = "userService")
    private IUserService userService;

    @Resource(name = "authenticationService")
    private IAuthenticationService authenticationService;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RequestMapping(value = "/getPersonal" , method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> getPersonalArticles(){
        try{
            User user = getUserFromRequest(RequestContextHolder.currentRequestAttributes());
            List<Article> articles = user.getArticles();
            return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
        } catch(UserUnauthorizedException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RequestMapping(value = "/getById/{articleId}/{userId}" , method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> getArticleById( @PathVariable("articleId") int articleId,
                                                     @PathVariable("userId") int userId){
        try{
            User authUser = getUserFromRequest(RequestContextHolder.currentRequestAttributes());                            //needed cuz /Home page is available only for authorized users.
            Integer id = (userId == -1) ? authUser.getUserId() : userId;
            User user = userService.getByUserId( id );
            Article article = userService.getArticleById(articleId, user);
            return new ResponseEntity<Article>(article, HttpStatus.OK);
        } catch(BadRequestException ex) {
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UserUnauthorizedException ex) {
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RequestMapping(value = "/update/{articleId}" , method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> updateArticle( @PathVariable("articleId") int articleId,
                                                    @RequestBody ArticleModel articleModel){
        try{
            User authUser = getUserFromRequest(RequestContextHolder.currentRequestAttributes());                            //needed cuz /Home page is available only for authorized users.
            Article article = new Article(articleModel, articleId);
            User user = userService.getByUserId(articleModel.getUserId());
            userService.updateArticle(article, user);
            return new ResponseEntity<>(new Gson().toJson(articleId), HttpStatus.OK);
        } catch(BadRequestException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UserUnauthorizedException ex) {
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    @JsonInclude( JsonInclude.Include.NON_NULL )
    @RequestMapping(value = "/delete/{articleId}" , method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> deleteArticle( @PathVariable("articleId") int articleId){
        try{
            User authUser = getUserFromRequest(RequestContextHolder.currentRequestAttributes());                            //needed cuz /Home page is available only for authorized users.
            Integer id = userService.deleteArticleById(articleId, authUser);
            return new ResponseEntity<>(new Gson().toJson(id), HttpStatus.OK);
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UserUnauthorizedException ex) {
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> createArticle( @RequestBody ArticleModel articleModel){
        try{
            User user = getUserFromRequest(RequestContextHolder.currentRequestAttributes());
            Article article = new Article(articleModel, null);
            Integer articleId = userService.createArticle(article, user);
            return new ResponseEntity<>(new Gson().toJson(articleId), HttpStatus.OK);
        } catch(UserUnauthorizedException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RequestMapping(value = "/getAll" , method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> getAllArticles(){
        try{
            User user = getUserFromRequest(RequestContextHolder.currentRequestAttributes());                            //needed cuz /Home page is available only for authorized users.
            List<Article> articles = userService.getAllArticles();
            return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
        } catch(UserUnauthorizedException ex){
            return new ResponseEntity<>(new Gson().toJson(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    private User getUserFromRequest(RequestAttributes requestAttributes) throws UserUnauthorizedException{
        Token token = authenticationService.authenticate( requestAttributes );
        return userService.getByName(token.getUsername());
    }
}
