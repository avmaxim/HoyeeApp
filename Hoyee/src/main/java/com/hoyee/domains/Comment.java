package com.hoyee.domains;

import com.hoyee.models.CommentModel;

/**
 * Created by andrei on 8/23/2016.
 */
public class Comment {

    private Integer commentId;
    private Integer articleId;
    private Integer userId;
    private String contents;
    private Integer likesCount;
    private Long timestamp;

    public Comment(){ }

    public Comment(CommentModel commentModel, Integer commentId){
        this.commentId = commentId;
        this.userId = commentModel.getUserId();
        this.articleId = commentModel.getArticleId();
        this.contents = commentModel.getContents();
        this.likesCount = commentModel.getLikesCount();
        this.timestamp = commentModel.getTimestamp();
    }

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

    public Integer getCommentId(){
        return commentId;
    }
    public void setCommentId(Integer commentId){
        this.commentId = commentId;
    }

    public String toString(){
        return "{\"userId\":\"" + userId + "\", " +
                "\"commentId\":\"" + commentId + "\", " +
                "\"articleId\":\"" + articleId + "\", " +
                "\"contents\":\"" + contents + "\", " +
                "\"likesCount\":\"" + likesCount + "\", " +
                "\"date\":\"" + timestamp + "\"}";
    }
}
