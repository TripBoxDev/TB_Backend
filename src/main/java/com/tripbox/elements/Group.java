package com.tripbox.elements;

import java.util.ArrayList;

public class Group {
	
	private String id = null;
	private String name = null;
	private String description=null;
	private ArrayList<String> users= new ArrayList<String>();
	
	/**
	 * Array con los destinos creados en un Group.
	 */
	private ArrayList<String> destinations = new ArrayList<String>();
	private ArrayList<Card> transportCards  =  new ArrayList<Card>();	
	private ArrayList<Card> placeToSleepCards  =  new ArrayList<Card>();
	private ArrayList<Card> otherCards  =  new ArrayList<Card>();
	
	public Group(){}
	
	
	public Group(String id, String name, String description,
			ArrayList<String> users, ArrayList<String> destinations,
			ArrayList<Card> transportCards, ArrayList<Card> placeToSleepCards,
			ArrayList<Card> otherCards) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
		this.destinations = destinations;
		this.transportCards = transportCards;
		this.placeToSleepCards = placeToSleepCards;
		this.otherCards = otherCards;
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
	public ArrayList<String> getDestinations() {
		return destinations;
	}

	public void setDestinations(ArrayList<String> destinations) {
		this.destinations = destinations;
	}

	public ArrayList<Card> getTransportCards() {
		return transportCards;
	}

	public void setTransportCards(ArrayList<Card> transportCards) {
		this.transportCards = transportCards;
	}

	public ArrayList<Card> getPlaceToSleepCards() {
		return placeToSleepCards;
	}

	public void setPlaceToSleepCards(ArrayList<Card> placeToSleepCards) {
		this.placeToSleepCards = placeToSleepCards;
	}

	public ArrayList<Card> getOtherCards() {
		return otherCards;
	}

	public void setOtherCards(ArrayList<Card> otherCards) {
		this.otherCards = otherCards;
	}
}
