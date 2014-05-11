package com.tripbox.elements;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	// @ObjectId
	// private String _id = null;

	@JsonProperty("_id")
	private String id = null;
	private String name = null;
	private String lastName = null;
	private String email = null;
	private ArrayList<String> groups = new ArrayList<String>();
	private String facebookId = null;
	private String googleId = null;
	private ArrayList<String> cardsVoted = new ArrayList<String>();

	public User() {
	}

	public User(String id, String facebookid, String googleId, String name,
			String lastName, String email, ArrayList<String> groups) {
		super();
		this.id = id;
		this.facebookId = facebookid;
		this.googleId = googleId;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.groups = groups;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
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

	public ArrayList<String> getCardsVoted() {
		return cardsVoted;
	}

	public void setCardsVoted(ArrayList<String> cardsVoted) {
		this.cardsVoted = cardsVoted;
	}

}