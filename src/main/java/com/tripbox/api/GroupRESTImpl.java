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

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.api.exceptions.MethodNotImplementedException;
import com.tripbox.api.exceptions.RequiredParamsFail;
import com.tripbox.api.exceptions.UserNotExistOnGroupREST;
import com.tripbox.api.interfaces.GroupREST;
import com.tripbox.elements.Card;
import com.tripbox.elements.Group;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.exceptions.CardTypeException;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.GroupService;


@Path("/group")
public class GroupRESTImpl implements GroupREST{

	GroupService groupService = new GroupServiceImpl();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroup(@PathParam("id") String id) {
		try{
			return Response.ok(groupService.getGroup(id)).build();
		}catch (Exception e) {
			throw new ElementNotFoundException("Item, " + id + ", is not found");
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putGroup(Group group) {
		try{
			return Response.ok(groupService.putGroup(group)).build();
		}catch (Exception e) {
			throw new ElementNotFoundException("Item not found");
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteGroup(@PathParam("id") String id) {
			throw new MethodNotImplementedException("Method not implemented");
	}
	
	@DELETE
	@Path("/{groupId}/user/{userId}")
	public Response deleteUserToGroup(@PathParam("groupId") String groupId, @PathParam("userId") String userId) {
		try{
			groupService.deleteUserToGroup(groupId, userId);
			return Response.ok().build();
		}catch(UserNotExistOnGroup ex){
			throw new UserNotExistOnGroupREST("User: "+userId+" doesn't exist on this group");
		}catch (Exception e){
			
			throw new ElementNotFoundException(e.getMessage());
		}
	}
	
	@PUT
	@Path("/{id}/destination")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response putDestination(@PathParam("id") String id, String newDestination){
		
		try {
			System.out.println("PUT destination REST");
			groupService.putDestination(id, newDestination);
			return Response.ok().build();
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new RequiredParamsFail("Destination already exists");
		}
	}
	
	
	@DELETE
	@Path("/{id}/destination")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteDestination(@PathParam("id") String id, String destinationToDelete){
		try {
			System.out.println("DELETE destination REST");
			groupService.deleteDestination(id, destinationToDelete);
			return Response.ok().build();
		} catch (Exception e) {
			throw new ElementNotFoundException(e.getMessage());
		}
	}
	
	@PUT
	@Path("/{id}/card")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCard(@PathParam("id") String id, Card card){
		try {
			System.out.println("PUT card REST");
			return Response.ok(groupService.putCard(id, card)).build();
		} catch (ElementNotFoundException e){
			throw new ElementNotFoundException(e.getMessage());
		} catch (CardTypeException e) {
			throw new RequiredParamsFail("Card Type doesn't exist");
		} catch (Exception e){
			throw new RequiredParamsFail(e.getMessage());
		}
	}
	
	@DELETE
	@Path("/{groupId}/card/{cardId}")
	public Response deleteCard(@PathParam("groupId") String groupId, @PathParam("cardId") String cardId){
		try {
			System.out.println("DELETE card REST");
			groupService.deleteCard(groupId, cardId);
			return Response.ok().build();
		} catch (Exception e) {
			throw new ElementNotFoundException(e.getMessage());
		}
	}
	
}
