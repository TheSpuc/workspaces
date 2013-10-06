package com.corejsf;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class UserBean implements Serializable {
	private User user = new User();
	private User validUser;
	@Inject
	private Service service;

	public User getUser() {
		return user;
	}

	public String login() {
		validUser = service.getValidUser(user);
		if (validUser != null) {
			user.update(validUser);
			return "welcome";
		} else {
			user = new User();
			return "error";
		}
	}
}
