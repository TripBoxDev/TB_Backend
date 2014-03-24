package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.User;

public interface UserREST {
	
	public Response getUser(String id);
	public Response putUser(User user);
	public void deleteUser(String id);

}
