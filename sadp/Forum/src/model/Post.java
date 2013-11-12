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
public class Post {
    
    private GregorianCalendar created;
    private User user;
    private String message;
    private Category category;
    private List<Comment> comments;
    
    Post(User user, String message, Category category){
        created = new GregorianCalendar();
        this.user = user;
        this.message = message;
        this.category = category;
        comments = new ArrayList<>();
    }
    
    public Comment createComment(User user, String commentMessage){
        Comment c = user.createComment(commentMessage);
        comments.add(c);
        return c;
    }
    
    public void unsertRelatedRelations(){
        for(Comment c : comments){
            c.unsetRelatedComments();
        }
        category.removePost(this);
        category = null;
    }
    
    public List<Comment> getComments(){
        return new ArrayList<>(comments);
    }
    
    public Category getCategory(){
        return category;
    }
    
    public String getMessage(){
        return message;
    }
    
    public User getUser(){
        return user;
    }
    
    public GregorianCalendar getCreated(){
        return created;
    }
    
    public String getPrettyDate(){
    	return created.getTime().toString();
    }
    
    @Override
    public String toString(){
    	return user.toString() + " " + message.substring(0, 6) + "....";
    }
}
