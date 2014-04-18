package com.tripbox.elements;

public class OtherCard extends Card {

	private long eventDate=0l;
	
	public OtherCard() {
		// TODO Auto-generated constructor stub
	}

	public OtherCard(String cardId, String cardType, String name,
			String description, long creationDate, String link, double price,
			String destination, String userId, String nameCreator,
			String lastNameCreator,long eventDate ) {
		super(cardId, cardType, name, description, creationDate, link, price,
				destination, userId, nameCreator, lastNameCreator);
		// TODO Auto-generated constructor stub
		this.eventDate=eventDate;
	}

	public long getEventDate() {
		return eventDate;
	}

	public void setEventDate(long eventDate) {
		this.eventDate = eventDate;
	}

}
