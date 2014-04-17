package com.tripbox.api;

import static org.junit.Assert.*;

import org.codehaus.jackson.map.util.JSONPObject;
import org.junit.Test;

import com.tripbox.elements.User;
import com.tripbox.services.UserServiceImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
 

public class UserRESTImplTest {

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

}