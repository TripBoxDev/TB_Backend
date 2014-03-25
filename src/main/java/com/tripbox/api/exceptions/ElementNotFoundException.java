package com.tripbox.api.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


@SuppressWarnings("serial")
public class ElementNotFoundException extends WebApplicationException {
	 
	  /**
	  * Create a HTTP 404 (Not Found) exception.
	  */
	  public ElementNotFoundException() {
	    super(Response.status(Response.Status.NOT_FOUND).build());
	  }
	 
	  /**
	  * Create a HTTP 404 (Not Found) exception.
	  * @param message the String that is the entity of the 404 response.
	  */
	  public ElementNotFoundException(String message) {
		  super(Response.status(Response.Status.NOT_FOUND).entity(message).type("text/plain").build());
	  }
	}
