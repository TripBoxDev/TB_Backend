package com.tripbox.elements;

public class Destination {

	private String id;
	private String name;
	private double percentage = 0;
	
	public Destination(){};
	
	public Destination(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public double getPercentage(){
		return percentage;
	}
	
	public void setPercentage(double percentage){
		this.percentage = percentage;
	}
}
