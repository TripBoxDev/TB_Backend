package com.tripbox.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.api.interfaces.UserREST;
import com.tripbox.elements.User;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.interfaces.UserService;

@Path("/user")
public class UserRESTImpl implements UserREST {
	
	UserService userService = new UserServiceImpl();
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") String id) {

		try{
			return Response.ok(userService.getUser(id)).build();
		}catch (Exception e) {
			throw new ElementNotFoundException("Item, " + id + ", is not found");
		}
	}


	public Response putUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}


	public void deleteUser(String id) {
		// TODO Auto-generated method stub

	}

}
