package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.elements.Group;
import com.tripbox.elements.OtherCard;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.User;
import com.tripbox.services.exceptions.CardTypeException;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.DestinationDoesntExistException;
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
	static ArrayList<String> destinationsTestCards= new ArrayList<String>();
	
	static Group testGetGroup;
	static Group resultGroup;
	static Group resultGroupNoDelete;
	static Group putDeleteTestGroup;
	
	static User usuario;
	static Group cardTestGroup;
	static Group cardTestGroupWrInputs;
	
	static TransportCard tTestCard;
	static PlaceToSleepCard ptsTestCard;
	static OtherCard oTestCard;
	
	static TransportCard tWrongTestCard;
	static PlaceToSleepCard ptsWrongTestCard;
	
	@BeforeClass
	public static void SetUpBeforeClass() throws Exception{
		destinations.add("Roma");
		destinations.add("Paris");
		destinationsTestCards.add("Argentina");

		usuario = new User();
		usuario.setName("userName");
		usuario.setLastName("userLastName");
		usuario.setFacebookId("fbID");
		usuario.setGoogleId("gID");
		usuario.setEmail("myMail@mail.com");
		usuario.setGroups(groups);

		//Anadimos usuario para el deleteUserToGroup
		try {
			usuario = userService.putUser(usuario);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		users.add(usuario.getId());
	
		tTestCard = new TransportCard();
		tTestCard.setUserIdCreator(usuario.getId());
		tTestCard.setName("Transport Test Card");
		tTestCard.setCardType("transport");
		tTestCard.setDestination("Argentina");
		
		tWrongTestCard = new TransportCard();
		tWrongTestCard.setUserIdCreator(usuario.getId());
		tWrongTestCard.setName("Transport Wrong Test Card");
		tWrongTestCard.setCardType("transport");
		tWrongTestCard.setDestination("Paris");
		
		ptsTestCard = new PlaceToSleepCard();
		ptsTestCard.setUserIdCreator(usuario.getId());
		ptsTestCard.setName("Place To Sleep Test Card");
		ptsTestCard.setCardType("placeToSleep");
		ptsTestCard.setDestination("Argentina");
		
		ptsWrongTestCard = new PlaceToSleepCard();
		ptsWrongTestCard.setUserIdCreator(usuario.getId());
		ptsWrongTestCard.setName("Place To Sleep Test Card");
		ptsWrongTestCard.setCardType("placeToSleep");
		ptsWrongTestCard.setDestination("Paris");
		
		oTestCard = new OtherCard();
		oTestCard.setUserIdCreator(usuario.getId());
		oTestCard.setName("Other Test Card");
		oTestCard.setCardType("other");
		oTestCard.setDestination("Argentina");
		
		cardTestGroup = new Group();
		cardTestGroup.setName("cardTestGroupName");
		cardTestGroup.setDescription("grupo para testeo de funciones de las cards");
		cardTestGroup.setUsers(users);
		
		cardTestGroupWrInputs = new Group();
		cardTestGroupWrInputs.setName("cardTestGroupWrInputsName");
		cardTestGroupWrInputs.setDescription("grupo para testeo de funciones de las cards");
		cardTestGroupWrInputs.setUsers(users);
		
		cardTestGroup.setDestinations(destinationsTestCards);
		cardTestGroupWrInputs.setDestinations(destinations);

		grupoServ.putGroup(cardTestGroup);
		grupoServ.putGroup(cardTestGroupWrInputs);
		
		
	}
	
	@Before
	public void Setup(){
		resultGroup = null;
	}
	
	@Test
	public void testPutGroup() {
		putDeleteTestGroup = new Group();
		putDeleteTestGroup.setName("putDeleteTestGroup");
		putDeleteTestGroup.setUsers(users);
		
		try {
			resultGroupNoDelete = grupoServ.putGroup(putDeleteTestGroup);
			assertNotNull(putDeleteTestGroup.getId());
			assertNotNull(grupoServ.getGroup(putDeleteTestGroup.getId()));

			assertTrue(resultGroupNoDelete.getId() == putDeleteTestGroup.getId());
			assertNotNull(resultGroupNoDelete.getUsers());
			
		} catch (InvalidIdsException e) {
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGetGroup() throws Exception {
		testGetGroup = new Group();
		testGetGroup.setName("testGroup");
		testGetGroup.setDescription("grupo para tests");
		testGetGroup.setUsers(users);
		
		grupoServ.putGroup(testGetGroup);
		
		resultGroup = grupoServ.getGroup(testGetGroup.getId());
		
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
		} catch(ElementNotFoundServiceException e){
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
		Group deleteTestGroup = new Group();
		deleteTestGroup.setName("deletetestGroup");
		deleteTestGroup.setUsers(users);
		
		grupoServ.putGroup(deleteTestGroup);
		
		//Eliminamos usuario existente de grupo existente
		try {
			grupoServ.deleteUserToGroup(deleteTestGroup.getId(), usuario.getId());
			
		} catch (UserNotExistOnGroup e) {
			fail();
		} catch (InvalidIdsException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			fail();
		}
		
		//Como "usuario" era el unico user en "deleteTestGroup" ahora "deleteTestGroup" deberia haber sido borrado
		try {
			grupoServ.getGroup(deleteTestGroup.getId());
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
		User userNotInGroup = new User();
		userNotInGroup.setFacebookId("fID");
		userNotInGroup.setName("userNotInGroupName");
		userNotInGroup.setGroups(groups);
		
		try {
			grupoServ.deleteUserToGroup(deleteTestGroup.getId(), userNotInGroup.getId());
			fail();
		} catch (InvalidIdsException e) {
			
		}
	}
	

	@Test
	public void testPutDestination() throws Exception {
		try {
			grupoServ.putDestination(cardTestGroup.getId(), "Tokyo");
			
			resultGroupNoDelete = grupoServ.getGroup(cardTestGroup.getId());
			
			assertTrue(resultGroupNoDelete.getDestinations().contains("Tokyo"));
			
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
		
		
		//El destino ya existe, ahora el throw de la excepcion esta comentado debido
		//a que creemos que no se debe lanzar ninguna excepcion si se da este caso.
		/*try {
			grupoServ.putDestination(cardTestGroupWrInputs.getId(), "Roma");
			fail();
			
		} catch (ElementNotFoundServiceException e) {
			fail();
		} catch (DestinationAlreadyExistException e) {
			resultGroup = grupoServ.getGroup(cardTestGroupWrInputs.getId());
			assertTrue(resultGroup.getDestinations().contains("Roma"));
		} catch (Exception e) {
			fail();
		}*/
	}
	
	@Test
	public void testDeleteDestination() throws Exception {
		TransportCard destTestCard = new TransportCard();
		destTestCard.setUserIdCreator(usuario.getId());
		destTestCard.setName("Paris transport Card");
		destTestCard.setCardType("transport");
		destTestCard.setDestination("Paris");
		
		Group destTestGroup = new Group();
		destTestGroup.setName("cardTestGroupName");
		destTestGroup.setDescription("grupo para testeo de funciones de las cards");
		destTestGroup.setUsers(users);
		destTestGroup.setDestinations(destinations);
		grupoServ.putGroup(destTestGroup);
		grupoServ.putCard(destTestGroup.getId(), destTestCard);
		
		destTestGroup = grupoServ.getGroup(destTestGroup.getId());
		
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
		try {
			grupoServ.putCard(cardTestGroup.getId(), tTestCard);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		ArrayList<String> parentCardIds= new ArrayList<String>();
		parentCardIds.add(tTestCard.getCardId());
		
		try {
			ptsTestCard.setParentCardIds(parentCardIds);
			grupoServ.putCard(cardTestGroup.getId(), ptsTestCard);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		try {
			grupoServ.putCard(cardTestGroup.getId(), oTestCard);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertNotNull(tTestCard.getCardId());
		assertNotNull(ptsTestCard.getCardId());
		assertNotNull(oTestCard.getCardId());
	
		cardTestGroup = grupoServ.getGroup(cardTestGroup.getId());
		
		assertEquals(cardTestGroup.getTransportCards().get(0).getName(), tTestCard.getName());
		assertEquals(cardTestGroup.getPlaceToSleepCards().get(0).getName(), ptsTestCard.getName());
		assertEquals(cardTestGroup.getOtherCards().get(0).getName(), oTestCard.getName());
		
		//Comprobamos que se puede linkar una card de tipo PlaceToSleep con una Transport
		//assertEquals(Id de la card que hay en el array de parents, Id de la card de transporte)
		assertEquals(cardTestGroup.getPlaceToSleepCards().get(0).getParentCardIds().get(0), tTestCard.getCardId());
		
	}
	
	@Test
	public void testPutCardWrongInputs() throws Exception {
		
		//Grupo inexistente
		try {
			grupoServ.putCard("333", tWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}
		
		
		//Card con Id inexistente
		tWrongTestCard.setCardId("333");
		
		try {
			grupoServ.putCard("333", tWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}
		tWrongTestCard.setCardId(null);
		
		
		//Id del usuario que ha creado la carta incorrecto
		tWrongTestCard.setUserIdCreator("333");
		
		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), tWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}
		
		tWrongTestCard.setUserIdCreator(usuario.getId());
		
		//Tipo de la card erroneo
		tWrongTestCard.setCardType("Tipo de carta erroneo");
		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), tWrongTestCard);
			fail();
		} catch (CardTypeException e) {

		} catch (Exception e) {
			fail();
		}
		
		tWrongTestCard.setCardType("transport");
		
		//El destino al que hace referencia la card no esta entre los destinos del grupo
		
		tWrongTestCard.setDestination("Desetino inexistente");
		
		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), tWrongTestCard);
			fail();
		} catch (DestinationDoesntExistException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		tWrongTestCard.setDestination("Paris");
		
		
		//El Parent Card al que hace referencia no existe
		ArrayList<String> parentCardIds= new ArrayList<String>();
		parentCardIds.add("333");
		
		ptsWrongTestCard.setParentCardIds(parentCardIds);
		
		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), ptsWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testDeleteCard() throws Exception {
		assertEquals(cardTestGroup.getTransportCards().get(0).getName(), tTestCard.getName());
		assertEquals(cardTestGroup.getPlaceToSleepCards().get(0).getName(), ptsTestCard.getName());
		assertEquals(cardTestGroup.getOtherCards().get(0).getName(), oTestCard.getName());
		
		try {
			grupoServ.deleteCard(cardTestGroup.getId(), tTestCard.getCardId());
			grupoServ.deleteCard(cardTestGroup.getId(), ptsTestCard.getCardId());
			grupoServ.deleteCard(cardTestGroup.getId(), oTestCard.getCardId());
			
		} catch (Exception e) {
			fail();
		}
		
		cardTestGroup = grupoServ.getGroup(cardTestGroup.getId());
		
		//Como solo habia una card, ahora las arrays de cards estan vacios, por eso las comprobaciones las realizamos con .isEmpty()
		assertTrue(cardTestGroup.getTransportCards().isEmpty());
		assertTrue(cardTestGroup.getPlaceToSleepCards().isEmpty());
		assertTrue(cardTestGroup.getOtherCards().isEmpty());
		
	}
	
	@Test
	public void testDeleteCardWrongInputs() throws Exception {
		//Grupo inexistente
		try {
			grupoServ.deleteCard("333", tTestCard.getCardId());
			fail();
		} catch (ElementNotFoundServiceException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		
		//CardID erroneo.
		try {
			grupoServ.deleteCard(cardTestGroupWrInputs.getId(), "333");
			fail();
		} catch (ElementNotFoundServiceException e) {
			
		} catch (Exception e) {
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception{
		try {
			if ((resultGroup!=null)&&(resultGroup.getId()!=null)){
				grupoServ.deleteGroup(resultGroup.getId());
			}
		} catch (Exception e){
			fail();
		}
	}
	
	@AfterClass
	public static void tearDownAfterClass(){

		try {
			userService.deleteUser(usuario.getId());

			grupoServ.deleteGroup(cardTestGroupWrInputs.getId());
			grupoServ.deleteGroup(cardTestGroup.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
