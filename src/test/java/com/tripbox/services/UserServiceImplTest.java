package com.tripbox.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;

public class UserServiceImplTest {
	
	@Test
	public void testGetUser() throws Exception {
		UserServiceImpl userSTTest = new UserServiceImpl();

		User usr = new User();
		
		//Test usuario existente
		usr = userSTTest.getUser("123456");
		assertTrue(usr.getId()=="123456");
		
		
		//Test usuario no existente
		try {
			usr = userSTTest.getUser("123");
			fail(); //Si el GET no da error el test fallara
		} catch (Exception e){
			
		}
	}
	
	@Test
	public void testPutUser() throws Exception {
		
		UserServiceImpl userSTTest = new UserServiceImpl();
		
		Querys bbdd = Mock.getInstance();
		
		User defaultUsr = new User();
		
		ArrayList<String> groups = new ArrayList<String>();
		groups.add("556677");
		groups.add("335577");
		
		ArrayList<String> googleGroups = new ArrayList<String>();
		groups.add("113355");
		groups.add("224466");
		
		ArrayList<String> testGroups = new ArrayList<String>();
		
		//Usuario que inicialmente solo tiene con ID. Hacemos el PUT y comprobamos que con GET nos
		//devuelve el mismo usuario con todos sus campos actualizados:
		try{
			User usr = new User("0", "654321", "543216", "Def", "Usr", "defUsr@hotmail.com",groups); //Actualizamos el usuario anteriormente insertado
			
			defaultUsr = userSTTest.putUser(usr);
			
			assertTrue(defaultUsr.getFacebookId()=="654321");
			assertTrue(defaultUsr.getGoogleId()=="543216");
			assertTrue(defaultUsr.getName()=="Def");
			assertTrue(defaultUsr.getLastName()=="Usr");
			assertTrue(defaultUsr.getEmail()=="defUsr@hotmail.com");
			assertTrue(defaultUsr.getGroups()==groups);
			
			userSTTest.deleteUser(defaultUsr.getId());
		} catch (RequiredParametersException e) {
			fail();
		} catch (InvalidIdsException e) {
			fail();
		} catch (Exception e){
			fail();
		}
		
		//Usuario sin ID pero con facebookID:
		User fbUsr = new User(null, "f654321", null, "Pers1", "Cigarrer", "userFacebook@hotmail.com",groups);
		
		User fbUsr2 = new User();
		
		try {
			fbUsr2 = userSTTest.putUser(fbUsr);
			
			fbUsr2 = bbdd.getUserbyFacebookId("f654321");
			
			assertNotNull(fbUsr2.getId());
			assertTrue(fbUsr2.getFacebookId()=="f654321");
			assertNull(fbUsr2.getGoogleId());
			assertTrue(fbUsr2.getName()=="Pers1");
			assertTrue(fbUsr2.getLastName()=="Cigarrer");
			assertTrue(fbUsr2.getEmail()=="userFacebook@hotmail.com");
			 
			assertTrue(fbUsr2.getGroups()==groups);
			
			userSTTest.deleteUser(fbUsr2.getId());
		} catch (Exception e){
			fail();
		}
		
			
		//Usuario sin ID pero con googleID:
		User gUsr = new User(null, null, "g654321", "googlePers", "cogGoogle", "google@gmail.com", googleGroups);
		
		User gUsr2 = new User();
		
		try {
			gUsr2 = userSTTest.putUser(gUsr);

			gUsr2 = bbdd.getUserbyGoogleId("g654321");
			
			assertNotNull(gUsr2.getId());
			assertNull(gUsr2.getFacebookId());
			assertTrue(gUsr2.getGoogleId()=="g654321");
			assertTrue(gUsr2.getName()=="googlePers");
			assertTrue(gUsr2.getLastName()=="cogGoogle");
			assertTrue(gUsr2.getEmail()=="google@gmail.com");
			
			testGroups = gUsr2.getGroups();
			
			assertTrue(testGroups==googleGroups);
			
			userSTTest.deleteUser(gUsr2.getId());
		} catch (InvalidIdsException e) { 
			fail();
		} catch (RequiredParametersException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		
		//Usuario sin ninguna ID pero con mail:
		User mailUsr = new User(null, null, null, "mailPers", "cogMail", "mail@gmail.com", null);
		
		User mailUsr2 = new User();
		
		try {
			mailUsr2 = userSTTest.putUser(mailUsr);

			mailUsr2 = bbdd.getUserbyEmail("mail@gmail.com");
			
			assertNotNull(mailUsr2.getId());
			assertNull(mailUsr2.getFacebookId());
			assertNull(mailUsr2.getGoogleId());
			assertTrue(mailUsr2.getName()=="mailPers");
			assertTrue(mailUsr2.getLastName()=="cogMail");
			assertTrue(mailUsr2.getEmail()=="mail@gmail.com");
			
			assertNull(mailUsr2.getGroups());
			
			userSTTest.deleteUser(mailUsr2.getId());
		} catch (InvalidIdsException e) { 
			fail();
		} catch (RequiredParametersException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		
		
		//Usuario no existente con ID  alternativa pero sin nombre:
		User unknownUsr = new User(null, "noNameUserFbId", null, null, "NoOne", null, groups);
		
		try {
			defaultUsr = userSTTest.putUser(unknownUsr);
			fail(); //Si el GET no da error el test fallara
		} catch (InvalidIdsException e){
			
		} catch (Exception e){
			
		}
		
		//Usuario sin ninguna ID ni mail:
		User badUsr = new User(null, null, null, "badPers", "cogBad", null, groups);
				
		try {
			defaultUsr = userSTTest.putUser(badUsr);
			fail(); //Si el GET no da error el test fallara
		} catch (InvalidIdsException e){
					
		}
		
		//Usuario con ID inexistente:
		badUsr = new User("123", null, null, "badPers", "cogBad", null, groups);
				
		try {
			defaultUsr = userSTTest.putUser(badUsr);
			fail(); //Si el GET no da error el test fallara
		} catch (InvalidIdsException e){
			
		}
	}

	@Test
	public void testDeleteUser() {
		UserServiceImpl userSTTest = new UserServiceImpl();
		
		User delUsr = new User(null, null, "g123", "deletePers", null, "delete@gmail.com", null);
		try {
			userSTTest.putUser(delUsr);
		} catch (Exception e) {
			fail();
		}
		
		try {
			userSTTest.deleteUser(delUsr.getId());
		} catch (Exception e) {
			fail();
		}
		
		try {
			userSTTest.getUser(delUsr.getId());
			fail();	//El usuario debia ser borrado. Si no falla quiere decir que lo ha encontrado y no lo ha eliminado correctamente
		} catch (Exception e) {
			
		}
	}
}