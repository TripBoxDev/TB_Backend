package com.tripbox.elements;

import java.util.ArrayList;

public abstract class Card {
	private String cardId = null;
	private String cardType = null;
	private String name = null;
	private String description = null;
	private long creationDate = 0l;
	private String link = null;
	private double price;
	private String destination = null;
	private String userIdCreator = null;
	private String nameCreator = null;
	private String lastNameCreator = null;
	private ArrayList<Vote> votes = new ArrayList<Vote>();

	/**
	 * Valor máximo de un voto.
	 */
	final private int MAX_VOTE = 5;

	/**
	 * Media de votos de la card.
	 */
	private double average;

	public Card() {
	}

	public Card(String cardId, String cardType, String name,
			String description, long creationDate, String link, double price,
			String destination, String userId, String nameCreator,
			String lastNameCreator) {
		super();
		this.cardId = cardId;
		this.cardType = cardType;
		this.name = name;
		this.description = description;
		this.creationDate = creationDate;
		this.link = link;
		this.price = price;
		this.destination = destination;
		this.userIdCreator = userId;
		this.nameCreator = nameCreator;
		this.lastNameCreator = lastNameCreator;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getUserIdCreator() {
		return userIdCreator;
	}

	public void setUserIdCreator(String userId) {
		this.userIdCreator = userId;
	}

	public String getNameCreator() {
		return nameCreator;
	}

	public void setNameCreator(String nameCreator) {
		this.nameCreator = nameCreator;
	}

	public String getLastNameCreator() {
		return lastNameCreator;
	}

	public void setLastNameCreator(String lastNameCreator) {
		this.lastNameCreator = lastNameCreator;
	}

	public ArrayList<Vote> getVotes() {
		return votes;
	}

	public void setVotes(ArrayList<Vote> votes) {
		this.votes = votes;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public void calculateAverage() {
		double totalValue = 0;
		for (Vote vote : votes) {
			totalValue += vote.getValue();
		}
		this.setAverage(totalValue / votes.size());
	}

}