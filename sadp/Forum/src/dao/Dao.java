/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Named(value="dao")
@ApplicationScoped
public class Dao implements Serializable {

	private static List<Comment> comments = new ArrayList<>();
	private static List<User> users = new ArrayList<>();
	private static List<Category> categories = new ArrayList<>();
	private static List<Post> posts = new ArrayList<>();

	public Comment addComment(Comment c) {
		comments.add(c);
		return c;
	}

	public void removeComment(Comment c) {
		comments.remove(c);
	}

	public User addUser(User u) {
		users.add(u);
		return u;

	}

	public void removeUser(User u) {
		users.remove(u);
	}

	public Category addCategory(Category c) {
		categories.add(c);
		return c;
	}

	public void removeCategory(Category c) {
		categories.remove(c);
	}

	public Post addPost(Post m) {
		posts.add(m);
		return m;
	}

	public void removePost(Post m){
		posts.remove(m);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public List<Post> getPosts() {
		return posts;
	}

}
