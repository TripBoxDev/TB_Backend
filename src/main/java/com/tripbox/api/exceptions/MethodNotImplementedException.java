package com.tripbox.api.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class MethodNotImplementedException extends WebApplicationException {

	/**
	 * Create a HTTP 501 (Not Implemented) exception.
	 */
	public MethodNotImplementedException() {
		super(Response.status(Response.Status.NOT_IMPLEMENTED).build());
	}

	/**
	 * Create a HTTP 501 (Not Implemented) exception.
	 * 
	 * @param message the String that is the entity of the 501 response.
	 */
	public MethodNotImplementedException(String message) {
		super(Response.status(Response.Status.NOT_IMPLEMENTED).entity(message)
				.type("text/plain").build());
	}
}
