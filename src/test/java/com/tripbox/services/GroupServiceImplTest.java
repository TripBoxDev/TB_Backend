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
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.ElementNotFoundServiceException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImplTest {
	
	static GroupServiceImpl grupoServ = new GroupServiceImpl();
	static UserService userService = new UserServiceImpl();
	static ArrayList<String> groups= new ArrayList<String>();
	static ArrayList<String> users= new ArrayList<String>();
	static ArrayList<String> destinations= new ArrayList<String>();
	static Group testGroup;
	static Group resultGroup;
	static Group putDeleteTestGroup;
	static Group grupo2;
	static Group groupToPut;
	static User usuario;
	static User userNotInGroup;
	
	static Group destTestGroup;
	static Group cardTestGroup;
	static Group cardTestGroupWrInputs;
	
	static Card destTestCard;
	
	@BeforeClass
	public static void SetUpBeforeClass() throws Exception{
		destinations.add("Roma");
		destinations.add("Paris");

		

		usuario = new User(null,"jo","ja","ji","gh", "lo", groups);
		userNotInGroup = new User(null,"fID", null, "userNotInGroupName", null, null, groups);
		
		//TODO
		users.add(usuario.getId());
		System.out.println("Users: " + users);
		//Anadimos usuario para el deleteUserToGroup
		try {
			usuario = userService.putUser(usuario);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		testGroup = new Group();
		testGroup.setName("testGroup");
		testGroup.setDescription("grupo para tests");
		testGroup.setUsers(users);
		
		putDeleteTestGroup = new Group();
		putDeleteTestGroup.setName("putDeleteTestGroup");
		putDeleteTestGroup.setUsers(users);
		
		grupo2 = new Group();
		grupo2.setName("Grupo 2");
		grupo2.setUsers(users);
		
		groupToPut = new Group();
		groupToPut.setName("groupToPutName");
		groupToPut.setDescription("un grupo para anadir");
		groupToPut.setUsers(users);
		
		destTestGroup = new Group();
		destTestGroup.setName("cardTestGroupName");
		destTestGroup.setDescription("grupo para testeo de funciones de las cards");
		destTestGroup.setUsers(users);
		destTestGroup.setDestinations(destinations);
		
		destTestCard = new TransportCard();
		destTestCard.setUserIdCreator(usuario.getId());
		destTestCard.setName("Paris transport Card");
		destTestCard.setCardType("transport");
		destTestCard.setDestination("Paris");
		
		cardTestGroup = new Group();
		cardTestGroup.setName("cardTestGroupName");
		cardTestGroup.setDescription("grupo para testeo de funciones de las cards");
		cardTestGroup.setUsers(users);
		
		cardTestGroupWrInputs = new Group();
		cardTestGroupWrInputs.setName("cardTestGroupWrInputsName");
		cardTestGroupWrInputs.setDescription("grupo para testeo de funciones de las cards");
		cardTestGroupWrInputs.setUsers(users);
		
		
		cardTestGroup.setDestinations(destinations);
		cardTestGroupWrInputs.setDestinations(destinations);
		
		grupoServ.putGroup(testGroup);
		grupoServ.putGroup(grupo2);
		grupoServ.putGroup(destTestGroup);
		grupoServ.putCard(destTestGroup.getId(), destTestCard);
		destTestGroup = grupoServ.getGroup(destTestGroup.getId());
		grupoServ.putGroup(cardTestGroup);
		grupoServ.putGroup(cardTestGroupWrInputs);
		
		
	}
	
	@Before
	public void Setup(){
		resultGroup = null;
	}
	
	@Test
	public void testPutGroup() {
		
		try {
			grupoServ.putGroup(putDeleteTestGroup);
			assertNotNull(putDeleteTestGroup.getId());
			assertNotNull(grupoServ.getGroup(putDeleteTestGroup.getId()));
			
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
		resultGroup = grupoServ.getGroup(testGroup.getId());
		
		assertEquals(resultGroup.getName(), "testGroup");

		
		try {
			grupoServ.getGroup("123");
			fail();   //no puede nunca encontrar este usuario
		} catch (ElementNotFoundServiceException exc) {
			
		} catch (Exception e) {
			fail();
		}
		
	}

	@Test
	public void testDeleteGroup() throws Exception {
		
		try{
			grupoServ.deleteGroup(putDeleteTestGroup.getId());
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
		System.out.println(grupo2.getUsers());
		//Eliminamos usuario existente de grupo existente
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
		
		//Como "usuario" era el unico user en "grupo2" ahora "grupo2" deberia haber sido borrado
		try {
			grupoServ.getGroup(grupo2.getId()); //TODO
			fail();
		} catch (Exception e) {
			
		}
		
		//Eliminamos usuario existente de grupo INEXISTENTE (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup("0000", usuario.getId());
			fail();
		} catch (Exception e) {
			
		}
		
		//Eliminamos usuario INEXISTENTE de grupo existente (no tiene que funcionar)
		try {
			grupoServ.deleteUserToGroup(grupo2.getId(), userNotInGroup.getId());
			fail();
		} catch (InvalidIdsException e) {
			
		}
	}
	

	@Test
	public void testPutDestination() throws Exception {
		try {
			grupoServ.putDestination(cardTestGroup.getId(), "Tokyo");
			
			resultGroup = grupoServ.getGroup(cardTestGroup.getId());
			
			assertTrue(resultGroup.getDestinations().contains("Tokyo"));
			
		} catch (ElementNotFoundServiceException e) {
			fail();
		} catch (DestinationAlreadyExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	

	@Test
	public void testPutDestinationWrongInputs() throws Exception {
		//ID del grupo incorrecto
		try {
			grupoServ.putDestination("333", "Roma");
			fail();		
		} catch (ElementNotFoundServiceException e) {

		} catch (DestinationAlreadyExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		
		//El destino ya existe
		try {
			grupoServ.putDestination(cardTestGroupWrInputs.getId(), "Roma");
			fail();
			
		} catch (ElementNotFoundServiceException e) {
			fail();
		} catch (DestinationAlreadyExistException e) {
			resultGroup = grupoServ.getGroup(cardTestGroupWrInputs.getId());
			assertTrue(resultGroup.getDestinations().contains("Roma"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testDeleteDestination() throws Exception {
		
		try{
			ArrayList<TransportCard> tCards = destTestGroup.getTransportCards();
			//Nos aseguramos que haya una card con destino a Paris
			assertEquals(tCards.get(0).getDestination(), "Paris");
			
			//Se elimina Paris como destino
			grupoServ.deleteDestination(destTestGroup.getId(), "Paris");
			
			resultGroup = grupoServ.getGroup(destTestGroup.getId());
			
			//Comprobamos que Paris ya no esta entre las destinaciones del grupo
			assertFalse(resultGroup.getDestinations().contains("Paris"));
			
			//Comprobamos que se hayan eliminado las cards relacionadas con Paris
			assertFalse(destTestGroup.getTransportCards().contains(destTestCard));
			
		//TODO mirar si les cards asociades a aquest desti han sigut esborrades	
		} catch (ElementNotFoundServiceException e){
			fail();
		} catch (Exception e){
			e.printStackTrace();
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
		
		try{
			grupoServ.deleteDestination("555", "Paris");
			fail();
		} catch (ElementNotFoundServiceException e){

		} catch (Exception e){
			fail();
		}
		
		
		try{
			grupoServ.deleteDestination(cardTestGroupWrInputs.getId(), "Tailandia");
			fail();
		} catch (ElementNotFoundServiceException e){

		} catch (Exception e){
			fail();
		}
	}
	
	@Test
	public void testPutCard() throws Exception {
		//TODO
		fail();
	}

	@Test
	public void testDeleteCard() throws Exception {
		//TODO
		fail();
		
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
			
			grupoServ.deleteGroup(testGroup.getId());
			grupoServ.deleteGroup(groupToPut.getId());
			
			grupoServ.deleteGroup(destTestGroup.getId());
			grupoServ.deleteGroup(cardTestGroup.getId());
			grupoServ.deleteGroup(cardTestGroupWrInputs.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
