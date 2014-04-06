package com.tripbox.services.Exceptions;

@SuppressWarnings("serial")
public class RequiredParametersException extends Exception {

	public RequiredParametersException(String msg){
		super(msg);
	}
}
