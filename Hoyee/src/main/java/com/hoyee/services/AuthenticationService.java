package com.hoyee.services;

import java.util.Base64;
import com.hoyee.exceptions.UserUnauthorizedException;
import com.hoyee.domains.Token;
import com.hoyee.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by andrei.maksimchanka on 8/12/2016.
 */

public class AuthenticationService implements IAuthenticationService{

    @Autowired
    private MongoOperations mongoOperations;

    public void setMongoOperations(MongoTemplate mongoOperations){  this.mongoOperations = mongoOperations; }
    public MongoOperations getMongoOperations(){
        return mongoOperations;
    }

    @Override
    public Token authenticate(String username, String password, RequestAttributes requestAttributes) throws UserUnauthorizedException {
        HttpServletRequest request = getRequest(requestAttributes);
        Query q = new Query(Criteria.where("username").is(username).and("password").is(password));
        User user = mongoOperations.findOne(q, User.class, "user");
        if (user == null)
            throw new UserUnauthorizedException("User Unauthorized. 401");

        String authData = request.getHeader("X-Auth-Token");
        //if ( authData == null){
        String encodedCredentials = Base64.getEncoder().encodeToString((username + password).getBytes());
        Token token = new Token();
        token.setUsername(username);
        token.setToken(encodedCredentials);
        q = new Query(Criteria.where("token").is(token.getToken()));
        if (mongoOperations.findOne(q, Token.class, "token") == null)
            mongoOperations.save(token, "token");
        return token;
        //}
    }

    @Override
    public Token authenticate(RequestAttributes requestAttributes) throws UserUnauthorizedException {
        HttpServletRequest request = getRequest(requestAttributes);
        String authData = request.getHeader("X-Auth-Token");
        if ( authData == null)
            throw new UserUnauthorizedException("User Unauthorized. 401");

        Query q = new Query(Criteria.where("token").is( authData.substring(7)));
        Token token = mongoOperations.findOne(q, Token.class, "token");
        if (token == null){
            throw new UserUnauthorizedException("Token is invalid or expired. 401");
        }
        return token;
    }

    private HttpServletRequest getRequest(RequestAttributes requestAttributes){
        ServletRequestAttributes attrs = (ServletRequestAttributes) requestAttributes;
        return attrs.getRequest();
    }
}
