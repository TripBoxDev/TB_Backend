package com.tripbox.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tripbox.api.exceptions.MethodNotImplementedException;
import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.api.exceptions.RequiredParamsFail;
import com.tripbox.api.interfaces.UserREST;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;


@Path("/user")
public class UserRESTImpl implements UserREST {
	
	UserService userService = new UserServiceImpl();
	GroupService grupoServ = new GroupServiceImpl();
	
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putUser(User user) {
		try{
			
			return Response.ok(userService.putUser(user)).build();
		}catch (RequiredParametersException ex){
			throw new RequiredParamsFail(ex.getMessage());
		}catch (InvalidIdsException exc){
			throw new ElementNotFoundException(exc.getMessage());
		}catch (Exception e) {
			throw new ElementNotFoundException("Item not found");
		}
	}

	@PUT
	@Path("/{userId}/group/{groupId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addGroupToUser(@PathParam("userId") String id, @PathParam("groupId") String groupId){
		//comprovem que user existeix
		try{
			userService.getUser(id);
		}catch (Exception e) {
			throw new ElementNotFoundException("Item, " + id + ", is not found");
		}
		
		//comprovem que grup existeix
		try{
			grupoServ.getGroup(groupId);
		}catch (Exception e) {
			throw new ElementNotFoundException("Item, " + groupId + ", is not found");
		}
		
		try{	
			userService.addGroupToUser(id, groupId);
			return Response.ok().build();		
		}catch (RequiredParametersException ex){
			throw new RequiredParamsFail(ex.getMessage());
		}catch (InvalidIdsException exc){
			throw new ElementNotFoundException(exc.getMessage());
		}catch (Exception e) {
			throw new ElementNotFoundException("Item not found");
		}
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteUser(@PathParam("id") String id) {
		throw new MethodNotImplementedException("Method not implemented");
	}

}
