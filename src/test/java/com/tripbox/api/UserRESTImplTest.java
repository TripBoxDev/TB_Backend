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
	 
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/user/123456");
	 
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	 
			
			
			String output = response.getEntity(String.class);
			
			
			String a = "{ \n"
					 + "  \"id\" : \"123456\", \n"
					 + "  \"facebookId\" : \"f123456\", \n"
					 + "  \"googleId\" : \"g123456\", \n"
					 + "  \"name\" : \"Pepitu\", \n"
					 + "  \"lastName\" : \"Sigaler\", \n"
					 + "  \"email\" : \"psigaler@gmail.com\", \n"
					 + "  \"groups\" : [ \"445566\", \"98765\" ] \n"
					 +"}";
			
			assertTrue(output.contains("123456"));
			
			
			
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
	 
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/user/");
			
			String input = "{\"id\" : \"\",\"facebookId\" : \"\",\"googleId\" : \"\",\"name\" : \"Josep\",\"lastName\" : \"Morato\",\"email\" : \"josepM@gmail.com\",\"groups\" : [ \"445566\", \"98765\" ] }";
	 
			ClientResponse response = webResource.type("application/json")
			   .put(ClientResponse.class, input);
	 
	 
			
			String output = response.getEntity(String.class);
			assertTrue(output.contains("josepM@gmail.com"));
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

}
