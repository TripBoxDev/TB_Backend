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
	static GroupServiceImpl grupoServ = new GroupServiceImpl();
	static UserService userService = new UserServiceImpl();
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<String> groups = new ArrayList<String>();
	static String output;
	static String output2;
	static Client client = Client.create();

	@BeforeClass
	public static void SetUp() {

		// añadimos usuario y grupo nuevos
		try {
			WebResource webResource = client
					.resource("http://localhost:8080/TB_Backend/api/user/");

			String input = "{\"facebookId\" : \"\",\"googleId\" : \"\",\"name\" : \"Josep\",\"lastName\" : \"Morato\",\"email\" : \"josepM@gmail.com\",\"groups\" : [ \"445566\", \"98765\" ] }";

			ClientResponse response = webResource.type("application/json").put(
					ClientResponse.class, input);

			output = response.getEntity(String.class);

			String[] id = output.split(",");

			String[] id2 = id[0].split(":");
			output = id2[1].substring(2, 14);

			WebResource webResource2 = client
					.resource("http://localhost:8080/TB_Backend/api/group/");

			String input2 = "{\"name\" : \"Uni\",\"description\" : \"viajee\",\"users\" : [ \"123456\", \"165432\" ] }";

			ClientResponse response2 = webResource2.type("application/json")
					.put(ClientResponse.class, input2);

			output2 = response2.getEntity(String.class);

			id = output2.split(",");

			id2 = id[0].split(":");
			output2 = id2[1].substring(2, 14);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Test
	public void testAddGroupToUser() throws Exception {
		// usuario y grupo existente
		WebResource webResource = client
				.resource("http://localhost:8080/TB_Backend/api/user/" + output
						+ "/group/" + output2);

		ClientResponse response = webResource.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea correcta
		assertTrue(response.getStatus() == 200);

		// comprobamos que el usuario y el grupo estan bien
		try {
			// comprobamos que el usuario esta bien
			WebResource webResource2 = client
					.resource("http://localhost:8080/TB_Backend/api/user/" + output);

			ClientResponse response2 = webResource2.accept("application/json").get(
					ClientResponse.class);
			
			String out = response2.getEntity(String.class);
			
			// comprobamos que el grupo esta bien
			WebResource webResource3 = client
					.resource("http://localhost:8080/TB_Backend/api/group/" + output2);

			ClientResponse response3 = webResource3.accept("application/json").get(
					ClientResponse.class);
			
			String out2 = response3.getEntity(String.class);

			assertTrue(out.contains(output2));
			assertTrue(out2.contains(output));

			// eliminamos para proximas comprobaciones
			WebResource webResource4 = client
					.resource("http://localhost:8080/TB_Backend/api/group/" + output2 + "/user/" + output);

			ClientResponse response4 = webResource4.accept("application/json").delete(
					ClientResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// usuario NO existente y grupo existente
		WebResource webResource2 = client
				.resource("http://localhost:8080/TB_Backend/api/user/1234/group/"
						+ output2);

		ClientResponse response2 = webResource2.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response2.getStatus() == 404);


		try {
			// comprobamos que el usuario esta bien
			WebResource webResource5 = client
					.resource("http://localhost:8080/TB_Backend/api/user/" + output);

			ClientResponse response5 = webResource5.accept("application/json").get(
					ClientResponse.class);
			
			String out = response5.getEntity(String.class);
			
			// comprobamos que el grupo esta bien
			WebResource webResource3 = client
					.resource("http://localhost:8080/TB_Backend/api/group/" + output2);

			ClientResponse response3 = webResource3.accept("application/json").get(
					ClientResponse.class);
			
			String out2 = response3.getEntity(String.class);
			
			assertFalse(out.contains(output2));
			assertFalse(out2.contains(output));


		} catch (Exception e) {
			e.printStackTrace();
		}

		// usuario existente y grupo NO existente
		WebResource webResource3 = client
				.resource("http://localhost:8080/TB_Backend/api/user/123456/group/44554816");

		ClientResponse response3 = webResource3.accept("application/json").put(
				ClientResponse.class);

		// comprobamos que la respuesta sea item no encontrado
		assertTrue(response3.getStatus() == 404);

		try {
			
			// comprobamos que el usuario esta bien
			WebResource webResource5 = client
					.resource("http://localhost:8080/TB_Backend/api/user/" + output);

			ClientResponse response5 = webResource5.accept("application/json").get(
					ClientResponse.class);
			
			String out = response5.getEntity(String.class);
			
			// comprobamos que el grupo esta bien
			WebResource webResource6 = client
					.resource("http://localhost:8080/TB_Backend/api/group/" + output2);

			ClientResponse response6 = webResource6.accept("application/json").get(
					ClientResponse.class);
			
			String out2 = response6.getEntity(String.class);

			assertFalse(out.contains(output2));
			assertFalse(out2.contains(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
