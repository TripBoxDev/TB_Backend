package com.tripbox.elements;

public class Vote {

	private String userId = null;
	private double value = 0.0;

	public Vote() {}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}