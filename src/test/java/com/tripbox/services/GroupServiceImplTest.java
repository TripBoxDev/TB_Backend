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
import com.tripbox.elements.Vote;
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
	static User usuario2;
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
		
		usuario2 = new User();
		usuario2.setName("userName2");
		usuario2.setLastName("userLastName2");
		usuario2.setFacebookId("fbID2");
		usuario2.setGoogleId("gID2");
		usuario2.setEmail("myMail@mail2.com");
		usuario2.setGroups(groups);

		//Anadimos usuario para el deleteUserToGroup
		try {
			usuario = userService.putUser(usuario);
			usuario2 = userService.putUser(usuario2);
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
	
	@Test
	public void testDefinePack() throws Exception {
		ArrayList<String> parentCards = new ArrayList<String>();
		
		Vote voto = new Vote();
		voto.setUserId(usuario.getId());
		voto.setValue(5);
		
		Vote voto2 = new Vote();
		voto2.setUserId(usuario.getId());
		voto2.setValue(1);
		
		Vote voto3 = new Vote();
		voto3.setUserId(usuario.getId());
		voto3.setValue(2);
		
		Vote voto4 = new Vote();
		voto4.setUserId(usuario2.getId());
		voto4.setValue(2);

		Group packTestGroup = new Group();
		packTestGroup.setName("packTestGroupName");
		packTestGroup.setDescription("grupo para testeo de funciones de los packs");
		packTestGroup.setUsers(users);
		packTestGroup.setDestinations(destinations);
		packTestGroup = grupoServ.putGroup(packTestGroup);
		
		
		TransportCard tCard = new TransportCard();
		tCard.setUserIdCreator(usuario.getId());
		tCard.setName("Pack transport Card To Paris");
		tCard.setCardType("transport");
		tCard.setDestination("Paris");
		tCard = (TransportCard) grupoServ.putCard(packTestGroup.getId(), tCard);
		tCard = (TransportCard) grupoServ.putVote(packTestGroup.getId(), tCard.getCardId(), voto);
		
		TransportCard tCard2 = new TransportCard();
		tCard2.setUserIdCreator(usuario.getId());
		tCard2.setName("Pack transport Card To Roma");
		tCard2.setCardType("transport");
		tCard2.setDestination("Roma");
		tCard2 = (TransportCard) grupoServ.putCard(packTestGroup.getId(), tCard2);
		tCard2 = (TransportCard) grupoServ.putVote(packTestGroup.getId(), tCard2.getCardId(), voto2);
		
		TransportCard tCard3 = new TransportCard();
		tCard3.setUserIdCreator(usuario.getId());
		tCard3.setName("Pack transport Card To Roma 2");
		tCard3.setCardType("transport");
		tCard3.setDestination("Roma");
		tCard3 = (TransportCard) grupoServ.putCard(packTestGroup.getId(), tCard3);
		tCard3 = (TransportCard) grupoServ.putVote(packTestGroup.getId(), tCard2.getCardId(), voto);
		
		PlaceToSleepCard ptsCard = new PlaceToSleepCard();
		ptsCard.setUserIdCreator(usuario.getId());
		ptsCard.setName("Pack placeToSleep Card In Paris");
		ptsCard.setCardType("placeToSleep");
		ptsCard.setDestination("Paris");
		
		PlaceToSleepCard ptsCard2 = new PlaceToSleepCard();
		ptsCard2.setUserIdCreator(usuario.getId());
		ptsCard2.setName("Pack placeToSleep Card In Roma");
		ptsCard2.setCardType("placeToSleep");
		ptsCard2.setDestination("Roma");
		
		PlaceToSleepCard ptsCard3 = new PlaceToSleepCard();
		ptsCard3.setUserIdCreator(usuario.getId());
		ptsCard3.setName("Pack placeToSleep Card In Roma 2");
		ptsCard3.setCardType("placeToSleep");
		ptsCard3.setDestination("Roma");
		
		PlaceToSleepCard ptsCard4 = new PlaceToSleepCard();
		ptsCard4.setUserIdCreator(usuario.getId());
		ptsCard4.setName("Pack placeToSleep Card In Roma 3");
		ptsCard4.setCardType("placeToSleep");
		ptsCard4.setDestination("Roma");
				
		parentCards.add(tCard.getCardId());
		ptsCard.setParentCardIds(parentCards);
		ptsCard = (PlaceToSleepCard) grupoServ.putCard(packTestGroup.getId(), ptsCard);
		ptsCard = (PlaceToSleepCard) grupoServ.putVote(packTestGroup.getId(), ptsCard.getCardId(), voto);
		
		parentCards = new ArrayList<String>();
		parentCards.add(tCard2.getCardId());
		ptsCard2.setParentCardIds(parentCards);
		ptsCard2 = (PlaceToSleepCard) grupoServ.putCard(packTestGroup.getId(), ptsCard2);
		ptsCard2 = (PlaceToSleepCard) grupoServ.putVote(packTestGroup.getId(), ptsCard2.getCardId(), voto3);

		parentCards = new ArrayList<String>();
		parentCards.add(tCard3.getCardId());
		ptsCard3.setParentCardIds(parentCards);
		ptsCard3 = (PlaceToSleepCard) grupoServ.putCard(packTestGroup.getId(), ptsCard3);
		ptsCard3 = (PlaceToSleepCard) grupoServ.putVote(packTestGroup.getId(), ptsCard3.getCardId(), voto3);
		
		//Linkamos 1 transporte con 2 placeToSleep y aprovechamos para testear una card sin votos
		ptsCard4.setParentCardIds(parentCards);
		ptsCard4 = (PlaceToSleepCard) grupoServ.putCard(packTestGroup.getId(), ptsCard4);
		
		packTestGroup = grupoServ.getGroup(packTestGroup.getId());
		
		//Cards linkadas
		try{
			grupoServ.definePack(packTestGroup);
			
			for (TransportCard tIterCard: packTestGroup.getTransportCards()) {
				//Comprobamos que las cards marcadas como mejor pack son de Tranpsorte, Paris: tCard y de Roma: tCard3
				if (tIterCard.getBestPack()==true){
					if (tIterCard.getDestination().equalsIgnoreCase("Paris")){
						assertEquals(tCard.getCardId(), tIterCard.getCardId());
					}
					
					if  (tIterCard.getDestination().equalsIgnoreCase("Roma")) {
						assertEquals(tCard3.getCardId(), tIterCard.getCardId());
					}
				}
			}
			
			for (PlaceToSleepCard ptsIterCard: packTestGroup.getPlaceToSleepCards()) {
				//Comprobamos que las cards marcadas como mejor pack son de Hospedaje, Paris: ptsCard y de Roma: tCard3
				if (ptsIterCard.getBestPack()==true){
					if (ptsIterCard.getDestination().equalsIgnoreCase("Paris")){
						assertEquals(ptsCard.getCardId(), ptsIterCard.getCardId());
					}
					
					if  (ptsIterCard.getDestination().equalsIgnoreCase("Roma")) {
						assertEquals(ptsCard2.getCardId(), ptsIterCard.getCardId());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
		//Si el pack cambia: Ahora una card que no habia sido votada recibe votos y queda con la 
		//misma puntuacion pero hay mas gente que la ha votado. Por lo tanto ahora esta 
		//deberia ser parte del mejor pack de Roma
		ptsCard4 = (PlaceToSleepCard) grupoServ.putVote(packTestGroup.getId(), ptsCard3.getCardId(), voto3);
		ptsCard4 = (PlaceToSleepCard) grupoServ.putVote(packTestGroup.getId(), ptsCard3.getCardId(), voto4);
		
		packTestGroup = grupoServ.getGroup(packTestGroup.getId());
		
		try{
			grupoServ.definePack(packTestGroup);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		
		for (TransportCard tIterCard: packTestGroup.getTransportCards()) {
			//Comprobamos que las cards marcadas como mejor pack son de Tranpsorte, Paris: tCard y de Roma: tCard3
			if (tIterCard.getBestPack()==true){	
				if  (tIterCard.getDestination().equalsIgnoreCase("Roma")) {
					assertEquals(tCard3.getCardId(), tIterCard.getCardId());
				}
			}
		}
		
		for (PlaceToSleepCard ptsIterCard: packTestGroup.getPlaceToSleepCards()) {
			//Comprobamos que las cards marcadas como mejor pack son de Hospedaje, Paris: ptsCard y de Roma: tCard3
			if (ptsIterCard.getBestPack()==true){
				if  (ptsIterCard.getDestination().equalsIgnoreCase("Roma")) {
					assertEquals(ptsCard4.getCardId(), ptsIterCard.getCardId());
				}
			}
		}
		
		//Sense cartes
		grupoServ.deleteGroup(packTestGroup.getId());
	}
	
	@Test
	public void testCalculatePackPercentage() throws Exception {
		double res1, res2, res3 = 0;
		Group percentTestGroup = new Group();
		percentTestGroup.setName("percentTestGroupName");
		percentTestGroup.setDescription("grupo para testeo del calculo del porcentaje de los packs");
		percentTestGroup.setUsers(users);
		percentTestGroup.setDestinations(destinations);
		percentTestGroup = grupoServ.putGroup(percentTestGroup);
		
		TransportCard tCard = new TransportCard();
		tCard.setUserIdCreator(usuario.getId());
		tCard.setName("Percent transport Card To Paris");
		tCard.setCardType("transport");
		tCard.setDestination("Paris");
		tCard = (TransportCard) grupoServ.putCard(percentTestGroup.getId(), tCard);
		
		TransportCard tCard2 = new TransportCard();
		tCard2.setUserIdCreator(usuario.getId());
		tCard2.setName("Percent transport Card To Paris");
		tCard2.setCardType("transport");
		tCard2.setDestination("Paris");
		tCard2 = (TransportCard) grupoServ.putCard(percentTestGroup.getId(), tCard2);
		
		PlaceToSleepCard ptsCard = new PlaceToSleepCard();
		ptsCard.setUserIdCreator(usuario.getId());
		ptsCard.setName("Percent placeToSleep Card In Paris");
		ptsCard.setCardType("placeToSleep");
		ptsCard.setDestination("Paris");
		
		OtherCard oCard = new OtherCard();
		oCard.setUserIdCreator(usuario.getId());
		oCard.setName("Percent other Card To Paris");
		oCard.setCardType("other");
		oCard.setDestination("Paris");
		oCard = (OtherCard) grupoServ.putCard(percentTestGroup.getId(), oCard);
		
		OtherCard oCard2 = new OtherCard();
		oCard2.setUserIdCreator(usuario.getId());
		oCard2.setName("Percent other Card To Paris");
		oCard2.setCardType("other");
		oCard2.setDestination("Paris");
		
		ArrayList<String> parentCards = new ArrayList<String>();
		parentCards.add(tCard.getCardId());
		ptsCard.setParentCardIds(parentCards);
		ptsCard = (PlaceToSleepCard) grupoServ.putCard(percentTestGroup.getId(), ptsCard);
		
		Vote voto = new Vote();
		voto.setUserId(usuario.getId());
		voto.setValue(5);
		
		Vote voto0 = new Vote();
		voto0.setUserId(usuario.getId());
		voto0.setValue(0);
		
		tCard = (TransportCard) grupoServ.putVote(percentTestGroup.getId(), tCard.getCardId(), voto);
		ptsCard = (PlaceToSleepCard) grupoServ.putVote(percentTestGroup.getId(), ptsCard.getCardId(), voto);
		
		percentTestGroup = grupoServ.getGroup(percentTestGroup.getId());

		try {
			res1 = grupoServ.calculatePackPercentage(tCard, ptsCard, percentTestGroup);
			assertTrue(res1 == 80.0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//Si se calcula con una card no votada, se comporta como si fuera votada con 0.
		try {
			double res = grupoServ.calculatePackPercentage(tCard2, ptsCard, percentTestGroup);
			assertTrue(res == 40.0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//Anadimos other cards
		oCard = (OtherCard) grupoServ.putVote(percentTestGroup.getId(), oCard.getCardId(), voto);
		
		percentTestGroup = grupoServ.getGroup(percentTestGroup.getId());
		
		try {
			res2 = grupoServ.calculatePackPercentage(tCard, ptsCard, percentTestGroup);
			assertTrue(res2 == 100.0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		oCard2 = (OtherCard) grupoServ.putCard(percentTestGroup.getId(), oCard2);
		oCard2 = (OtherCard) grupoServ.putVote(percentTestGroup.getId(), oCard2.getCardId(), voto0);
		
		percentTestGroup = grupoServ.getGroup(percentTestGroup.getId());
		
		try {
			res3 = grupoServ.calculatePackPercentage(tCard, ptsCard, percentTestGroup);
			assertTrue(res3 == 93.0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//Se anade un nuevo usuario. Las valoraciones de las cards bajan.
		userService.addGroupToUser(usuario2.getId(), percentTestGroup.getId());
		percentTestGroup = grupoServ.getGroup(percentTestGroup.getId());
		
		try {
			double res = grupoServ.calculatePackPercentage(tCard, ptsCard, percentTestGroup);
			assertTrue(res < res3);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		grupoServ.deleteGroup(percentTestGroup.getId());
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
