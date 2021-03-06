package com.tripbox.elements;

import java.util.ArrayList;

import org.jongo.marshall.jackson.oid.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Group {

	// @ObjectId
	// private String _id = null;

	@JsonProperty("_id")
	private String id = null;
	public static final String USERS = "users";
	private String name = null;
	private String description = null;
	private ArrayList<String> users = new ArrayList<String>();
	/**
	 * Array con los destinos creados en un Group.
	 */
	private ArrayList<Destination> destinations = new ArrayList<Destination>();
	private ArrayList<TransportCard> transportCards = new ArrayList<TransportCard>();
	private ArrayList<PlaceToSleepCard> placeToSleepCards = new ArrayList<PlaceToSleepCard>();
	private ArrayList<OtherCard> otherCards = new ArrayList<OtherCard>();
	private ArrayList<String> negativeVotes = new ArrayList<String>();
	private ArrayList<String> positiveVotes = new ArrayList<String>();

	/**
	 * Flag de la imagen
	 */
	private boolean flagImage = false;

	public Group() {
	}

	public Group(String id, String name, String description,
			ArrayList<String> users, ArrayList<Destination> destinations,
			ArrayList<TransportCard> transportCards,
			ArrayList<PlaceToSleepCard> placeToSleepCards,
			ArrayList<OtherCard> otherCards, boolean flagImage, ArrayList<String> negativeVotes,
			ArrayList<String> positiveVotes) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
		this.destinations = destinations;
		this.transportCards = transportCards;
		this.placeToSleepCards = placeToSleepCards;
		this.otherCards = otherCards;
		this.flagImage = flagImage;
		this.negativeVotes = negativeVotes;
		this.positiveVotes = positiveVotes;
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
	
	public boolean getFlagImage() {
		return flagImage;
	}

	public void setFlagImage(boolean flagImage) {
		this.flagImage = flagImage;
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}

	public ArrayList<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
	}

	public ArrayList<TransportCard> getTransportCards() {
		return transportCards;
	}

	public void setTransportCards(ArrayList<TransportCard> transportCards) {
		this.transportCards = transportCards;
	}

	public ArrayList<PlaceToSleepCard> getPlaceToSleepCards() {
		return placeToSleepCards;
	}

	public void setPlaceToSleepCards(
			ArrayList<PlaceToSleepCard> placeToSleepCards) {
		this.placeToSleepCards = placeToSleepCards;
	}

	public ArrayList<OtherCard> getOtherCards() {
		return otherCards;
	}

	public void setOtherCards(ArrayList<OtherCard> otherCards) {
		this.otherCards = otherCards;
	}

	public ArrayList<String> getNegativeVotes() {
		return negativeVotes;
	}

	public void setNegativeVotes(ArrayList<String> negativeVotes) {
		this.negativeVotes = negativeVotes;
	}

	public ArrayList<String> getPositiveVotes() {
		return positiveVotes;
	}

	public void setPositiveVotes(ArrayList<String> positiveVotes) {
		this.positiveVotes = positiveVotes;
	}

}