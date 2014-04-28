
package com.tripbox.api;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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

		// a�adimos usuario y grupo nuevos
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
	public void testGetUser() {
		
		try {
			 
			Client client = Client.create();
	 
			//Test cliente existente
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/user/123456");
	 
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	 
			
			
			String output = response.getEntity(String.class);
			
			
			assertTrue(output.contains("123456"));
			
			//test cliente no existente
			
			WebResource webResource1 = client
					   .resource("http://localhost:8080/TB_Backend/api/user/123sd245asd");
			 
					ClientResponse response1 = webResource1.accept("application/json")
			                   .get(ClientResponse.class);
					
			assertTrue(response1.getStatus() != 200);		
			
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
		
		
	
	
	}

	@Test
	public void testPutUser() {
		try {
			 
			Client client = Client.create();
			
			// Insertar nuevo usuario
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/user/");
			
			String input = "{\"id\" : \"\",\"facebookId\" : \"\",\"googleId\" : \"\",\"name\" : \"Josep\",\"lastName\" : \"Morato\",\"email\" : \"josepM@gmail.com\",\"groups\" : [ \"445566\", \"98765\" ] }";
	 
			ClientResponse response = webResource.type("application/json")
			   .put(ClientResponse.class, input);
	 
	 
			
			String output = response.getEntity(String.class);
			assertTrue(output.contains("josepM@gmail.com"));
			
			// Usuario sin ID pero con facebook ID
			
			input = "{\"id\" : \"\",\"facebookId\" : \"154876541\",\"googleId\" : \"\",\"name\" : \"Oriol\",\"lastName\" : \"Gonzalez\",\"email\" : \"oriol@gmail.com\",\"groups\" : [ \"86466\", \"67865\" ] }";
			response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			
			output = response.getEntity(String.class);
			assertTrue(output.contains("oriol@gmail.com"));
			
			// Usuario sin ID pero con google ID
			
			input = "{\"id\" : \"\",\"facebookId\" : \"\",\"googleId\" : \"5468654\",\"name\" : \"Greg\",\"lastName\" : \"Guzman\",\"email\" : \"gguzma@gmail.com\",\"groups\" : [ \"86466\", \"67865\" ] }";
			response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			
			output = response.getEntity(String.class);
			assertTrue(output.contains("gguzma@gmail.com"));
			
			// Usuario sin ID pero con email
			
			input = "{\"id\" : \"\",\"facebookId\" : \"\",\"googleId\" : \"\",\"name\" : \"Paco\",\"lastName\" : \"Gala\",\"email\" : \"fgala@gmail.com\",\"groups\" : [ \"86466\", \"67865\" ] }";
			response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			
			output = response.getEntity(String.class);
			assertTrue(output.contains("fgala@gmail.com"));
			
			//Usuario no existente con ID  alternativa pero sin nombre:
			
			input = "{\"id\" : \"\",\"facebookId\" : \"46454\",\"googleId\" : \"\",\"name\" : \"\",\"lastName\" : \"Gala\",\"email\" : \"\",\"groups\" : [ \"86466\", \"67865\" ] }";
			try{
				output= null;
			response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			
			output = response.getEntity(String.class);
			} catch (Exception e){
				assertTrue(output==null);
			}
			
			//Usuario sin ninguna ID ni mail:
			
			input = "{\"id\" : \"\",\"facebookId\" : \"\",\"googleId\" : \"\",\"name\" : \"Ted\",\"lastName\" : \"Mosby\",\"email\" : \"\",\"groups\" : [ \"86466\", \"67865\" ] }";
			try{
				output= null;
			response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			
			output = response.getEntity(String.class);
			} catch (Exception e){
				assertTrue(output==null);
			}
			
			//Usuario con ID inexistente:
			
			input = "{\"id\" : \"123\",\"facebookId\" : \"\",\"googleId\" : \"\",\"name\" : \"Ted\",\"lastName\" : \"Mosby\",\"email\" : \"\",\"groups\" : [ \"86466\", \"67865\" ] }";
			try{
				output= null;
			response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			
			output = response.getEntity(String.class);
			} catch (Exception e){
				assertTrue(output==null);
			}
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	}

	@Test
	public void testDeleteUser() {
		
		
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
	
	@AfterClass
	public static void tearDown() throws Exception{
		WebResource webResource4 = client
				.resource("http://localhost:8080/TB_Backend/api/group/" + output2);

		ClientResponse response4 = webResource4.accept("application/json").delete(
				ClientResponse.class);
		
		WebResource webResource5 = client
				.resource("http://localhost:8080/TB_Backend/api/user/" + output);

		ClientResponse response5 = webResource5.accept("application/json").delete(
				ClientResponse.class);
	}


}

