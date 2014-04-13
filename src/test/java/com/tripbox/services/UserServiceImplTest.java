package com.tripbox.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;

public class UserServiceImplTest {
	
	static UserServiceImpl userSTTest = new UserServiceImpl();
	static GroupServiceImpl groupSTTest = new GroupServiceImpl();
	static Querys bbdd = Mock.getInstance();
	
	static ArrayList<String> groups = new ArrayList<String>();
	static ArrayList<String> googleGroups = new ArrayList<String>();
	static ArrayList<String> testGroups = new ArrayList<String>();
	static ArrayList<String> userList = new ArrayList<String>();
	static ArrayList<String> emptyList = new ArrayList<String>();
	
	static User testUser = new User();
	static User resultUser = new User();
	
	static User userToAdd = new User();
	static User emptyUser = new User();
	static Group groupToAdd = new Group();
	static Group emptyGroup = new Group();
	
	@BeforeClass
	public static void setup() throws Exception{
		groups.add("556677");
		groups.add("335577");
		googleGroups.add("113355");
		googleGroups.add("224466");
		userList.add("123456");
		
		userToAdd = new User(null, "f123", null, "userToAddName", null, null, groups);
		userToAdd = userSTTest.putUser(userToAdd);
		
		emptyUser = new User(null, null, "g567", "emptyUser", null, null, emptyList);
		emptyUser = userSTTest.putUser(emptyUser);
		
		groupToAdd = new Group(null, "grupo", null, userList);
		groupToAdd = groupSTTest.putGroup(groupToAdd);
		
		emptyGroup = new Group(null, "emptyGroup", null, emptyList);
		emptyGroup = groupSTTest.putGroup(emptyGroup);
	}
	
	@Before
	public void starting() throws Exception{
		resultUser=null;
	}
	
	@Test
	public void testGetUser() throws Exception {
		//Test usuario existente
		testUser = userSTTest.getUser("123456");
		assertTrue(testUser.getId()=="123456");
		
		//Test usuario no existente
		try {
			resultUser = userSTTest.getUser("123");
			fail(); //Si el GET no da error el test fallara
		} catch (Exception e){
			
		}
	}
	
