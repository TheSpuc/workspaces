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
public class Category {
    
    private List<Post> posts;
    private String name;
    
    public Category(String name){
        posts = new ArrayList<>();
        this.name = name;
    }
    
    public void createPost(User user, String message, Category category){
        Post p = user.createPost(message, category);
        posts.add(p);
    }
    
    public void unsetRelations(){
        for(Post p : posts){
            p.unsertRelatedRelations();
        }
    }
    
    public void removePost(Post p){
        posts.remove(p);
    }
    
    public String getName(){
        return name;
    }
    
    public List<Post> getPosts(){
        return new ArrayList<>(posts);
    }
    
    @Override
    public String toString(){
        return name;
    }
}
