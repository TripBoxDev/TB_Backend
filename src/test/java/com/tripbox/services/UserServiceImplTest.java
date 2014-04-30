package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.ElementNotFoundServiceException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.RequiredParametersException;

public class UserServiceImplTest {
	
	static UserServiceImpl userSTTest = new UserServiceImpl();
	static GroupServiceImpl groupSTTest = new GroupServiceImpl();
	
	static ArrayList<String> groups = new ArrayList<String>();
	static ArrayList<String> googleGroups = new ArrayList<String>();
	static ArrayList<String> testGroups = new ArrayList<String>();
	static ArrayList<String> userList = new ArrayList<String>();
	static ArrayList<String> emptyList = new ArrayList<String>();
	
	static User testUser = new User();
	static User resultUser = new User();
	
	static User userToGet = new User();
	static User userToAdd = new User();
	static User emptyUser = new User();
	static Group groupToAdd = new Group();
	static Group resultGroup = new Group();
	static Group emptyGroup = new Group();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		groups.add("556677");
		groups.add("335577");
		googleGroups.add("113355");
		googleGroups.add("224466");
		userList.add("123456");
		
		userToGet = new User();
		userToGet.setName("userToGet");
		userToGet.setEmail("userToGet@mail.com");
		userToGet.setGroups(groups);
		userToGet = userSTTest.putUser(userToGet);
		
		userToAdd = new User();
		userToAdd.setFacebookId("f123");
		userToAdd.setName("userToAddName");
		userToAdd.setGroups(groups);
		userToAdd = userSTTest.putUser(userToAdd);
		
		emptyUser = new User();
		emptyUser.setGoogleId("g567");
		emptyUser.setName("emptyUser");
		emptyUser.setGroups(emptyList);
		emptyUser = userSTTest.putUser(emptyUser);
		
		groupToAdd = new Group();
		groupToAdd.setName("grupo");
		groupToAdd.setUsers(userList);
		groupToAdd = groupSTTest.putGroup(groupToAdd);
		
