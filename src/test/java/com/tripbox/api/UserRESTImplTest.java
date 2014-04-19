package com.tripbox.api;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.codehaus.jackson.map.util.JSONPObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.interfaces.UserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UserRESTImplTest {

	@Test
	public void testAddGroupToUser() {
		Client client = Client.create();

		// usuario y grupo existente
		WebResource webResource = client
				.resource("http://localhost:8080/TB_Backend/api/user/123456/group/445566");

		ClientResponse response = webResource.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea correcta
		assertTrue(response.getStatus() == 200);


		
		
		// usuario NO existente y grupo existente
		WebResource webResource2 = client
				.resource("http://localhost:8080/TB_Backend/api/user/1234/group/445566");

		ClientResponse response2 = webResource2.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response2.getStatus() == 404);

		
		
		// usuario existente y grupo NO existente
		WebResource webResource3 = client
				.resource("http://localhost:8080/TB_Backend/api/user/123456/group/44554816");

		ClientResponse response3 = webResource3.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response3.getStatus() == 404);

	}

}
