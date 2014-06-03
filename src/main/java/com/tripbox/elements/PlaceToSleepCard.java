package com.tripbox.elements;

import java.util.ArrayList;

public class PlaceToSleepCard extends Card {

	private ArrayList<String> parentCardIds=new ArrayList<String>();
	private long initDate=0l;
	private long finalDate=0l;
	private String placeType=null;
	
	
	public PlaceToSleepCard() {}

	public PlaceToSleepCard(String cardId,  String name,
			String description, long creationDate, String link, double price,
			String destination, String userId, String nameCreator,
			String lastNameCreator,ArrayList<String> parentCardIds, long initDate,
			long finalDate, String placeType) {
		super(cardId, "placeToSleep", name, description, creationDate, link, price,
				destination, userId, nameCreator, lastNameCreator);
		this.parentCardIds = parentCardIds;
		this.initDate = initDate;
		this.finalDate = finalDate;
		this.placeType = placeType;
	}
	
	

	public ArrayList<String> getParentCardIds() {
		return parentCardIds;
	}

	public void setParentCardIds(ArrayList<String> parentCardIds) {
		this.parentCardIds = parentCardIds;
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

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

}
