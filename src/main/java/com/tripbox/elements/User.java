package com.tripbox.elements;

import java.util.ArrayList;

public class User {
	
	private String id = null;
	private String name = null;
	private String lastName=null;
	private String email=null;
	private ArrayList<String> groups = new ArrayList<String>();
	
	public User(){}
	
	public User(String id, String name, String lastName, String email,
			ArrayList<String> groups) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.groups = groups;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<String> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}

}
