package com.hoyee.models;

/**
 * Created by andrei on 8/3/2016.
 */


public class ArticleModel {

    private String header;
    private String contents;
    private Integer likesCount;
    private Long timestamp;
    private Integer userId;
    private boolean isPosted;

    public String getHeader(){  return header;  }
    public void setHeader(String header){ this.header = header; }

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

    public boolean getIsPosted(){
        return isPosted;
    }
    public void setIsPosted(boolean isPosted){
        this.isPosted = isPosted;
    }

    public String toString(){
        return "{\"header\":\"" + header + "\", " +
                "\"userId\":\"" + userId + "\", " +
                "\"contents\":\"" + contents + "\", " +
                "\"isPosted\":\"" + isPosted + "\", " +
                "\"likesCount\":\"" + likesCount + "\", " +
                "\"date\":\"" + timestamp + "\"}";
    }
}
