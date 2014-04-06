package com.tripbox.api.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class RequiredParamsFail extends WebApplicationException{

	 /**
	  * Create a HTTP 412 (Precondition Failed) exception.
	  */
	  public RequiredParamsFail() {
	    super(Response.status(Response.Status.PRECONDITION_FAILED).build());
	  }
	 
	  /**
	  *  Create a HTTP 412 (Precondition Failed) exception.
	  * @param message the String that is the entity of the 412 response.
	  */
	  public RequiredParamsFail(String message) {
		  super(Response.status(Response.Status.PRECONDITION_FAILED).entity(message).type("text/plain").build());
	  }
}
