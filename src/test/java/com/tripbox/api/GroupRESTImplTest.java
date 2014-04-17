package com.tripbox.api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GroupRESTImplTest {

	@Test
	public void testGetGroup() {
		try {
			 
			Client client = Client.create();
			
			//Grup Existent
			
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/group/445566");
	 
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	 
			
			
			String output = response.getEntity(String.class);
			
			
			
			assertTrue(output.contains("445566"));
			
			// Grup no existeix
			
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
			// Crear un nou grup
			WebResource webResource = client
			   .resource("http://localhost:8080/TB_Backend/api/group/");
			
			String input = "{\"id\" : \"\",\"name\" : \"Uni\",\"description\" : \"viajee\",\"users\" : [ \"123456\", \"165432\" ] }";
	 
			ClientResponse response = webResource.type("application/json")
			   .put(ClientResponse.class, input);
	 
			String output = response.getEntity(String.class);
			assertTrue(output.contains("Uni"));
			
			//Modificar un grup
			
			webResource = client
					   .resource("http://localhost:8080/TB_Backend/api/group/");
					
					input = "{\"id\" : \"331188\",\"name\" : \"Tripbox\",\"description\" : \"viajee\",\"users\" : [ \"123456\", \"165432\" ] }";
			 
					 response = webResource.type("application/json")
					   .put(ClientResponse.class, input);
			 
					 output = response.getEntity(String.class);
					assertTrue(output.contains("331188"));
					assertTrue(output.contains("Tripbox"));
			
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	}

	@Test
	public void testDeleteGroup() {
		
		
		
	}

	@Test
	public void testDeleteUserToGroup() {
		
		//eliminamos usuario existente de grupo existente
		
		Client client = Client.create();
		WebResource webResource = client
		   .resource("http://localhost:8080/TB_Backend/api/group/445566/123456");
 
		ClientResponse response = webResource.accept("application/json")
                   .delete(ClientResponse.class);
 
		
		assertTrue(response.getStatus() == 200);	
		
		//eliminamos usuario existente de grupo INEXISTENTE (no tiene que funcionar)
		
		client = Client.create();
		webResource = client
		   .resource("http://localhost:8080/TB_Backend/api/group/466/123456");
 
		 response = webResource.accept("application/json")
                   .delete(ClientResponse.class);
 
		
		assertTrue(response.getStatus() != 200);
		
		//eliminamos usuario INEXISTENTE de grupo existente (no tiene que funcionar)
		
		client = Client.create();
		webResource = client
		   .resource("http://localhost:8080/TB_Backend/api/group/445566/126");
 
		 response = webResource.accept("application/json")
                   .delete(ClientResponse.class);
 
		
		assertTrue(response.getStatus() != 200);
	}
	
}
