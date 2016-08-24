package com.hoyee.domains;

import com.hoyee.annotations.CascadeSave;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private String email;
    private Role role;
    private Integer userId;
    private List<Article> articles;

    public User(){}
    public User(String username, String password, String email, Role role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }

    public ObjectId getId(){
        return id;
    }
    public void setId(ObjectId id){
        this.id = id;
    }

    public List<Article> getArticles(){
        return articles;
    }
    public void setArticles(List<Article> articles){
        this.articles = articles;
    }

    public Integer getUserId(){
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }

    @Override
    public String toString(){
        return "{ \"id\": \"" + getId() + "\", " +
                "\"userId\": \"" + getUserId() + "\", " +
                "\"username\": \"" + getUsername() + "\", " +
                "\"password\": \"" + getPassword() + "\", " +
                "\"email\": \"" + getEmail() + "\", " +
                "\"role\": \"" + ((getRole() == null) ? "null" : getRole()) + "\"}";
    }

}
