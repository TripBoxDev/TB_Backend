package com.tripbox.api;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.api.exceptions.MethodNotImplementedException;
import com.tripbox.api.exceptions.RequiredParamsFail;
import com.tripbox.api.exceptions.UserNotExistOnGroupREST;
import com.tripbox.api.interfaces.GroupREST;
import com.tripbox.elements.Card;
import com.tripbox.elements.Group;
import com.tripbox.elements.OtherCard;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.Vote;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.exceptions.CardTypeException;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.DestinationDoesntExistException;
import com.tripbox.services.exceptions.ElementNotFoundServiceException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.GroupService;

@Path("/group")
public class GroupRESTImpl implements GroupREST {

	GroupService groupService = new GroupServiceImpl();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroup(@PathParam("id") String id) {
		try {
			return Response.ok(groupService.getGroup(id)).build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException("Item, " + id + ", is not found");
		} catch (Exception e) {
			throw new WebApplicationException();
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putGroup(Group group) {
		try {
			return Response.ok(groupService.putGroup(group)).build();
		} catch (InvalidIdsException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteGroup(@PathParam("id") String id) {
		throw new MethodNotImplementedException("Method not implemented");
	}

	@DELETE
	@Path("/{groupId}/user/{userId}")
	public Response deleteUserToGroup(@PathParam("groupId") String groupId,
			@PathParam("userId") String userId) {
		try {
			groupService.deleteUserToGroup(groupId, userId);
			return Response.ok().build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (UserNotExistOnGroup ex) {
			throw new UserNotExistOnGroupREST("User: " + userId
					+ " doesn't exist on this group");
		} catch (Exception e) {

			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}

	@PUT
	@Path("/{id}/destination")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response putDestination(@PathParam("id") String id,
			String newDestination) {

		try {
			return Response.ok(groupService.putDestination(id, newDestination)).build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException();
		}
	}

	@DELETE
	@Path("/{groupId}/destination/{idDestination}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteDestination(@PathParam("id") String id,
			@PathParam("idDestination") String idDestination) {
		try {
			groupService.deleteDestination(id, idDestination);
			return Response.ok().build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException();
		}
	}

	@PUT
	@Path("/{id}/transportCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCard(@PathParam("id") String id, TransportCard card) {
		try {
			return Response.ok(groupService.putCard(id, card)).build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (CardTypeException e) {
			throw new RequiredParamsFail("Card Type doesn't exist");
		} catch (DestinationDoesntExistException exDes) {
			throw new ElementNotFoundException("Destination "
					+ card.getDestination() + " doesn't exist");
		} catch (InvalidIdsException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}

	@PUT
	@Path("/{id}/placeToSleepCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCard(@PathParam("id") String id, PlaceToSleepCard card) {
		try {
			return Response.ok(groupService.putCard(id, card)).build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (CardTypeException e) {
			throw new RequiredParamsFail("Card Type doesn't exist");
		} catch (DestinationDoesntExistException exDes) {
			throw new ElementNotFoundException("Destination "
					+ card.getDestination() + " doesn't exist");
		} catch (InvalidIdsException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}

	@PUT
	@Path("/{id}/otherCard")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putCard(@PathParam("id") String id, OtherCard card) {
		try {
			return Response.ok(groupService.putCard(id, card)).build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (CardTypeException e) {
			throw new RequiredParamsFail("Card Type doesn't exist");
		} catch (DestinationDoesntExistException exDes) {
			throw new ElementNotFoundException("Destination "
					+ card.getDestination() + " doesn't exist");
		} catch (InvalidIdsException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}

	@DELETE
	@Path("/{groupId}/card/{cardId}")
	public Response deleteCard(@PathParam("groupId") String groupId,
			@PathParam("cardId") String cardId) {
		try {
			groupService.deleteCard(groupId, cardId);
			return Response.ok().build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}

	@PUT
	@Path("/{groupId}/card/{cardId}/vote")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putVote(@PathParam("groupId") String groupId,
			@PathParam("cardId") String cardId, Vote vote) {
		try {
			return Response.ok(groupService.putVote(groupId, cardId, vote))
					.build();
		} catch (ElementNotFoundServiceException e) {
			throw new ElementNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new WebApplicationException(e.getStackTrace().toString());
		}
	}

	@PUT
	@Path("/{groupId}/image")
	@Consumes("image/jpeg")
	public Response saveGroupImage(@PathParam("groupId") String groupId,
			File fileImage) {

		String uploadedFileLocation = "/var/www/groupImgs/" + groupId + ".jpg";

		try {
			groupService.saveGroupImage(groupId, fileImage,
					uploadedFileLocation);
		} catch (InvalidIdsException exc) {
			throw new ElementNotFoundException(exc.getMessage());
		} catch (Exception e) {
			throw new ElementNotFoundException("Item not found");
		}

		return Response.status(200).build();
	}

	@PUT
	@Path("/{groupId}/transport/{idTransporte}/sleep/{idAlojamiento}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response finalProposition(@PathParam("groupId") String groupId, @PathParam("idTransporte") String idTransporte,
			@PathParam("idAlojamiento") String idAlojamiento) {
		try {
			return Response.ok(groupService.finalProposition(groupId, idTransporte, idAlojamiento)).build();
		} catch (InvalidIdsException exc) {
			throw new ElementNotFoundException(exc.getMessage());
		} catch (Exception e) {
			throw new ElementNotFoundException("Item not found");
		}
	}
}
