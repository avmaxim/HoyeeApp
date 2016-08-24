package com.hoyee.domains;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
public class Token {

    @Id
    private ObjectId id;
    private String token;
    private String username;

    public ObjectId getId(){
        return id;
    }
    public void setId(ObjectId id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }

}
