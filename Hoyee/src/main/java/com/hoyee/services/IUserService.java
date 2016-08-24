package com.hoyee.services;

import com.hoyee.domains.Article;
import com.hoyee.domains.User;
import com.hoyee.exceptions.BadRequestException;
import com.hoyee.models.UserLoginModel;
import com.hoyee.models.UserRegisterModel;

import java.util.List;

public interface IUserService {
    //Users
    List<User> getAll();
    User getById(Integer id);
    User getByUserId(Integer id) throws BadRequestException;
    User getByName(String name);
    User findByCredentials(UserLoginModel person);
    String save(UserRegisterModel person);
    void deleteById(Integer id);
    Integer update(User user);

    //Articles
    Article getArticleById(Integer id, User user) throws BadRequestException;
    Integer updateArticle(Article article, User user) throws BadRequestException;
    Integer createArticle(Article article, User user);
    List<Article> getAllArticles();
    Integer deleteArticleById(Integer id, User user) throws BadRequestException;
}
