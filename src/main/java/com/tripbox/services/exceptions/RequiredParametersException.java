package com.tripbox.services.exceptions;

@SuppressWarnings("serial")
public class RequiredParametersException extends Exception {

	public RequiredParametersException(String msg){
		super(msg);
	}
}
