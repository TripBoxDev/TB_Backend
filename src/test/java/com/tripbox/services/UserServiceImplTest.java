package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;

public class UserServiceImplTest {

	@Test
	public void testUserServiceImpl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUser() throws Exception {
		UserServiceImpl userSTTest = new UserServiceImpl();

		User usr = new User();
		
		//Test usuario existente
		usr = userSTTest.getUser("123456");
		assertTrue(usr.getId()=="123456");
		
		//Test usuario no existente
		//TO-DO
		
	
	}

	@Test
	public void testPutUser() throws Exception {
		
		UserServiceImpl userSTTest = new UserServiceImpl();
		
		ArrayList<String> groups = new ArrayList<String>();
		groups.add("556677");
		groups.add("335577");
		
		ArrayList<String> googleGroups = new ArrayList<String>();
		groups.add("113355");
		groups.add("224466");
		
		ArrayList<String> testGroups = new ArrayList<String>();
		
		//Usuario con ID. Hacemos el PUT y comprobamos que con GET nos
		//devuelve el mismo usuario con todos sus campos:
		//TO-DO
		
		//Usuario sin ID pero con facebookID:
		
		User fbUsr = new User(null, "654321", null, "Pers1", "Cigarrer", "userFacebook@hotmail.com",groups);
		
		User fbUsr2 = userSTTest.putUser(fbUsr);
		
		//User fbUsr2 = new User();
		
		Querys bbdd = Mock.getInstance();
		fbUsr2 = bbdd.getUserbyFacebookId("654321");
		
		assertNotNull(fbUsr2.getId());
		assertTrue(fbUsr2.getFacebookId()=="654321");
		assertNull(fbUsr2.getGoogleId());
		assertTrue(fbUsr2.getName()=="Pers1");
		assertTrue(fbUsr2.getLastName()=="Cigarrer");
		assertTrue(fbUsr2.getEmail()=="userFacebook@hotmail.com");
		
		
		testGroups = fbUsr2.getGroups();
		 
		assertTrue(testGroups.contains("556677"));
		assertTrue(testGroups.contains("335577"));
		
		
		//Usuario sin ID pero con googleID:
		User gUsr = new User(null, null, "g123456", "googlePers", "cogGoogle", "google@gmail.com",googleGroups);
		
		User gUsr2 = userSTTest.putUser(gUsr);
		
		//User gUsr2 = new User();
		
		gUsr2 = bbdd.getUserbyGoogleId("g123456");
		
		assertNotNull(gUsr2.getId());
		assertNull(gUsr2.getFacebookId());
		assertTrue(gUsr2.getGoogleId()=="g123456");
		assertTrue(gUsr2.getName()=="googlePers");
		assertTrue(gUsr2.getLastName()=="cogGoogle");
		assertTrue(gUsr2.getEmail()=="google@gmail.com");
		
		testGroups = gUsr2.getGroups();
		
		assertTrue(testGroups.contains("113355"));
		assertTrue(testGroups.contains("224466"));
		
		//Usuario sin ninguna ID:
		//TO-DO
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

}
