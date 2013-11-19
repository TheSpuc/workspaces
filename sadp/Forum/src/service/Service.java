/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.Dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Category;
import model.Comment;
import model.Post;
import model.User;

/**
 *
 * @author Mark
 */
@Named(value="service")
@ApplicationScoped
public class Service implements Serializable{

	private Dao dao = Dao.getInstance(); 

	public Service(){
	}

	public User createUser(String name, String password){
		User u = new User(name, password, 0);
		dao.addUser(u);
		return u;
	}

	public User deleteUser(User u){
		//Delete all user comments first
		List<Comment> comments = u.getComments();
		for(Comment c : comments){
			u.deleteComment(c);
		}
		//Delete all user posts as second
		List<Post> posts = u.getPosts();
		for(Post p : posts){
			u.deletePost(p);
		}

		dao.removeUser(u);
		return u;
	}

	public Category createCategory(String name){
		Category c = new Category(name);
		dao.addCategory(c);
		return c;
	}

	public Category deleteCategory(Category c){
		c.unsetRelations();
		dao.removeCategory(c);
		return c;
	}

	public Post createPost(User u, Category c, String message){
		Post p = u.createPost(message, c);
		dao.addPost(p);
		return p;
	}

	public Post deletePost(Post p){
		p.getUser().deletePost(p);
		dao.removePost(p);
		return p;
	}

	public Comment createComment(User u, String message){
		Comment c = u.createComment(message);
		dao.addComment(c);
		return c;
	}

	public Comment createPostComment(Post p, User u, String message){
		Comment c = p.createComment(u, message);
		dao.addComment(c);
		return c;
	}

	public Comment deleteComment(Comment c){
		c.getUser().deleteComment(c);
		dao.removeComment(c);
		return c;
	}

	public boolean validateUser(String s1, String s2){
		boolean found = false;
		int index = 0;
		List<User> tempList = dao.getUsers();

		while(!found && index<tempList.size()){
			User tempUser = tempList.get(index);
			if(tempUser.getName().equals(s1) && tempUser.getPassword().equals(s2)){
				found = true;
			} else 
				index++;
		}
		return found;
	}

	public List<Category> getCategories(){
		return dao.getCategories();
	}

	public List<Post> getPost(){
		return dao.getPosts();
	}

	public List<User> getUser(){
		return dao.getUsers();
	}

	public List<Comment> getComment(){
		return dao.getComments();
	}

	public List<Post> getSpecificPost(Category c){
		List<Post> temp = new ArrayList<>();
		if(c != null){
			for(Post p : getPost()){
				if(p.getCategory().equals(c)){
					temp.add(p);
				}
			}
		}
		return temp;
	}
	
	public void postUnset(Post p){
		p.unsertRelatedRelations();
		dao.removePost(p);
	}

	public List<Comment> getCommentsFromPost(Post p){
		return p.getComments();
	}
	
	public List<Comment> getCommentsFromComment(Comment c){
		return c.getComments();
	}

	@PostConstruct
	public void createObjects(){
		User u1 = createUser("Christian","Christian123");
		User u2 = createUser("Mark", "mark123");
		User u3 = createUser("Emil", "Emil123");

		Category cat1 = createCategory("SADP");
		Category cat2 = createCategory("CNDS");

		Post p1 = createPost(u1,cat1,"SAPD projektet er meget underligt");
		Post p2 = createPost(u2, cat1, "Det sjovt");

		Comment c1 = createPostComment(p1, u2, "Jeg helt enig!");
		Comment c2 = createPostComment(p1, u3, "Rolig nu drenge");
		
		c1.addComment(u2, "Det er sejt");
	}
}
