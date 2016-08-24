package com.hoyee.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoyee.models.ArticleModel;

import java.util.Date;
import java.util.List;

/**
 * Created by andrei.maksimchanka on 8/15/2016.
 */


public class Article {

    private Integer articleId;
    private Integer userId;
    private String header;
    private String contents;
    private Integer likesCount;
    private Long timestamp;
    private boolean isPosted;
    private List<Comment> comments;

    public Article(){ }

    public Article(ArticleModel articleModel, Integer articleId){
        this.articleId = articleId;
        this.userId = articleModel.getUserId();
        this.header = articleModel.getHeader();
        this.contents = articleModel.getContents();
        this.likesCount = articleModel.getLikesCount();
        this.timestamp = articleModel.getTimestamp();
        this.isPosted = articleModel.getIsPosted();
    }

    public String getHeader(){  return header;  }
    public void setHeader(String header){ this.header = header; }

    public String getContents(){  return contents; }
    public void setContents(String contents){ this.contents = contents; }

    public Integer getLikesCount(){ return likesCount; }
    public void setLikesCount(Integer likesCount){ this.likesCount = likesCount; }

    public Long getTimestamp(){ return timestamp; }
    public void setTimestamp(Long timestamp){ this.timestamp = timestamp; }

    public Integer getArticleId(){
        return articleId;
    }
    public void setArticleId(Integer articleId){
        this.articleId = articleId;
    }

    public Integer getUserId(){
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public boolean getIsPosted(){
        return isPosted;
    }
    public void setIsPosted(boolean isPosted){
        this.isPosted = isPosted;
    }

    public List<Comment> getComments(){
        return comments;
    }
    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    @Override
    public String toString(){
        return "{\"header\":\"" + header + "\", " +
                "\"userId\":\"" + userId + "\", " +
                "\"contents\":\"" + contents + "\", " +
                "\"isPosted\":\"" + isPosted + "\", " +
                "\"likesCount\":\"" + likesCount + "\", " +
                "\"date\":\"" + timestamp + "\"}";
    }

}
