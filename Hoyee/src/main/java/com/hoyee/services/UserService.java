package com.hoyee.services;

import com.hoyee.domains.Article;
import com.hoyee.domains.Comment;
import com.hoyee.domains.Role;
import com.hoyee.domains.User;
import com.hoyee.exceptions.BadRequestException;
import com.hoyee.models.UserLoginModel;
import com.hoyee.models.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserService implements IUserService{
    
    @Autowired
    private MongoOperations mongoOperations;

    public void setMongoOperations(MongoTemplate mongoOperations){
        this.mongoOperations = mongoOperations;
    }

    public MongoOperations getMongoOperations(){
        return mongoOperations;
    }

    //<<<<<<<<<<<<<<<<<<<<<     Users     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

    @Override
    public List<User> getAll(){
       return mongoOperations.findAll( User.class );
    }

    @Override
    public User getById(Integer id){
        return mongoOperations.findById(id, User.class);
    }

    @Override
    public User getByUserId(Integer id) throws BadRequestException{
        Query q = new Query(Criteria.where("userId").is(id));
        User user = mongoOperations.findOne(q, User.class, "user");
        if(user == null)
            throw new BadRequestException("Bad Request: User is not found");
        return user;
    }

    public User getByName(String name) {
        Query q = new Query(Criteria.where("username").is(name));
        return mongoOperations.findOne(q, User.class, "user");
    }

    @Override
    public String save(UserRegisterModel person){
        User user = new User();
        user.setUserId(getAll().size() + 1);
        user.setEmail(person.getEmail());
        user.setUsername(person.getUsername());
        user.setPassword(person.getPassword());
        user.setAvatar("https://localhost:8080/abc.png");
        user.setRole(new Role("USER"));
        mongoOperations.save(user, "user");
        return user.getId().toString();
    }

    @Override
    public User findByCredentials(UserLoginModel person ){
        Query searchUserQuery = new Query(
                Criteria
                        .where("username").is(person.getUsername())
                        .and("password").is(person.getPassword())
        );
        return mongoOperations.findOne(searchUserQuery, User.class, "user");
    }

    @Override
    public Integer update(User user){
        /*Query searchUserQuery = new Query(
                Criteria
                        .where("id").is( user.getId() )
                        .and("password").is( user.getPassword())
                        .and("email").is( user.getEmail())
                        .and("role").is(user.getRole().getRoleName())
                        .and("id").is(user.getId())
        );

        mongoOperations.updateFirst(searchUserQuery,
                Update.update("username", user.getUsername())
                        .("password").is( user.getPassword())
                        .and("email").is( user.getEmail())
                        .and("role").is(user.getRole().getRoleName()) ,User.class );*/
        return 0;
    }


    //<<<<<<<<<<<<<<<<<<<<<     Articles     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

    @Override
    public Article getArticleById(Integer id, User user) throws BadRequestException{
        List<Article> articles = user.getArticles();
        for( Article article: articles )
            if( article.getArticleId().equals(id) )
                return article;
        throw new BadRequestException("Bad Request: Article with ID = " + id + " was not found");
    }

    @Override
    public Integer updateArticle(Article articleToUpdate, User user) throws BadRequestException {
        List<Article> articles = user.getArticles();
        for( Article article: articles )
            if( article.getArticleId().equals( articleToUpdate.getArticleId() ) && article.getUserId().equals(user.getUserId()) ){
                article.setUserId(articleToUpdate.getUserId());
                article.setIsPosted(articleToUpdate.getIsPosted());
                article.setHeader(articleToUpdate.getHeader());
                article.setContents(articleToUpdate.getContents());
                article.setLikesCount(articleToUpdate.getLikesCount());
                //article.setComments(new ArrayList<Comment>());              //implement when genericComment feature on front will be available
                article.setTimestamp(articleToUpdate.getTimestamp());
                mongoOperations.save(user, "user");
                return article.getArticleId();
            }
        throw new BadRequestException("Bad Request: Article with ID = " + articleToUpdate.getArticleId() + " was not found");
    }

    @Override
    public Integer createArticle(Article article, User user){
        List<Article> articles = user.getArticles();
        article.setArticleId( articles.size() + 1 );
        article.setUserId( user.getUserId());
        article.setIsPosted(false);
        article.setTimestamp(new Date(article.getTimestamp()).getTime());
        article.setLikesCount(0);
        articles.add(article);
        mongoOperations.save(user, "user");
        return article.getArticleId();
    }

    @Override
    public List<Article> getAllArticles(){
        List<User> users = getAll();
        List<Article> attachedArticles = new ArrayList<>();
        for( User user: users ){
            List<Article> articles = user.getArticles();
            attachedArticles.addAll(articles);
        }
        return attachedArticles;
    }

    @Override
    public Integer deleteArticleById(Integer id, User user) throws BadRequestException{
        List<Article> articles = user.getArticles();
        for(Article article: articles)
            if(article.getArticleId().equals(id)){
                articles.remove(article);
                user.setArticles(articles);
                mongoOperations.save(user, "user");
                return id;
            }
        throw new BadRequestException("Bad Request: Article with ID = " + id + " was not found");
    }

    @Override
    public void deleteById(Integer id){
        Query searchUserQuery = new Query( Criteria.where("id").is(id) );
        mongoOperations.remove(searchUserQuery, User.class);
    }
}



/*

 @Override
    public User getByName(String name) {

        List<User> users = mongoOperations.findAll(User.class, "user");
        for(User user: users){
            user.setArticles(new ArrayList<>());
            mongoOperations.save(user);
        }
        User u = users.get(0);
        List<Article> articles = u.getArticles();
        articles.add(new Article(
                "CISCO UNCOVERS SECURITY THREAT IN INDUSTRIAL CONTROL SYSTEM",
                "The Simple Network Management Protocol exploit could let an attacker take complete control of Rockwell Automation’s MicroLogix system.",
                0,
                new Date()));

        articles.add(new Article(
                "Cortana: The spy in Windows 10",
                "When I first saw Mr. Spock talking to the Enterprise’s computer, I thought it was so cool. I still do. But the more I look at Cortana, Windows 10’s inherent virtual assistant, the more creeped out I get.\n" +
                        "\n" +
                        "Opinion by Steven J. Vaughan-Nichols\n" +
                        "\n" +
                        "Would you subscribe to Windows?\n" +
                        "I don’t want to be LinkedIn with Microsoft\n" +
                        "Is Oracle cooking its cloud books?\n" +
                        "Oracle’s API stance would be silly if it weren’t so dangerous\n" +
                        "SEE MORE\n" +
                        "Let’s start with Cortana’s fundamental lust for your data. When it’s working as your virtual assistant it’s collecting your every keystroke and spoken syllable. It does this so it can be more helpful to you. If you don’t like that, well, you’ve got more problems than just Cortana. Google Now and Apple Siri do the same things. And it’s not just virtual assistants; every cloud-based software as a service (SaaS) does this to one degree or another — Google Docs, Office 365, whatever.",
                0,
                new Date()));

        u.setArticles(articles);
        mongoOperations.save(u);
       // Query q = new Query(Criteria.where("username").is(name));
        return null;
    }
 */