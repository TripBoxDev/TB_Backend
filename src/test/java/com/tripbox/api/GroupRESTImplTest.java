package com.tripbox.api;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.GroupServiceImpl;
import com.tripbox.services.UserServiceImpl;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;

public class GroupRESTImplTest {
	
	static GroupServiceImpl gService;
	static UserServiceImpl uService;
	
	//static Client client = Client.create();
	//static WebResource webResource;
	//static ClientResponse response;
	
	static ArrayList<String> groupUsers= new ArrayList<String>();
	static Group testGroup;
	static User testUser;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testUser = new User(null, "f123456789", null, "testGroupAPI", null, null, null);
		testGroup = new Group();
		testGroup.setName("testGroupAPI");
		
		/*try {
			uService.putUser(testUser);
		} catch (InvalidIdsException e){
			System.out.println("IDs");
		} catch (RequiredParametersException e) {
			System.out.println("Params");
		}*/
		
		//gService.putGroup(testGroup);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//uService.deleteUser(testUser.getId());
		//gService.deleteGroup(testGroup.getId());
	}

	@Test
	public void testPutDestination() {
		//webResource = client.resource("http://localhost:8080/TB_Backend/api/group/");

	}

	@Test
	public void testDeleteDestination() {
		fail("Not yet implemented");
	}

	@Test
	public void testPutCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCard() {
		fail("Not yet implemented");
	}

}
