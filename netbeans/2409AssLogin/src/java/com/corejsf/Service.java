
package com.corejsf;


import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named(value="service")
@ApplicationScoped
public class Service {

    private ArrayList<User> users= new ArrayList<User>();

	public Service() {
		users.add(new User("Tarzan", "111", 1));
		users.add(new User("Jane", "222", 2));
	}
	public User getValidUser(User user) {
		for (User u : users) {
			if (u.getName().equals(user.getName())
					&& u.getPassword().equals(user.getPassword())) {
				return u;
			}
		}
		return null;
	}

}
