package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Card;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImplTest {
	
	static Querys bbdd = Mock.getInstance();
	
	static GroupServiceImpl grupoServ = new GroupServiceImpl();
	static UserService userService = new UserServiceImpl();
	static ArrayList<String> groups= new ArrayList<String>();
	static ArrayList<String> users= new ArrayList<String>();
	static ArrayList<String> destinations= new ArrayList<String>();
	static Group testGroup;
	static Group resultGroup;
	static Group grupo1;
	static Group grupo2;
	static Group groupToPut;
	static User usuario;
	static User userNotInGroup;
	
	static Group cardTestGroup;
	static Group cardTestGroupWrInputs;
	
	
	@BeforeClass

	public static void SetUpBeforeClass() throws Exception{
		destinations.add("Roma");
		destinations.add("Paris");

		

		usuario = new User(null,"jo","ja","ji","gh", "lo", groups);
		userNotInGroup = new User(null,"fID", null, "userNotInGroupName", null, null, groups);
		

		users.add(usuario.getId());

		//a�adimos usuario para el deleteUserToGroup
		try {
			userService.putUser(usuario);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		testGroup = new Group(null, "testGroup", "grupo para tests", users);

		grupo1 = new Group(null,"prueba1","nada", users);
		grupo2 = new Group(null,"prueba1","nada", users);
		groupToPut = new Group(null,"groupToPutName","un grupo para anadir", users);
		cardTestGroup = new Group(null,"cardTestGroupName","grupo para testeo de funciones de las cards", users);
		cardTestGroupWrInputs = new Group(null,"cardTestGroupWrInputsName","grupo para testeo de funciones de las cards", users);
		cardTestGroup.setDestinations(destinations);
		cardTestGroupWrInputs.setDestinations(destinations);
		
		grupoServ.putGroup(testGroup);
		grupoServ.putGroup(grupo2);
		grupoServ.putGroup(cardTestGroup);
		grupoServ.putGroup(cardTestGroupWrInputs);
		
	
		userService.putUser(usuario);
	}
	
	@Before
	public void Setup(){
		resultGroup = null;
	}
	
	@Test
	public void testPutGroup() {
		
		try {
			grupoServ.putGroup(grupo1);
			assertNotNull(grupo1.getId());
			assertNotNull(grupoServ.getGroup(grupo1.getId()));
			
			users.add(usuario.getId());

			resultGroup = grupoServ.putGroup(groupToPut);
			assertTrue(resultGroup.getId() == groupToPut.getId());

			assertNotNull(grupo2.getUsers());
			assertNotNull(grupoServ.getGroup(groupToPut.getId()));
			
		} catch (InvalidIdsException e) {
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void testGetGroup() throws Exception {
		Group grupo;
		resultGroup = grupoServ.getGroup(testGroup.getId());
		
		assertEquals(resultGroup.getName(), "testGroup");

		
		try {
			grupoServ.getGroup("123");
			fail();   //no puede nunca encontrar este usuario
		} catch (ElementNotFoundException exc) {
			
		}
		
	}

	@Test
	public void testDeleteGroup() throws Exception {
		
		try{
			grupoServ.deleteGroup(grupo1.getId());
		} catch(ElementNotFoundException e){
			fail();		//El grupo existe, asiq ue no tiene que fallar
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		try{
			grupoServ.deleteGroup("52");
			fail();		//No existe el grupo, asi que tiene que fallar
		} catch (Exception e) {
		
		}
	}

	@Test
	public void testDeleteUserToGroup() throws Exception {
		
		//eliminamos usuario existente de grupo existente
		try {
			grupoServ.deleteUserToGroup(grupo2.getId(), usuario.getId());
			
		} catch (UserNotExistOnGroup e) {
			fail();
		} catch (InvalidIdsException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			fail();
		}
		
		//eliminamos usuario existente de grupo INEXISTENTE (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup("0000", usuario.getId());
			fail();
		} catch (Exception e) {
			
		}
		
		//eliminamos usuario INEXISTENTE de grupo existente (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup(grupo2.getId(), userNotInGroup.getId());
			fail();
		} catch (InvalidIdsException e) {
			
		}
	}
	
	/**
	 * Test de la funcion PutDestination
	 * @throws Exception
	 */
	@Test
	public void testPutDestination() throws Exception {
		try {
			grupoServ.putDestination(cardTestGroup.getId(), "Tokyo");
			
			resultGroup = bbdd.getGroup(cardTestGroup.getId());
			
			assertTrue(resultGroup.getDestinations().contains("Tokyo"));
			
		} catch (ElementNotFoundException e) {
			fail();
		} catch (DestinationAlreadyExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * Test de la funcion PutDestination cuando el grupo no existe y 
	 * cuando el destino que se quiere anadir ya existe.
	 * @throws Exception
	 */
	@Test
	public void testPutDestinationWrongInputs() throws Exception {
		try {
			grupoServ.putDestination("333", "Roma");
			fail();		
		} catch (ElementNotFoundException e) {

		} catch (DestinationAlreadyExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		
		
		try {
			grupoServ.putDestination(cardTestGroupWrInputs.getId(), "Roma");
			fail();
			
		} catch (ElementNotFoundException e2) {
			fail();
		} catch (DestinationAlreadyExistException e2) {
			resultGroup = bbdd.getGroup(cardTestGroupWrInputs.getId());
			assertTrue(resultGroup.getDestinations().contains("Roma"));
		} catch (Exception e2) {
			fail();
		}
	}
	
	@Test
	public void testDeleteDestination() throws Exception {
		cardTestGroup = new Group(null,"cardTestGroupName","grupo para testeo de funciones de las cards", users);
		cardTestGroup.setDestinations(destinations);
		grupoServ.putGroup(cardTestGroup);
		
		try{
			grupoServ.deleteDestination(cardTestGroup.getId(), "Paris");
			
			resultGroup = bbdd.getGroup(cardTestGroup.getId());
			assertFalse(resultGroup.getDestinations().contains("Paris"));
			
		//TODO mirar si les cards asociades a aquest desti han sigut esborrades	
		} catch (ElementNotFoundException e){
			fail();
		} catch (Exception e){
			fail();
		}
	}
	
	/**
	 * Test de la funcion DeleteDestination cuando el grupo no existe y 
	 * cuando el destino que se quiere borrar tampoco existe.
	 * @throws Exception
	 */
	@Test
	public void testDeleteDestinationWrongInputs() throws Exception {
		cardTestGroup = new Group(null,"cardTestGroupName","grupo para testeo de funciones de las cards", users);
		cardTestGroup.setDestinations(destinations);
		grupoServ.putGroup(cardTestGroup);
		
		try{
			grupoServ.deleteDestination("555", "Paris");
			fail();
		} catch (ElementNotFoundException e){

		} catch (Exception e){
			fail();
		}
		
		
		
		try{
			grupoServ.deleteDestination(cardTestGroupWrInputs.getId(), "Tailandia");
			fail();
		} catch (ElementNotFoundException e){

		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	public void testPutCard() throws Exception {
		// TODO
	}

	@Test
	public void testDeleteCard() throws Exception {
		// TODO
		
	}

	@After
	public void tearDown() throws Exception{
		try {
			if ((resultGroup!=null)&&(resultGroup.getId()!=null)){
				grupoServ.deleteGroup(resultGroup.getId());
			}
		} catch (Exception e){
			
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass(){

		try {
			userService.deleteUser(usuario.getId());
			grupoServ.deleteGroup(grupo2.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
