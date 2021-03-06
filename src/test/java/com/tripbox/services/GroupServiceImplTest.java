package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.mail.Transport;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tripbox.elements.Card;
import com.tripbox.elements.Destination;
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
	static ArrayList<String> groups = new ArrayList<String>();
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<String> usuarios = new ArrayList<String>();
	
	static Destination RomaDestination;
	static Destination ParisDestination;
	static Destination ArgentinaDestination;
	static Destination LondresDestination;
	static Destination MoscowDestination;
	static Destination CerdanyolaDestination;
	
	static Group testGetGroup;
	static Group resultGroup;
	static Group resultGroupNoDelete;
	static Group putDeleteTestGroup;
	static Group finalgroup;
	static Group testVoteFinal;

	static User usuario;
	static User usuario2;
	static Group cardTestGroup;
	static Group cardTestGroupWrInputs;

	static TransportCard tTestCard;
	static TransportCard tTestCard2;
	static TransportCard tTestCard3;
	static PlaceToSleepCard ptsTestCard;
	static PlaceToSleepCard ptsTestCard2;
	static OtherCard oTestCard;

	static TransportCard tWrongTestCard;
	static PlaceToSleepCard ptsWrongTestCard;

	@BeforeClass
	public static void SetUpBeforeClass() throws Exception {

		RomaDestination = new Destination();
		ParisDestination = new Destination();
		ArgentinaDestination = new Destination();
		LondresDestination = new Destination();
		MoscowDestination = new Destination();
		CerdanyolaDestination = new Destination();
		
		RomaDestination.setName("Roma");
		ParisDestination.setName("Paris");
		ArgentinaDestination.setName("Argentina");
		LondresDestination.setName("Londres");
		MoscowDestination.setName("Moscow");
		CerdanyolaDestination.setName("Cerdanyola");
		
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

		// Anadimos usuario para el deleteUserToGroup
		try {
			usuario = userService.putUser(usuario);
			usuario2 = userService.putUser(usuario2);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		users.add(usuario.getId());
		usuarios.add(usuario.getId());
		usuarios.add(usuario2.getId());

		tTestCard = new TransportCard();
		tTestCard.setUserIdCreator(usuario.getId());
		tTestCard.setName("Transport Test Card");
		tTestCard.setCardType("transport");
		tTestCard.setDestination("Argentina");

		tTestCard2 = new TransportCard();
		tTestCard2.setUserIdCreator(usuario.getId());
		tTestCard2.setName("Transport Test Card 2");
		tTestCard2.setCardType("transport");
		tTestCard2.setDestination("Moscow");

		tTestCard3 = new TransportCard();
		tTestCard3.setUserIdCreator(usuario.getId());
		tTestCard3.setName("Transport Test Card 2");
		tTestCard3.setCardType("transport");
		tTestCard3.setDestination("Moscow");

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

		ptsTestCard2 = new PlaceToSleepCard();
		ptsTestCard2.setCardId("jnfjdngjf");
		ptsTestCard2.setUserIdCreator(usuario.getId());
		ptsTestCard2.setName("Place To Sleep Test Card");
		ptsTestCard2.setCardType("placeToSleep");
		ptsTestCard2.setDestination("Tokio");
		
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
		cardTestGroup
				.setDescription("grupo para testeo de funciones de las cards");
		cardTestGroup.setUsers(users);
		
		testVoteFinal = new Group();
		testVoteFinal.setName("voteTestGroup");
		testVoteFinal
				.setDescription("grupo para testeo de funciones de las cards");
		testVoteFinal.setUsers(usuarios);
		
		cardTestGroupWrInputs = new Group();
		cardTestGroupWrInputs.setName("cardTestGroupWrInputsName");
		cardTestGroupWrInputs
				.setDescription("grupo para testeo de funciones de las cards");
		cardTestGroupWrInputs.setUsers(users);

		cardTestGroup = grupoServ.putGroup(cardTestGroup);
		cardTestGroupWrInputs = grupoServ.putGroup(cardTestGroupWrInputs);
		
		grupoServ.putDestination(cardTestGroup.getId(), "Argentina");
		grupoServ.putDestination(cardTestGroupWrInputs.getId(), "Roma");
		grupoServ.putDestination(cardTestGroupWrInputs.getId(), "Paris");
		
	}

	@Before
	public void Setup() {
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

			assertTrue(resultGroupNoDelete.getId() == putDeleteTestGroup
					.getId());
			
			resultGroupNoDelete = grupoServ.getGroup(resultGroupNoDelete.getId());
			
			assertTrue(resultGroupNoDelete.getUsers().contains(usuario.getId()));

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
			fail(); // no puede nunca encontrar este usuario
		} catch (ElementNotFoundServiceException exc) {

		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void testDeleteGroup() throws Exception {
		//System.out.println("\nID usuario: " + usuario.getId() + "\tID Grup: " + putDeleteTestGroup.getId());
		//System.out.println("users que estan en aquest grup" + putDeleteTestGroup.getUsers());
		
		usuario = userService.getUser(usuario.getId());
		//System.out.println("grups que te l'usuari: " + usuario.getGroups());
		try {
			grupoServ.deleteGroup(putDeleteTestGroup.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail(); // El grupo existe, asi que no tiene que fallar
		}

		//Intentamos borrar el mismo grupo otra vez. Como ya ha sido borrado deberia saltar una excepcion
		try {
			grupoServ.deleteGroup(putDeleteTestGroup.getId());
			fail();	
		} catch (Exception e) {

		}
		
		try {
			grupoServ.deleteGroup("52");
			fail(); // No existe el grupo, asi que tiene que fallar
		} catch (Exception e) {

		}
	}

	@Test
	public void testDeleteUserToGroup() throws Exception {
		Group deleteTestGroup = new Group();
		deleteTestGroup.setName("deletetestGroup");
		deleteTestGroup.setUsers(usuarios);

		deleteTestGroup = grupoServ.putGroup(deleteTestGroup);
		deleteTestGroup = grupoServ.getGroup(deleteTestGroup.getId());

		// Eliminamos usuario existente de grupo existente
		try {
			grupoServ.deleteUserToGroup(deleteTestGroup.getId(),
					usuario.getId());

		} catch (UserNotExistOnGroup e) {
			fail();
		} catch (InvalidIdsException e) {
			e.printStackTrace();
			fail();
		} catch (Exception e) {
			fail();
		}

		//Comprobamos que el array de usuarios del grupo ya no contiene la ID de "usuario" pero si la de "usuario2"
		deleteTestGroup = grupoServ.getGroup(deleteTestGroup.getId());
		assertFalse(deleteTestGroup.getUsers().contains(usuario.getId()));
		assertTrue(deleteTestGroup.getUsers().contains(usuario2.getId()));
		
		//Comprobamos que el array de grupos del usuario ya no contiene la ID de "deleteTestGroup"
		usuario = userService.getUser(usuario.getId());
		assertFalse(usuario.getGroups().contains(deleteTestGroup.getId()));

		//Borraremos usuario2 del grupo para comprobar si al no tener usuarios el grupo se borra automaticamente
		try {
			grupoServ.deleteUserToGroup(deleteTestGroup.getId(), usuario2.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		try {
			grupoServ.getGroup(deleteTestGroup.getId());
			fail();
		} catch (Exception e) {

		}

		// Como el grupo ha sido borrado, hay que volver a hacer el PUT para
		// seguir con el test
		deleteTestGroup.setId(null);
		deleteTestGroup = grupoServ.putGroup(deleteTestGroup);

		// Eliminamos usuario existente de grupo INEXISTENTE (no tiene que
		// funcionar)
		try {
			grupoServ.deleteUserToGroup("0000", usuario.getId());
			fail();
		} catch (Exception e) {

		}

		// Eliminamos usuario INEXISTENTE de grupo existente (no tiene que
		// funcionar)
		User userNotInGroup = new User();
		userNotInGroup.setFacebookId("fID");
		userNotInGroup.setName("userNotInGroupName");
		userNotInGroup.setGroups(groups);

		try {
			grupoServ.deleteUserToGroup(deleteTestGroup.getId(),
					userNotInGroup.getId());
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}

		grupoServ.deleteGroup(deleteTestGroup.getId());
	}

	@Test
	public void testPutDestination() throws Exception {
		try {
			grupoServ.putDestination(cardTestGroup.getId(), "Tokyo");

			resultGroupNoDelete = grupoServ.getGroup(cardTestGroup.getId());

			boolean foundDest = false;
			for (Destination dest : resultGroupNoDelete.getDestinations()) {
				//Comprobamos que todos los destinos dentro del grupo tienen ID
				assertNotNull(dest.getId());
				if (dest.getName().equalsIgnoreCase("Tokyo")) {
					foundDest = true;
				}
			}
			
			assertTrue(foundDest);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPutDestinationWrongInputs() throws Exception {
		// ID del grupo incorrecto
		try {
			grupoServ.putDestination("333", "Roma");
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (DestinationAlreadyExistException e) {
			fail();
		} catch (Exception e) {
			fail();
		}

		// El destino ya existe, ahora el throw de la excepcion esta comentado
		// debido
		// a que creemos que no se debe lanzar ninguna excepcion si se da este
		// caso.
		/*
		 * try { grupoServ.putDestination(cardTestGroupWrInputs.getId(),
		 * "Roma"); fail(); } catch (ElementNotFoundServiceException e) {
		 * fail(); } catch (DestinationAlreadyExistException e) { resultGroup =
		 * grupoServ.getGroup(cardTestGroupWrInputs.getId());
		 * assertTrue(resultGroup.getDestinations().contains("Roma")); } catch
		 * (Exception e) { fail(); }
		 */
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
		destTestGroup
				.setDescription("grupo para testeo de funciones de las cards");
		destTestGroup.setUsers(users);
		
		destTestGroup = grupoServ.putGroup(destTestGroup);

		Destination rDest = grupoServ.putDestination(destTestGroup.getId(), "Roma");
		Destination pDest = grupoServ.putDestination(destTestGroup.getId(), "Paris");
		
		destTestCard = (TransportCard) grupoServ.putCard(destTestGroup.getId(), destTestCard);

		destTestGroup = grupoServ.getGroup(destTestGroup.getId());

		try {
			// Nos aseguramos que haya una card con destino a Paris
			ArrayList<TransportCard> tCards = destTestGroup.getTransportCards();
			assertEquals(tCards.get(0).getDestination(), "Paris");
			
			// Se elimina Paris como destino
			grupoServ.deleteDestination(destTestGroup.getId(), pDest.getId());
			
			resultGroup = grupoServ.getGroup(destTestGroup.getId());

			// Comprobamos que Paris ya no existe en la array de destinos, pero Roma si.
			boolean parisDest = false;
			boolean romeDest = false;
			for (Destination dest: resultGroup.getDestinations()) {
				if (dest.getId().equalsIgnoreCase(pDest.getId())) {
					parisDest = true;
				}
				
				if (dest.getId().equalsIgnoreCase(rDest.getId())) {
					romeDest = true;
				}
			}
			
			assertFalse(parisDest);
			assertTrue(romeDest);

			// Comprobamos que la card con destino a Paris se ha eliminado
			TransportCard foundCard = (TransportCard) grupoServ.cardExistOnArray(destTestCard.getCardId(), resultGroup.getTransportCards());
			assertNull(foundCard);
			
		} catch (ElementNotFoundServiceException e) {
			fail();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test de la funcion DeleteDestination cuando el grupo no existe y cuando
	 * el destino que se quiere borrar tampoco existe.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteDestinationWrongInputs() throws Exception {

		try {
			grupoServ.deleteDestination("555", "333");
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}

		try {
			grupoServ.deleteDestination(cardTestGroupWrInputs.getId(),
					"333");
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testPutCard() throws Exception {
		TransportCard resultTCard = null;
		PlaceToSleepCard resultPtsCard = null;
		OtherCard resultOCard = null;
		try {
			resultTCard = (TransportCard) grupoServ.putCard(cardTestGroup.getId(), tTestCard);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		ArrayList<String> parentCardIds = new ArrayList<String>();
		parentCardIds.add(tTestCard.getCardId());

		try {
			ptsTestCard.setParentCardIds(parentCardIds);
			resultPtsCard = (PlaceToSleepCard) grupoServ.putCard(cardTestGroup.getId(), ptsTestCard);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		try {
			resultOCard = (OtherCard) grupoServ.putCard(cardTestGroup.getId(), oTestCard);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertNotNull(tTestCard.getCardId());
		assertNotNull(ptsTestCard.getCardId());
		assertNotNull(oTestCard.getCardId());
		assertNotNull(tTestCard.getCreationDate());
		assertNotNull(ptsTestCard.getCreationDate());
		assertNotNull(oTestCard.getCreationDate());
		
		assertEquals(tTestCard.getCreationDate(), ptsTestCard.getCreationDate(), 10);
		assertEquals(tTestCard.getCreationDate(), oTestCard.getCreationDate(), 10);
		
		cardTestGroup = grupoServ.getGroup(cardTestGroup.getId());

		assertEquals(cardTestGroup.getTransportCards().get(0).getName(),
				tTestCard.getName());
		assertEquals(cardTestGroup.getPlaceToSleepCards().get(0).getName(),
				ptsTestCard.getName());
		assertEquals(cardTestGroup.getOtherCards().get(0).getName(),
				oTestCard.getName());

		// Comprobamos que se puede linkar una card de tipo PlaceToSleep con una
		// Transport
		// assertEquals(Id de la card que hay en el array de parents, Id de la
		// card de transporte)
		cardTestGroup = grupoServ.getGroup(cardTestGroup.getId());
		
		
		//Miramos en una direccion
		PlaceToSleepCard ptsChild = (PlaceToSleepCard) grupoServ.cardExistOnArray(resultPtsCard.getCardId(), cardTestGroup.getPlaceToSleepCards());
		ArrayList<String> aParents = ptsChild.getParentCardIds();
		
		//System.out.println("\n\nPTS ID: " + ptsChild.getCardId() + "\tTrans ID: " + aParents);
		
		//Miramos en la otra direccion (linkaje bidireccional)
		TransportCard tParent = (TransportCard) grupoServ.cardExistOnArray(resultTCard.getCardId(), cardTestGroup.getTransportCards());
		ArrayList<String> aChilds = tParent.getChildCardsId();
		
		//System.out.println("Trans ID: " + tParent.getCardId() + "\tPTSs ID: " + aChilds);
		
		//Comprobaciones, 1 direccion
		PlaceToSleepCard foundCard;
		boolean found = false;
		for (String child: aChilds) {
			foundCard = (PlaceToSleepCard) grupoServ.getCard(child, "placeToSleep", cardTestGroup);
			if (foundCard != null) {
				found = true;
			}
		}
		
		assertTrue(found);

		//La otra direccion (linkaje bidireccional)
		TransportCard tFoundCard;
		found = false;
		for (String parent: aParents) {
			tFoundCard = (TransportCard) grupoServ.getCard(parent, "transport", cardTestGroup);
			if (tFoundCard != null) {
				found = true;
			}
		}
		
		assertTrue(found);
		
		//Miraremos si, haciendo el linkaje posteriormente, despues de hacer el put tambien se actualiza
		PlaceToSleepCard ptsCardToLink = new PlaceToSleepCard();
		ptsCardToLink.setName("ptsCardToLink_Name");
		ptsCardToLink.setUserIdCreator(usuario.getId());
		ptsCardToLink.setDestination("Argentina");
		ptsCardToLink.setCardType("placeToSleep");
		
		TransportCard tCardToLink = new TransportCard();
		tCardToLink.setName("tCardToLink_Name");
		tCardToLink.setUserIdCreator(usuario.getId());
		tCardToLink.setDestination("Argentina");
		tCardToLink.setCardType("transport");
		
		
		try {
			tCardToLink = (TransportCard) grupoServ.putCard(cardTestGroup.getId(), tCardToLink);
			ptsCardToLink = (PlaceToSleepCard) grupoServ.putCard(cardTestGroup.getId(), ptsCardToLink);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		ArrayList<String> parentCardIds2 = new ArrayList<>();
		parentCardIds2.add(tCardToLink.getCardId());
		ptsCardToLink.setParentCardIds(parentCardIds2);
				
		try {
			resultPtsCard = (PlaceToSleepCard) grupoServ.putCard(cardTestGroup.getId(), ptsCardToLink);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		cardTestGroup = grupoServ.getGroup(cardTestGroup.getId());
		
		//Miramos en una direccion
		ptsChild = (PlaceToSleepCard) grupoServ.cardExistOnArray(resultPtsCard.getCardId(), cardTestGroup.getPlaceToSleepCards());
		aParents = ptsChild.getParentCardIds();
		
		//System.out.println("\nPTS ID: " + ptsChild.getCardId() + "\tTrans ID: " + aParents);
		
		//Miramos en la otra direccion (linkaje bidireccional)
		tParent = (TransportCard) grupoServ.cardExistOnArray(tCardToLink.getCardId(), cardTestGroup.getTransportCards());
		aChilds = tParent.getChildCardsId();
		
		//System.out.println("Trans ID: " + tParent.getCardId() + "\tPTSs ID: " + aChilds);
		
		//Comprobaciones, 1 direccion

		found = false;
		for (String child: aChilds) {
			foundCard = (PlaceToSleepCard) grupoServ.getCard(child, "placeToSleep", cardTestGroup);
			if (foundCard != null) {
				found = true;
			}
		}
		
		assertTrue(found);

		//La otra direccion (linkaje bidireccional)
		found = false;
		for (String parent: aParents) {
			tFoundCard = (TransportCard) grupoServ.getCard(parent, "transport", cardTestGroup);
			if (tFoundCard != null) {
				found = true;
			}
		}
		
		assertTrue(found);
	}

	@Test
	public void testPutCardWrongInputs() throws Exception {

		// Grupo inexistente
		try {
			grupoServ.putCard("333", tWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}

		// Card con Id inexistente
		tWrongTestCard.setCardId("333");

		try {
			grupoServ.putCard("333", tWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}
		tWrongTestCard.setCardId(null);

		// Id del usuario que ha creado la carta incorrecto
		tWrongTestCard.setUserIdCreator("333");

		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), tWrongTestCard);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}

		tWrongTestCard.setUserIdCreator(usuario.getId());

		// Tipo de la card erroneo
		tWrongTestCard.setCardType("Tipo de carta erroneo");
		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), tWrongTestCard);
			fail();
		} catch (CardTypeException e) {

		} catch (Exception e) {
			fail();
		}

		tWrongTestCard.setCardType("transport");

		// El destino al que hace referencia la card no esta entre los destinos
		// del grupo

		tWrongTestCard.setDestination("Desetino inexistente");

		try {
			grupoServ.putCard(cardTestGroupWrInputs.getId(), tWrongTestCard);
			fail();
		} catch (DestinationDoesntExistException e) {

		} catch (Exception e) {
			fail();
		}

		tWrongTestCard.setDestination("Paris");

		// El Parent Card al que hace referencia no existe
		ArrayList<String> parentCardIds = new ArrayList<String>();
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
		assertEquals(cardTestGroup.getTransportCards().get(0).getName(),
				tTestCard.getName());
		assertEquals(cardTestGroup.getPlaceToSleepCards().get(0).getName(),
				ptsTestCard.getName());
		assertEquals(cardTestGroup.getOtherCards().get(0).getName(),
				oTestCard.getName());

		try {
			grupoServ.deleteCard(cardTestGroup.getId(), tTestCard.getCardId());
			grupoServ
					.deleteCard(cardTestGroup.getId(), ptsTestCard.getCardId());
			grupoServ.deleteCard(cardTestGroup.getId(), oTestCard.getCardId());

		} catch (Exception e) {
			fail();
		}

		cardTestGroup = grupoServ.getGroup(cardTestGroup.getId());

		// Comprobamos que las cards ya no estan en los arrays del grupo
		assertNull(grupoServ.cardExistOnArray(tTestCard.getCardId(), cardTestGroup.getTransportCards()));
		assertNull(grupoServ.cardExistOnArray(ptsTestCard.getCardId(), cardTestGroup.getPlaceToSleepCards()));
		assertNull(grupoServ.cardExistOnArray(oTestCard.getCardId(), cardTestGroup.getOtherCards()));

	}

	@Test
	public void testDeleteCardWrongInputs() throws Exception {
		// Grupo inexistente
		try {
			grupoServ.deleteCard("333", tTestCard.getCardId());
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}

		// CardID erroneo.
		try {
			grupoServ.deleteCard(cardTestGroupWrInputs.getId(), "333");
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCardExistOnArray() throws Exception {
		// Card que si existe
		Group cardExistGroup = new Group();
		cardExistGroup.setName("cardExistGroup");
		
		cardExistGroup = grupoServ.putGroup(cardExistGroup);
		
		grupoServ.putDestination(cardExistGroup.getId(), "Londres");
		grupoServ.putDestination(cardExistGroup.getId(), "Moscow");
		grupoServ.putDestination(cardExistGroup.getId(), "Cerdanyola");

		try {
			grupoServ.putCard(cardExistGroup.getId(), tTestCard2);
		} catch (Exception e){
			e.getMessage();
			e.printStackTrace();
		}
		
		cardExistGroup = grupoServ.getGroup(cardExistGroup.getId());

		try {
			TransportCard resultCard = (TransportCard) grupoServ
					.cardExistOnArray(tTestCard2.getCardId(),
							cardExistGroup.getTransportCards());

			assertEquals(resultCard.getCardId(), tTestCard2.getCardId());
			assertEquals(resultCard.getName(), tTestCard2.getName());

		} catch (Exception e) {
			fail();
		}

		// Card que existe pero no esta en la array
		try {
			TransportCard resultCard = (TransportCard) grupoServ
					.cardExistOnArray(tTestCard2.getCardId(),
							cardExistGroup.getPlaceToSleepCards());

			assertNull(resultCard);

		} catch (Exception e) {
			fail();
		}

		grupoServ.deleteGroup(cardExistGroup.getId());

	}


	@Test
	public void testPutVote() throws Exception {
		TransportCard resultCard;
		Group putVoteGroup = new Group();
		putVoteGroup.setName("cardExistGroup");
		putVoteGroup.setUsers(usuarios);
		putVoteGroup = grupoServ.putGroup(putVoteGroup);
		
		grupoServ.putDestination(putVoteGroup.getId(), "Londres");
		grupoServ.putDestination(putVoteGroup.getId(), "Moscow");
		grupoServ.putDestination(putVoteGroup.getId(), "Cerdanyola");
		
		putVoteGroup = grupoServ.getGroup(putVoteGroup.getId());

		try {
			grupoServ.putCard(putVoteGroup.getId(), tTestCard3);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		putVoteGroup = grupoServ.getGroup(putVoteGroup.getId());
		
		Vote voto = new Vote();
		voto.setUserId(usuario.getId());
		voto.setValue(4);
		Vote voto2 = new Vote();
		voto2.setUserId(usuario2.getId());
		voto2.setValue(2);
		
		try {
			// Nuevo voto
			resultCard = (TransportCard) grupoServ.putVote(
					putVoteGroup.getId(), tTestCard3.getCardId(), voto);

			assertTrue(resultCard.getVotes().get(0).getValue() == 4);
			assertTrue(resultCard.getAverage() == 4);

			// Si insertamos mas votos se hace la media correctamente
			resultCard = (TransportCard) grupoServ.putVote(
					putVoteGroup.getId(), tTestCard3.getCardId(), voto2);
			assertTrue(resultCard.getAverage() == 3);

			// Modificar Voto
			voto2.setValue(0);
			resultCard = (TransportCard) grupoServ.putVote(
					putVoteGroup.getId(), tTestCard3.getCardId(), voto2);
			assertTrue(resultCard.getAverage() == 2);
			// Si sale un usuario del grupo se recalcula la media correctamente
			grupoServ.deleteUserToGroup(putVoteGroup.getId(), usuario2.getId());
			
			putVoteGroup = grupoServ.getGroup(putVoteGroup.getId());
			resultCard = (TransportCard) grupoServ.cardExistOnArray(
					resultCard.getCardId(), putVoteGroup.getTransportCards());
			
			assertTrue(resultCard.getAverage() == 4);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		grupoServ.deleteGroup(putVoteGroup.getId());
	}

	@Test
	public void testPutVoteWrongInputs() throws Exception {
		TransportCard tTestCard4 = new TransportCard();
		tTestCard4.setUserIdCreator(usuario.getId());
		tTestCard4.setName("Transport Test Card 2");
		tTestCard4.setCardType("transport");
		tTestCard4.setDestination("Moscow");

		Vote voto = new Vote();
		voto.setUserId(usuario.getId());
		voto.setValue(5);

		Group putVoteGroup = new Group();
		putVoteGroup.setName("cardExistGroup");
		
		putVoteGroup = grupoServ.putGroup(putVoteGroup);
		
		grupoServ.putDestination(putVoteGroup.getId(), "Londres");
		grupoServ.putDestination(putVoteGroup.getId(), "Moscow");
		grupoServ.putDestination(putVoteGroup.getId(), "Cerdanyola");
		putVoteGroup.setUsers(usuarios);

		try {
			grupoServ.putCard(putVoteGroup.getId(), tTestCard4);
			putVoteGroup = grupoServ.getGroup(putVoteGroup.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		// Grupo inexistente
		try {
			grupoServ.putVote("333", tTestCard4.getCardId(), voto);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}

		// ID de la card erroneo
		try {
			grupoServ.putVote(putVoteGroup.getId(), "333", voto);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			fail();
		}

		// Card existente en un grupo en el cual el usuario no esta
		Group putVoteGroup2 = new Group();
		putVoteGroup2.setName("cardExistGroup");

		putVoteGroup2 = grupoServ.putGroup(putVoteGroup2);
		
		grupoServ.putDestination(putVoteGroup2.getId(), "Londres");
		grupoServ.putDestination(putVoteGroup2.getId(), "Moscow");
		grupoServ.putDestination(putVoteGroup2.getId(), "Cerdanyola");

		try {
			grupoServ.putVote(putVoteGroup2.getId(), tTestCard4.getCardId(),
					voto);
			fail();
		} catch (ElementNotFoundServiceException e) {

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		/*
		 * Por ahora no se controla que un voto pueda tener unos valores
		 * conretos //Valor voto incorrecto try { voto.setValue(20);
		 * grupoServ.putVote(putVoteGroup.getId(), tTestCard4.getCardId(),
		 * voto); fail(); } catch (Exception e){ e.printStackTrace(); } try {
		 * voto.setValue(-2); grupoServ.putVote(putVoteGroup.getId(),
		 * tTestCard4.getCardId(), voto); fail(); } catch (Exception e){
		 * e.printStackTrace(); }
		 */
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

		packTestGroup = grupoServ.putGroup(packTestGroup);

		grupoServ.putDestination(packTestGroup.getId(), "Paris");
		grupoServ.putDestination(packTestGroup.getId(), "Roma");
		
		packTestGroup = grupoServ.getGroup(packTestGroup.getId());
		
		
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
		
		grupoServ.deleteGroup(packTestGroup.getId());
	}
	
	@Test
	public void testCalculatePackPercentage() throws Exception {
		double res1, res2, res3 = 0;
		Group percentTestGroup = new Group();
		percentTestGroup.setName("percentTestGroupName");
		percentTestGroup.setDescription("grupo para testeo del calculo del porcentaje de los packs");
		percentTestGroup.setUsers(users);
		
		percentTestGroup = grupoServ.putGroup(percentTestGroup);
		
		grupoServ.putDestination(percentTestGroup.getId(), "Paris");
		grupoServ.putDestination(percentTestGroup.getId(), "Roma");
		
		percentTestGroup = grupoServ.getGroup(percentTestGroup.getId());
		
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
	
	@Test
	public void testFinalProposition() throws Exception {
		Group grupo = new Group();
		PlaceToSleepCard a = new PlaceToSleepCard();
		TransportCard b = new TransportCard();
		PlaceToSleepCard c = new PlaceToSleepCard();
		TransportCard d = new TransportCard();
		grupo.setName("putFinalProposition");
		grupo.setUsers(users);
		
		finalgroup = grupoServ.putGroup(grupo);
		
		grupoServ.putDestination(grupo.getId(), "Paris");
		grupoServ.putDestination(grupo.getId(), "Roma");
		
		grupo = grupoServ.getGroup(grupo.getId());
		
		grupo.getPlaceToSleepCards().add(ptsTestCard);
		grupo.getPlaceToSleepCards().add(ptsTestCard2);
		grupo.setPlaceToSleepCards(grupo.getPlaceToSleepCards());
		grupo.getTransportCards().add(tTestCard);
		grupo.getTransportCards().add(tTestCard2);
		grupo.setTransportCards(grupo.getTransportCards());
		
		finalgroup = grupoServ.putGroup(grupo);
		assertTrue(tTestCard.getFinalProposition() == false);
		assertTrue(ptsTestCard.getFinalProposition() == false);
		try {
			grupo = grupoServ.finalProposition(finalgroup.getId(), tTestCard.getCardId(), ptsTestCard.getCardId());
			a = (PlaceToSleepCard) grupoServ.cardExistOnArray(ptsTestCard.getCardId(), grupo.getPlaceToSleepCards());
			b = (TransportCard) grupoServ.cardExistOnArray(tTestCard.getCardId(), grupo.getTransportCards());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(b.getFinalProposition() == true);
		assertTrue(a.getFinalProposition() == true);
		
		assertTrue(tTestCard2.getFinalProposition() == false);
		assertTrue(ptsTestCard2.getFinalProposition() == false);
		try {
			grupo = grupoServ.finalProposition(finalgroup.getId(), tTestCard2.getCardId(), ptsTestCard2.getCardId());
			c = (PlaceToSleepCard) grupoServ.cardExistOnArray(ptsTestCard2.getCardId(), grupo.getPlaceToSleepCards());
			d = (TransportCard) grupoServ.cardExistOnArray(tTestCard2.getCardId(), grupo.getTransportCards());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//lo cojemos otra vez porque el grupo cambia
		a = (PlaceToSleepCard) grupoServ.cardExistOnArray(ptsTestCard.getCardId(), grupo.getPlaceToSleepCards());
		b = (TransportCard) grupoServ.cardExistOnArray(tTestCard.getCardId(), grupo.getTransportCards());
		
		//para comprobar que han cambiado la propuesta
		assertTrue(b.getFinalProposition() == false);
		assertTrue(a.getFinalProposition() == false);
		assertTrue(d.getFinalProposition() == true);
		assertTrue(c.getFinalProposition() == true);
		
		//ahora lo eliminamos con la funcion "deleteFinalProposition"
		grupo = grupoServ.deleteFinalProposition(grupo.getId(), d.getCardId(), c.getCardId());
		
		//lo cojemos otra vez porque el grupo cambia
		c = (PlaceToSleepCard) grupoServ.cardExistOnArray(c.getCardId(), grupo.getPlaceToSleepCards());
		d = (TransportCard) grupoServ.cardExistOnArray(d.getCardId(), grupo.getTransportCards());

		//para comprobar que se ha borrado la proposición anterior
		assertTrue(d.getFinalProposition() == false);
		assertTrue(c.getFinalProposition() == false);
	}
	
	@Test
	public void testVoteFinalProposition() throws Exception {
		testVoteFinal = grupoServ.putGroup(testVoteFinal);
		
		assertTrue(testVoteFinal.getPositiveVotes().isEmpty());
		assertTrue(testVoteFinal.getNegativeVotes().isEmpty());
		
		//groupId no existe
		try {
			testVoteFinal = grupoServ.putVoteFinalProposition("ddf1dg5fdf",
					usuario.getId(), true);
			fail();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		//usuario no existe
		try {
			testVoteFinal = grupoServ.putVoteFinalProposition(testVoteFinal.getId(),
					"25g15f1ht5h", true);
			fail();
		} catch (Exception e) {
			//e.printStackTrace();
		}		
		
		//a?ade a array positivos
		try {
			testVoteFinal = grupoServ.putVoteFinalProposition(testVoteFinal.getId(),
					usuario.getId(), true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(testVoteFinal.getPositiveVotes().get(0).equals(usuario.getId()));
		assertTrue(testVoteFinal.getNegativeVotes().isEmpty());

		//anade a array negativos y borra el positivo
		try {
			testVoteFinal = grupoServ.putVoteFinalProposition(testVoteFinal.getId(),
					usuario.getId(), false);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(testVoteFinal.getNegativeVotes().get(0).equals(usuario.getId()));
		assertTrue(testVoteFinal.getPositiveVotes().isEmpty());
	}
	
	
	@After
	public void tearDown() throws Exception {
		try {
			if ((resultGroup != null) && (resultGroup.getId() != null)) {
				grupoServ.deleteGroup(resultGroup.getId());
			}
		} catch (Exception e) {
			fail();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {

		try {
			userService.deleteUser(usuario.getId());

			grupoServ.deleteGroup(cardTestGroupWrInputs.getId());
			grupoServ.deleteGroup(cardTestGroup.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}