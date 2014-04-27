package com.tripbox.elements;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Group {

	@ObjectId
	private String _id = null;
	public static final String USERS = "users";
	private String name = null;
	private String description=null;
	private ArrayList<String> users= new ArrayList<String>();
	
	public Group(){}
	
	public Group(String id, String name, String description,
			ArrayList<String> users) {
		super();
		this._id = id;
		this.name = name;
		this.description = description;
		this.users = users;
	}
	
	public String getId() {
		return _id;
	}
	
	public void setId(String id) {
		this._id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<String> getUsers() {
		return users;
	}
	
	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}

}
