package com.hoyee.models;

public class CommentModel {

    private String contents;
    private Integer likesCount;
    private Long timestamp;
    private Integer userId;
    private Integer articleId;

    public String getContents(){  return contents; }
    public void setContents(String contents){ this.contents = contents; }

    public Integer getLikesCount(){ return likesCount; }
    public void setLikesCount(Integer likesCount){this.likesCount = likesCount; }

    public Long getTimestamp(){ return timestamp; }
    public void setTimestamp(Long timestamp){this.timestamp = timestamp; }

    public Integer getUserId(){
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getArticleId(){
        return articleId;
    }
    public void setArticleId(Integer articleId){
        this.articleId = articleId;
    }

    public String toString(){
        return "{\"userId\":\"" + userId + "\", " +
                "\"articleId\":\"" + articleId + "\", " +
                "\"contents\":\"" + contents + "\", " +
                "\"likesCount\":\"" + likesCount + "\", " +
                "\"date\":\"" + timestamp + "\"}";
    }

}
