package com.tripbox.api;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GroupRESTImplTest {

	@Test
	public void testGetGroup() {
		try {
			 
			Client client = Client.create();
	 
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/group/445566");
	 
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	 
			
			
			String output = response.getEntity(String.class);
			
			
			
			assertTrue(output.contains("445566"));
			
			
			
			WebResource webResource1 = client
					   .resource("http://localhost:8080/TB_Backend/api/group/123sd245asd");
			 
					ClientResponse response1 = webResource1.accept("application/json")
			                   .get(ClientResponse.class);
					
			assertTrue(response1.getStatus() != 200);		
			
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
		
		
	
	}

	@Test
	public void testPutGroup() {
		try {
			 
			Client client = Client.create();
	 
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/group/");
			
			String input = "{\"id\" : \"\",\"name\" : \"Uni\",\"description\" : \"viajee\",\"users\" : [ \"123456\", \"165432\" ] }";
	 
			ClientResponse response = webResource.type("application/json")
			   .put(ClientResponse.class, input);
	 
	 
			
			String output = response.getEntity(String.class);
			assertTrue(output.contains("Uni"));
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	}

	@Test
	public void testDeleteGroup() {
		fail("Not yet implemented");
	}

}
