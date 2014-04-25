package com.tripbox.api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;

public class GroupRESTImplTest {
	
	final static int POS_INICIO_ID=12;
	final static int POS_FINAL_ID=24;
	
	static GroupServiceImpl gService;
	static UserServiceImpl uService;
	
	static Client client;
	static ClientResponse response;
	static WebResource webResource;
	
	static ArrayList<String> groupUsers= new ArrayList<String>();
	static Group testGroup;
	static User testUser;
	
	static String testGroupID;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		client = Client.create();
		
		//Introduir usuari per testejar
		webResource = client.resource("http://localhost:8080/TB_Backend/api/group/");

		String input = "{\"name\" : \"Test Group\",\"description\" : \"Grupo para testear la API\",\"users\" : [ \"123456\", \"165432\" ] }";
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
		webResource = client.resource("http://localhost:8080/TB_Backend/api/group/"+testGroupID+"/Oslo");
		System.out.println("http://localhost:8080/TB_Backend/api/group/"+testGroupID+"/Oslo");
		response = webResource.accept("application/json").put(ClientResponse.class);
		
		//TODO respon amb 404, no esta be
		System.out.println("Headers: " + response.getStatus());
		assertTrue(response.getStatus() == 200);
		
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	@Test
	public void testDeleteDestination() {
		System.out.println("\nDELETE: ");
		webResource = client.resource("http://localhost:8080/TB_Backend/api/group/"+testGroupID+"/Oslo");
		System.out.println("http://localhost:8080/TB_Backend/api/group/"+testGroupID+"/Oslo");
		response = webResource.accept("application/json").delete(ClientResponse.class);
		
		//TODO respon amb 404, no esta be
		System.out.println("Headers: " + response.getStatus());
		assertTrue(response.getStatus() == 200);
		
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	@Test
	public void testPutCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCard() {
		fail("Not yet implemented");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//TODO borrar testGroup
		//webResource = client.resource("http://localhost:8080/TB_Backend/api/group/"+testGroupID);
		//response = webResource.type("application/json").delete(ClientResponse.class);
		//System.out.println(response.getStatus()); //No puc esborrar el grup perque el metode no esta implementat xD
	}

}
