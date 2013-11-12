/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mark
 */
public class User {
    
    private String name;
    private int userPri;
    private String password;

    private List<Comment> comments;
    private List<Post> posts;

    
    
    public User(String name, String password, int userPri){
        this.name = name;
        this.password = password;
        this.userPri = userPri;
        comments = new ArrayList<>();
        posts = new ArrayList<>();
    }
    
    public Comment createComment(String commentMessage){
        Comment c = new Comment(this, commentMessage);
        comments.add(c);
        return c;
    }
    
    public void deleteComment(Comment c){
        comments.remove(c);
        c.unsetRelatedComments();
    }
    
    public Post createPost(String message, Category category){
        Post p = new Post(this, message, category);
        posts.add(p);
        return p;
    }
    
    public void deletePost(Post p){
        p.unsertRelatedRelations();
        posts.remove(p);
    }
    
    public List<Post> getPosts(){
        return new ArrayList<>(posts);
    }
    
    public List<Comment> getComments(){
        return new ArrayList<>(comments);
    }
    
    public String getName(){ 
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public int getUserPri(){
        return userPri;
    }
    
    public void setUserPri(int userPri){
        this.userPri = userPri;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
