/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Mark
 */
public class Comment {
    
    private List<Comment> comments;
    private User user;
    private GregorianCalendar created;
    private String commentMessage;
    
    Comment(User user, String commentMessage){
        comments = new ArrayList<>();
        this.user = user;
        created = new GregorianCalendar();
        this.commentMessage = commentMessage;
    }
    
    public User getUser(){
        return user;
    }
    
    public void unsetRelatedComments(){
        for(Comment temp : comments){
            temp.getUser().deleteComment(temp);
        }
    }
    
    public String getCommentMessage(){
        return commentMessage;
    }
    
    public void addComment(User user, String commentMessage){
        Comment newComment = new Comment(user, commentMessage);
        comments.add(newComment);
    }
    
    public List<Comment> getComments(){
        return new ArrayList<>(comments);
    }
    
    public String getPrettyDate(){
    	return created.getTime().toString();
    }
}