		emptyGroup = new Group();
		emptyGroup.setName("emptyGroup");
		emptyGroup.setUsers(emptyList);
		emptyGroup = groupSTTest.putGroup(emptyGroup);
	}
	
	@Before
	public void setUp() throws Exception{
		resultUser=null;
	}
	
	@Test
	public void testGetUser() throws Exception {
		//Test usuario existente
		resultUser = userSTTest.getUser(userToGet.getId());
		assertEquals(userToGet.getId(), resultUser.getId());
		
		//Test usuario no existente
		try {
			resultUser = userSTTest.getUser("123");
			fail(); //Si el GET no da error el test fallara
		} catch (Exception e){
			
		}
	}
	
	@Test
	/**
	 * Test de un usuario que inicialmente solo tiene facebook ID (para que no de error) y nombre.
	 * Hacemos el PUT y comprobamos que con GET nos devuelve el mismo usuario con todos sus campos actualizados
	 * @throws Exception
	 */
	public void testPutUserWithId() throws Exception {	

		try{
			testUser = new User();
			testUser.setFacebookId("654321");
			testUser.setName("Obligatori Name");
			testUser = userSTTest.putUser(testUser);
			
			//Actualizamos el usuario anteriormente insertado
			testUser = new User(); 
			testUser.setFacebookId("321654");
			testUser.setGoogleId("543216");
			testUser.setName("Def");
			testUser.setLastName("Usr");
			testUser.setEmail("defUsr@hotmail.com");
			testUser.setGroups(groups);
			resultUser = userSTTest.putUser(testUser);
	
			assertTrue(resultUser.getId()==testUser.getId());
			assertTrue(resultUser.getFacebookId()=="321654");
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
		testUser = new User();
		testUser.setFacebookId("f654321");
		testUser.setName("Pers1");
		testUser.setLastName("Cigarrer");
		testUser.setEmail("userFacebook@hotmail.com");
		testUser.setGroups(groups);

		try {
			resultUser = userSTTest.putUser(testUser);

			resultUser = userSTTest.getUser(resultUser.getId());
			assertNotNull(resultUser.getId());
			assertEquals(resultUser.getFacebookId(), "f654321");
			assertNull(resultUser.getGoogleId());
			assertEquals(resultUser.getName(), "Pers1");
			assertEquals(resultUser.getLastName(), "Cigarrer");
			assertEquals(resultUser.getEmail(), "userFacebook@hotmail.com");

			assertEquals(resultUser.getGroups(), groups);

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
		testUser = new User();
		testUser.setGoogleId("g654321");
		testUser.setName("googlePers");
		testUser.setLastName("cogGoogle");
		testUser.setEmail("google@gmail.com");
		testUser.setGroups(googleGroups);

		try {
			resultUser = userSTTest.putUser(testUser);

			resultUser = userSTTest.getUser(resultUser.getId());

			assertNotNull(resultUser.getId());
			assertNull(resultUser.getFacebookId());
			assertEquals(resultUser.getGoogleId(), "g654321");
			assertEquals(resultUser.getName(), "googlePers");
			assertEquals(resultUser.getLastName(), "cogGoogle");
			assertEquals(resultUser.getEmail(), "google@gmail.com");

			testGroups = resultUser.getGroups();

			assertEquals(testGroups, googleGroups);
			
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
		
		testUser = new User();
		testUser.setName("mailPers");
		testUser.setLastName("cogMail");
		testUser.setEmail("mail@gmail.com");
		
		try {
			resultUser = userSTTest.putUser(testUser);

			resultUser = userSTTest.getUser(resultUser.getId());
			assertNotNull(resultUser.getId());
			assertNull(resultUser.getFacebookId());
			assertNull(resultUser.getGoogleId());
			assertEquals(resultUser.getName(), "mailPers");
			assertEquals(resultUser.getLastName(), "cogMail");
			assertEquals(resultUser.getEmail(), "mail@gmail.com");
			assertTrue(resultUser.getGroups().isEmpty());
			
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
	 * Test de la funcion a??adir una persona a un grupo cuando el grupo ya tiene 
	 * usuarios y el usuario ya esta en otros grupos
	 * @throws Exception
	 */
	public void testAddGroupToUser(){
		try{
			userSTTest.addGroupToUser(userToAdd.getId(), groupToAdd.getId());
		
			//Get del grupo y usuario que acabamos de modificar
			resultUser = userSTTest.getUser(userToAdd.getId());
			resultGroup = groupSTTest.getGroup(groupToAdd.getId());
			
			groups = resultUser.getGroups();
			userList = resultGroup.getUsers();
			
			//Comprobamos que se han modificado correctamente
			assertTrue(groups.contains(groupToAdd.getId()));
			assertTrue(userList.contains(userToAdd.getId()));
			
			
		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	/**
	 * Test de la funcion a??adir una persona a un grupo cuando este grupo
	 * todavia no tiene nadie dentro ni esa persona est?? en ning??n grupo
	 * @throws Exception
	 */
	public void testAddGroupToUserEmptyGroup() throws Exception {
		try {
			userSTTest.addGroupToUser(emptyUser.getId(), emptyGroup.getId());
			
			//Get del grupo y usuario que acabamos de modificar
			resultUser = userSTTest.getUser(emptyUser.getId());
			resultGroup = groupSTTest.getGroup(emptyGroup.getId());
			
			groups = resultUser.getGroups();
			userList = resultGroup.getUsers();
			
			//Comprobamos que se han modificado correctamente
			assertTrue(groups.contains(emptyGroup.getId()));
			assertTrue(userList.contains(emptyUser.getId()));
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	/**
	 * Test de la funcion que deber??a dar excepciones como control de errores.
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
		} catch (ElementNotFoundServiceException e) {
			
		}
		
		try {
			userSTTest.addGroupToUser("999", groupToAdd.getId());
			fail();
		} catch (ElementNotFoundServiceException e) {
			
		}
	}
	
	
	@After
	public void tearDown() throws Exception{
		try {
			if ((resultUser!=null)&&(resultUser.getId()!=null)){
				userSTTest.deleteUser(resultUser.getId());
			}
		} catch (Exception e){
			fail();
		}
	}
	 
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		groupSTTest.deleteGroup(groupToAdd.getId());
		groupSTTest.deleteGroup(emptyGroup.getId());
	}
}
