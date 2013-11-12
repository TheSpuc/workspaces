/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import model.Category;
import model.Comment;
import model.Post;
import model.User;
import service.Service;

/**
 *
 * @author Mark
 */
@Named("userBean")
@SessionScoped
public class Bean implements Serializable {

	@Inject
	private Service service;
	private Category category;
	private User user;
	private Post selectedPost;
	private Comment selectedComment;
	private boolean isAdmin;

	public Bean() {
		user = new User("", "", 0);
		isAdmin = false;
		service = new Service();
		service.createObjects();
		category = new Category("null");
		selectedPost = null;
		selectedComment = null;
	}

	public String ableToLogin() {
		if (service.validateUser(user.getName(), user.getPassword())) {
			return "forum";
		} else {
			return "error";
		}
	}

	public Post getSelectedPost(){
		return selectedPost;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Category> getCategories() {
		return service.getCategories();
	}

	public Service getService() {
		return service;
	}

	public String selectSpecificPost(Post p){
		selectedPost = p;
		return null;
	}

	public String deleteSpecificPost(Post p){
		service.postUnset(p);
		selectedPost = null;
		return null;
	}

	public List<Comment> getCommentsFromPost(){
		if(selectedPost != null){
			return service.getCommentsFromPost(selectedPost);
		}else return new ArrayList<>();
	}
	
	public List<Comment> getCommentsFromComment(){
		if(selectedComment != null){
			return service.getCommentsFromComment(selectedComment);
		}else return new ArrayList<>();
	}
	
	public String selectSpecificComment(Comment c){
		selectedComment = c;
		return null;
	}

	public List<Post> getCategoryPosts() {
		if(category != null){
			return service.getSpecificPost(category);
		}else return new ArrayList<>();
	}

	public void categoryChanged(ValueChangeEvent event) {
		for (Category c : getCategories()) {
			if (c.getName().equals(event.getNewValue())) {
				setCategory(c);
			}
		}
	}
}
