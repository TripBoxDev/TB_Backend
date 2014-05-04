package com.tripbox.elements;

import java.util.ArrayList;

public class TransportCard extends Card {

	private long initDate=0l;
	private long finalDate=0l;
	private String transportType=null;
	private ArrayList<String> childCardsId=new ArrayList<String>();
	
	public TransportCard(){}
	
	public TransportCard(String cardId, String name,
			String description, long creationDate, String link, double price,
			String destination, String userId, String nameCreator,
			String lastNameCreator,long initDate, long finalDate, String transportType) {
		super(cardId, "transport", name, description, creationDate, link, price,
				destination, userId, nameCreator, lastNameCreator);
		
		this.initDate = initDate;
		this.finalDate = finalDate;
		this.transportType = transportType;
		
	}

	public long getInitDate() {
		return initDate;
	}

	public void setInitDate(long initDate) {
		this.initDate = initDate;
	}

	public long getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(long finalDate) {
		this.finalDate = finalDate;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public ArrayList<String> getChildCardsId() {
		return childCardsId;
	}

	public void setChildCardsId(ArrayList<String> childCardsId) {
		this.childCardsId = childCardsId;
	}

	
	
}
