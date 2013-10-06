package com.corejsf;

public class User {
	private String name, password;
	private int number;

	public User(String name, String password, int number) {
		this.name = name;
		this.password = password;
		this.number = number;
	}

	public User() {
		this("", "", 0);
	}

	public void update(User user) {
//		name = user.name;
//		password = user.password;
		number = user.number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
