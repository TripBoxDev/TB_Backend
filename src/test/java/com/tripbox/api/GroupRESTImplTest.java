package com.tripbox.api;


import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.management.Query;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;


public class GroupRESTImplTest {
	final static int POS_INICIO_ID=12;
	final static int POS_FINAL_ID=24;
	
	static GroupServiceImpl gService;
	static UserServiceImpl uService;
	static Query bbdd;
	
	static Client client;
	static ClientResponse response;
	static WebResource webResource;
	
	static ArrayList<String> groupUsers= new ArrayList<String>();
	static Group testGroup;
	static User testUser;
	
	final static String gURL="http://localhost:8080/TB_Backend/api/group/";
	static String testGroupID;
	static String cardId="encara no la tinc";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client = Client.create();


		//Introduir usuari per testejar
		webResource = client.resource("http://localhost:8080/TB_Backend/api/group/");

		String input = "{\"name\" : \"Test Group\",\"description\" : \"Grupo para testear la API\",\"users\" : [ \"123456\", \"165432\" ],\"destinations\" : [ \"Taiwan\" ] }";
		response = webResource.type("application/json").put(ClientResponse.class, input);

		String output = response.getEntity(String.class);
		System.out.println(output);
		//Conseguimos la ID del usuario que hemos creado
		testGroupID = output.substring(POS_INICIO_ID, POS_FINAL_ID);
	}
	

	
	@Before
	public void setUp(){
		webResource = null;
		response = null;
	}

	@Test
	public void testPutDestination() {
		webResource = client.resource(gURL+testGroupID+"/destination");
		
		String input = "Oslo";
		
		response = webResource.accept("application/json").put(ClientResponse.class, input);
	
		assertTrue(response.getStatus() == 200);
		
		//GET y comprobacion del grupo modificado correctamente.
		webResource = client.resource(gURL+testGroupID);
		response = webResource.accept("application/json").get(ClientResponse.class);
		
		String output = response.getEntity(String.class);
		assertTrue(output.contains("Oslo"));
	}
	
	@Test
	public void testPutDestinationExceptions() {
		String input;
		
		//Grupo inexistente
		webResource = client.resource(gURL+"333"+"/destination");
		input = "Oslo";
		response = webResource.accept("application/json").put(ClientResponse.class, input);
	
		assertTrue(response.getStatus() == 404);
		
		//Required params fail: El destino que intentamos introducir ya existe
		webResource = client.resource(gURL+testGroupID+"/destination");
		input = "Egipto";
		response = webResource.accept("application/json").put(ClientResponse.class, input);
		response = webResource.accept("application/json").put(ClientResponse.class, input);
		
		assertTrue(response.getStatus() == 412);
	}

	@Test
	public void testDeleteDestination() {
	
		webResource = client.resource(gURL+testGroupID+"/destination/Taiwan");
		
		try {
			response = webResource.accept("application/json").delete(ClientResponse.class);
		} catch (ElementNotFoundException e) {
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(response.getStatus() == 200);
		
		//GET y comprobacion del grupo modificado correctamente.
		webResource = client.resource(gURL+testGroupID);
		response = webResource.accept("application/json").get(ClientResponse.class);
				
		String output = response.getEntity(String.class);
		assertFalse(output.contains("Taiwan"));
	}
	
	@Test
	public void testDeleteDestinationExceptions() {
		webResource = client.resource(gURL+"333"+"/destination/Taiwan");
		response = webResource.accept("application/json").delete(ClientResponse.class);
		
		assertTrue(response.getStatus() == 404);
		
		webResource = client.resource(gURL+testGroupID+"/destination/Pekin");
		response = webResource.accept("application/json").delete(ClientResponse.class);
		
		assertTrue(response.getStatus() == 404);
	}

	@Test
	public void testPutCard() {
		//TODO
		/*System.out.println("\nPUT CARD: ");
		webResource = client.resource(gURL+testGroupID+"/Card");
		System.out.println(gURL+testGroupID+"/Card");
		
		String input = "{\"name\" : \"Card de transport\",\"destination\" : \"Oslo\",\"userId\" : 123456 }";
		
		response = webResource.type("application/json").put(ClientResponse.class, input);
		
		System.out.println("Headers: " + response.getStatus());
		
		String output = response.getEntity(String.class);
		System.out.println(output);*/
	}

	@Test
	public void testDeleteCard() {
		//TODO
		/*System.out.println("\nDELETE CARD: ");
		webResource = client.resource(gURL+testGroupID+"/Card"+cardId);
		System.out.println(gURL+testGroupID+"/Card"+cardId);
		
		response = webResource.type("application/json").delete(ClientResponse.class);
		
		System.out.println("Headers: " + response.getStatus());
		
		String output = response.getEntity(String.class);*/
		//System.out.println(output); No te sentit xk es void
		
	}
	
	
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
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//TODO borrar testGroup
		//webResource = client.resource("http://localhost:8080/TB_Backend/api/group/"+testGroupID);
		//response = webResource.type("application/json").delete(ClientResponse.class);
		//System.out.println(response.getStatus()); //No puc esborrar el grup perque el metode no esta implementat xD
	}
	

}
