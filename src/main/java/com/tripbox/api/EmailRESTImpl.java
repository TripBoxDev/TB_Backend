package com.tripbox.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tripbox.api.interfaces.EmailREST;
import com.tripbox.elements.Email;
import com.tripbox.services.EmailServiceImpl;
import com.tripbox.services.interfaces.EmailService;

@Path("/email")
public class EmailRESTImpl implements EmailREST {

	EmailService emailService = new EmailServiceImpl();

	@Path("/invitation")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response invitation(Email email) {
		try {
			emailService.sendInvitation(email.getInvitationUrl(),
					email.getEmails());
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

}