	@Test
	/**
	 * Test de un usuario que inicialmente solo tiene ID y nombre. Hacemos el PUT y comprobamos que 
	 * con GET nos devuelve el mismo usuario con todos sus campos actualizados
	 * @throws Exception
	 */
	public void testPutUserWithId() throws Exception {	

		try{
			testUser = new User("0", "654321", "543216", "Def", "Usr", "defUsr@hotmail.com",groups); //Actualizamos el usuario anteriormente insertado
			
			resultUser = userSTTest.putUser(testUser);
			
			assertTrue(resultUser.getId()=="0");
			assertTrue(resultUser.getFacebookId()=="654321");
			assertTrue(resultUser.getGoogleId()=="543216");
			assertTrue(resultUser.getName()=="Def");
			assertTrue(resultUser.getLastName()=="Usr");
			assertTrue(resultUser.getEmail()=="defUsr@hotmail.com");
			assertTrue(resultUser.getGroups()==groups);
			
		} catch (RequiredParametersException e) {
			fail();
		} catch (InvalidIdsException e) {
			fail();
		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	/**
	 * Test de un usuario sin ID pero con Facebook ID.
	 * @throws Exception
	 */
	public void testPutUserWithFbId() throws Exception {
		testUser = new User(null, "f654321", null, "Pers1", "Cigarrer", "userFacebook@hotmail.com",groups);
		
		try {
			resultUser = userSTTest.putUser(testUser);
			
			resultUser = bbdd.getUserbyFacebookId("f654321");
			
			assertNotNull(resultUser.getId());
			assertTrue(resultUser.getFacebookId()=="f654321");
			assertNull(resultUser.getGoogleId());
			assertTrue(resultUser.getName()=="Pers1");
			assertTrue(resultUser.getLastName()=="Cigarrer");
			assertTrue(resultUser.getEmail()=="userFacebook@hotmail.com");
			 
			assertTrue(resultUser.getGroups()==groups);

		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	/**
	 * Test de un usuario sin ID pero con google ID.
	 * @throws Exception
	 */
	public void testPutUserWithGoogleId() throws Exception {
		testUser = new User(null, null, "g654321", "googlePers", "cogGoogle", "google@gmail.com", googleGroups);
		
		try {
			resultUser = userSTTest.putUser(testUser);

			resultUser = bbdd.getUserbyGoogleId("g654321");
			
			assertNotNull(resultUser.getId());
			assertNull(resultUser.getFacebookId());
			assertTrue(resultUser.getGoogleId()=="g654321");
			assertTrue(resultUser.getName()=="googlePers");
			assertTrue(resultUser.getLastName()=="cogGoogle");
			assertTrue(resultUser.getEmail()=="google@gmail.com");
			
			testGroups = resultUser.getGroups();
			
			assertTrue(testGroups==googleGroups);

		} catch (InvalidIdsException e) { 
			fail();
		} catch (RequiredParametersException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	/**
	 * Test de un usuario sin ID pero con mail.
	 * @throws Exception
	 */
	public void testPutUserWithMail() throws Exception {
		
		testUser = new User(null, null, null, "mailPers", "cogMail", "mail@gmail.com", null);
		
		try {
			resultUser = userSTTest.putUser(testUser);

			resultUser = bbdd.getUserbyEmail("mail@gmail.com");
			
			assertNotNull(resultUser.getId());
			assertNull(resultUser.getFacebookId());
			assertNull(resultUser.getGoogleId());
			assertTrue(resultUser.getName()=="mailPers");
			assertTrue(resultUser.getLastName()=="cogMail");
			assertTrue(resultUser.getEmail()=="mail@gmail.com");
			
			assertNull(resultUser.getGroups());
			
		} catch (InvalidIdsException e) { 
			fail();
		} catch (RequiredParametersException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}

	
	@Test
	/**
	 * Test de usuarios que no debe permitir introducir en la BD.
	 * @throws Exception
	 */
	public void testPutUserWrongInputs() throws Exception{
		//Usuario no existente con ID  alternativa pero sin nombre:
		testUser = new User(null, "noNameUserFbId", null, null, "NoOne", null, groups);
			
		try {
			resultUser = userSTTest.putUser(testUser);
			fail(); //Si el GET no da error el test fallara
		} catch (InvalidIdsException e){
			
		} catch (Exception e){
			
		}
		
		//Usuario sin ninguna ID ni mail:
		testUser = new User(null, null, null, "badPers", "cogBad", null, groups);
				
		try {
			resultUser = userSTTest.putUser(testUser);
			fail(); //Si el GET no da error el test fallara
		} catch (InvalidIdsException e){
					
		}
		
		//Usuario con ID inexistente:
		testUser = new User("123", null, null, "badPers", "cogBad", null, groups);
				
		try {
			resultUser = userSTTest.putUser(testUser);
			fail(); //Si el GET no da error el test fallara
		} catch (InvalidIdsException e){
			
		}
	}
	
	@Test
	public void testDeleteUser() {		
		testUser = new User(null, null, "g123", "deletePers", null, "delete@gmail.com", null);
		try {
			userSTTest.putUser(testUser);
		} catch (Exception e) {
			fail();
		}
		
		try {
			userSTTest.deleteUser(testUser.getId());
		} catch (Exception e) {
			fail();
		}
		
		try {
			userSTTest.getUser(testUser.getId());
			fail();	//El usuario debia ser borrado. Si no falla quiere decir que lo ha encontrado y no lo ha eliminado correctamente
		} catch (Exception e) {
			
		}
	}
	
	@Test
	/**
	 * Test de la funcion añadir una persona a un grupo cuando el grupo ya tiene 
	 * usuarios y el usuario ya esta en otros grupos
	 * @throws Exception
	 */
	public void testAddGroupToUser(){
		try{
			userSTTest.addGroupToUser(userToAdd.getId(), groupToAdd.getId());
		
		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	/**
	 * Test de la funcion añadir una persona a un grupo cuando este grupo
	 * todavia no tiene nadie dentro ni esa persona está en ningún grupo
	 * @throws Exception
	 */
	public void testAddGroupToUserEmptyGroup() throws Exception {
		try {
			userSTTest.addGroupToUser(emptyUser.getId(), emptyGroup.getId());
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	/**
	 * Test de la funcion que debería dar excepciones como control de errores.
	 * @throws Exception
	 */
	public void testAddGroupToUserWrongInputs() throws Exception {

		//Una de las IDs es nula
		try {
			userSTTest.addGroupToUser(userToAdd.getId(), null );
			fail();
		} catch (InvalidIdsException e) {
			
		}
		
		try {
			userSTTest.addGroupToUser(null, groupToAdd.getId());
			fail();
		} catch (InvalidIdsException e) {
			
		}
		
		//No encuentra una u otra ID
		try {
			userSTTest.addGroupToUser(userToAdd.getId(), "888");
			fail();
		} catch (InvalidIdsException e) {
			
		}
		
		try {
			userSTTest.addGroupToUser("999", groupToAdd.getId());
			fail();
		} catch (InvalidIdsException e) {
			
		}
	}
	
	
	@After
	public void deleteUser() throws Exception{
		try {
			if ((resultUser!=null)&&(resultUser.getId()!=null)){
				userSTTest.deleteUser(resultUser.getId());
			}
		} catch (Exception e){
			fail();
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		userSTTest.deleteUser(userToAdd.getId());
		userSTTest.deleteUser(emptyUser.getId());

		groupSTTest.deleteGroup(groupToAdd.getId());
		groupSTTest.deleteGroup(emptyGroup.getId());
	}
}