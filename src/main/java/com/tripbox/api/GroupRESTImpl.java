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
import com.tripbox.api.interfaces.GroupREST;
import com.tripbox.elements.Group;
import com.tripbox.services.GroupServiceImpl;
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
		try{
			groupService.deleteGroup(id);
			return Response.ok().build();
		}catch (Exception e) {
			throw new ElementNotFoundException("Item, " + id + ", is not found");
		}
		
	}

}
