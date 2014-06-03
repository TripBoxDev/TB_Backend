package com.tripbox.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.MongoDB;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Card;
import com.tripbox.elements.Destination;
import com.tripbox.elements.Group;
import com.tripbox.elements.OtherCard;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.User;
import com.tripbox.elements.Vote;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.CardTypeException;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.DestinationDoesntExistException;
import com.tripbox.services.exceptions.ElementNotFoundServiceException;
import com.tripbox.services.exceptions.IdAlreadyExistException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImpl implements GroupService {

	// Querys bbdd = Mock.getInstance();
	// Querys bbdd=new MongoDB();
	IdGenerator idGen = IdGenerator.getInstance();

	MongoDB mongo;

	public GroupServiceImpl() {

	}

	public Group getGroup(String id) throws Exception {
		mongo = MongoDB.getInstance();
		try {
			return mongo.getGroup(id);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("El grup no s'ha trobat.");
		}
	}

	public Group putGroup(Group group) throws Exception {

		// si el Group es nuevo le asignamos una id
		if (group.getId() == null) {
			group = putNewGroup(group);
			
		} else {

			try {
				// comprobamos que el id existe
				this.getGroup(group.getId());

				// modificamos el group a la bbdd
				// bbdd.putGroup(group);
				mongo = MongoDB.getInstance();
				mongo.putGroup(group);

			} catch (Exception e) {
				throw new InvalidIdsException("El Group con el ID, "
						+ group.getId() + ", no exsiste");
			}
		}
		// devolvemos el elemento Group completo
		return group;
	}

	private Group putNewGroup(Group group) throws Exception {
		String newId = idGen.generateId();
		group.setId(newId);
		while (true) {
			try {
				// comprovamos si el id existe
				try {
					this.getGroup(newId);
					// generamos nueva id
					throw new IdAlreadyExistException();
				} catch (IdAlreadyExistException ex) {
					throw new IdAlreadyExistException();
				} catch (Exception e) {
					// insertamos el Group a la bbdd
					// bbdd.putGroup(group);
					mongo = MongoDB.getInstance();
					mongo.putGroup(group);
					break;
				}

			} catch (IdAlreadyExistException ex) {
				// si el id ya existe probamos con otro id
				newId = idGen.generateId();
				group.setId(newId);
				continue;
			}
		}
		return group;
	}

	public void deleteGroup(String id) throws Exception {
		mongo = MongoDB.getInstance();
		try {
			mongo.deleteGroup(id);
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void deleteUserToGroup(String groupId, String userId)
			throws Exception {
		UserService userService = new UserServiceImpl();
		User user;
		Group group;
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("El grupo con el ID, "
					+ groupId + ", no exsiste");
		}

		try {
			user = userService.getUser(userId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("El usuario con el ID, "
					+ userId + ", no exsiste");
		}

		// eliminamos el user de la lista de users del grupo
		group = this.getGroup(groupId);
		ArrayList<String> groupUsers = group.getUsers();

		try {
			groupUsers.remove(userId);
		} catch (Exception e) {
			throw new UserNotExistOnGroup("El usuario con ID: " + userId
					+ "no existe en el grupo");
		}
		group.setUsers(groupUsers);

		// eliminamos el grup de la lista de grupos del usuario
		user = userService.getUser(userId);
		ArrayList<String> userGroups = user.getGroups();
		userGroups.remove(groupId);
		user.setGroups(userGroups);

		// actualizamos la bbdd
		if (group.getUsers().isEmpty()) {
			this.deleteGroup(groupId);
		} else {
			this.putGroup(group);
		}

		// eliminamos los votos del user en las cards del grupo
		ArrayList<String> cardsVoted = user.getCardsVoted();
		ArrayList<String> cardsVoteDelete = new ArrayList<String>(); // guardamos
		// las
		// cards
		// encontradas
		// en
		// este
		// grupo
		for (String cardVoted : cardsVoted) {
			// buscamos la card dentro de los tres array: transportCards,
			// placeToSleepCards y otherCards
			Card cardFound = null;
			cardFound = cardExistOnArray(cardVoted, group.getTransportCards());
			if (cardFound == null) {
				cardFound = cardExistOnArray(cardVoted,
						group.getPlaceToSleepCards());
				if (cardFound == null) {
					cardFound = cardExistOnArray(cardVoted,
							group.getOtherCards());
				}
			}

			if (cardFound != null) {// si encontramos la card guardamos su id y
			// buscamos y eliminamos el voto del user
				cardsVoteDelete.add(cardVoted);

				boolean voteFound = false;
				Vote foundVote = null;
				Iterator<Vote> it = cardFound.getVotes().iterator();
				while (it.hasNext() && !voteFound) { // buscamos el voto
					Vote vote = it.next();
					if (vote.getUserId().equalsIgnoreCase(userId)) {
						voteFound = true;
						foundVote = vote;
					}
				}

				cardFound.getVotes().remove(foundVote);// eliminamos el voto
				this.definePack(group);
				cardFound.calculateAverage();
				this.putCard(groupId, cardFound);
			}
		}

		for (String deleteVoteOnUser : cardsVoteDelete) {
			user.getCardsVoted().remove(deleteVoteOnUser);
		}
		// Como las votaciones han cambiado redefinimos el mejor pack de cada
		// destino.
		this.definePack(group);
		userService.putUser(user);
	}

	public Destination putDestination(String groupId, String newDestination)
			throws Exception {
		Destination destiny = new Destination();
		Group group;
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}
		if (group.getDestinations().contains(newDestination)) {
			// destination already exist. do nothing
			// throw new DestinationAlreadyExistException();
		} else {
			String newId;
			do {
				newId = idGen.generateId();
			} while (group.getDestinations().contains(newId));
			destiny.setName(newDestination);
			destiny.setId(newId);
			group.getDestinations().add(destiny);
			this.putGroup(group);
		}
		return destiny;
	}

	public void deleteDestination(String groupId, String idDestination)
			throws Exception {
		Group group;
		Destination destiny = new Destination();

		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}

		Boolean foundId = false;
		for (Destination dest : group.getDestinations()) {
			if (dest.getId().equals(idDestination)) {
				foundId = true;
				destiny = dest;
			}
		}

		if (foundId == true) {
			group.getDestinations().remove(destiny);

			ArrayList<TransportCard> transCards = group.getTransportCards();
			ArrayList<TransportCard> transCardsToDelete = new ArrayList<TransportCard>();
			for (TransportCard card : transCards) {
				if (card.getDestination().equalsIgnoreCase(destiny.getName())) {

					transCardsToDelete.add(card);
				}
			}
			for (TransportCard cardToDelete : transCardsToDelete) {
				group.getTransportCards().remove(cardToDelete);
			}

			ArrayList<PlaceToSleepCard> placetoCards = group
					.getPlaceToSleepCards();
			ArrayList<PlaceToSleepCard> placetoCardsToDelete = new ArrayList<PlaceToSleepCard>();
			for (PlaceToSleepCard card : placetoCards) {
				if (card.getDestination().equalsIgnoreCase(destiny.getName())) {
					placetoCardsToDelete.add(card);
				}
			}
			for (PlaceToSleepCard cardToDelete : placetoCardsToDelete) {
				group.getPlaceToSleepCards().remove(cardToDelete);
			}

			ArrayList<OtherCard> otherCards = group.getOtherCards();
			ArrayList<OtherCard> otherCardsToDelete = new ArrayList<OtherCard>();
			for (OtherCard card : otherCards) {
				if (card.getDestination().equalsIgnoreCase(destiny.getName())) {
					otherCardsToDelete.add(card);
				}
			}
			for (OtherCard cardToDelete : otherCardsToDelete) {
				group.getOtherCards().remove(cardToDelete);
			}

			this.putGroup(group);
		} else {
			throw new ElementNotFoundServiceException("Destination with ID "
					+ idDestination + " doesn't exist");

		}

	}

	public Card putCard(String groupId, Card card) throws Exception {
		UserService userService = new UserServiceImpl();
		Group group;

		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}
		// comprobem que el desti existeixi
		if (card.getDestination() == null) {
			throw new DestinationDoesntExistException();
		} else {
			boolean foundDest = false;
			for (Destination dest : group.getDestinations()) {
				if (dest.getName().equalsIgnoreCase(card.getDestination())) {
					foundDest = true;
				}
			}
			if (foundDest == false) {
				throw new DestinationDoesntExistException();
			}
		}

		// comprobem que l'usuari que ha creat la card existeix
		try {
			userService.getUser(card.getUserIdCreator());
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("User "
					+ card.getUserIdCreator() + " not found");
		}

		// si o te id significa que es una nova card
		if (card.getCardId() == null) {
			card.setCardId(idGen.generateId());
			card.setCreationDate((new Date()).getTime());

			switch (card.getCardType()) {
			case "transport":
				TransportCard auxTransCard = (TransportCard) card;
				group.getTransportCards().add(auxTransCard);
				break;

			case "placeToSleep":
				PlaceToSleepCard auxPlaceCard = (PlaceToSleepCard) card;
				// comprobem que els parentCards existeixin
				for (String parentId : auxPlaceCard.getParentCardIds()) {
					TransportCard parentCard = (TransportCard) cardExistOnArray(
							parentId, group.getTransportCards());
					if (parentCard == null) {
						throw new ElementNotFoundServiceException("ParentCard "
								+ parentId + " not found");
					} else {
						// heretem les dates de init i final del parent
						auxPlaceCard.setInitDate(parentCard.getInitDate());
						auxPlaceCard.setFinalDate(parentCard.getFinalDate());

						// afegim la id d'aquesta card al childCardsId de la
						// parent card
						parentCard.getChildCardsId().add(
								auxPlaceCard.getCardId());
						this.putCard(groupId, parentCard);
					}
				}

				group.getPlaceToSleepCards().add(auxPlaceCard);
				break;

			case "other":
				OtherCard auxOtherCard = (OtherCard) card;
				group.getOtherCards().add(auxOtherCard);
				break;

			default:
				throw new CardTypeException();
			}
		} else { // si te ID el primer que fem es comprobar que aquesta card
		// existeixi de veritat
			Card foundCard = null;
			switch (card.getCardType()) {
			case "transport":

				foundCard = cardExistOnArray(card.getCardId(),
						group.getTransportCards());
				if (foundCard != null) {
					TransportCard auxTransCard = (TransportCard) foundCard;
					group.getTransportCards().remove(auxTransCard);

					auxTransCard = (TransportCard) card;
					group.getTransportCards().add(auxTransCard);
				}
				break;

			case "placeToSleep":
				foundCard = cardExistOnArray(card.getCardId(),
						group.getPlaceToSleepCards());
				if (foundCard != null) {

					PlaceToSleepCard auxPlaceCard = (PlaceToSleepCard) card;
					PlaceToSleepCard oldPlaceCard = (PlaceToSleepCard) foundCard;

					// comprobem que els parentCards existeixin
					for (String parentId : auxPlaceCard.getParentCardIds()) {
						TransportCard parentCard = (TransportCard) cardExistOnArray(
								parentId, group.getTransportCards());
						if (parentCard == null) {
							throw new ElementNotFoundServiceException(
									"ParentCard " + parentId + " not found");
						} else {
							// afegim la id d'aquesta card al childCardsId de la
							// parent card
							if (!parentCard.getChildCardsId().contains(
									auxPlaceCard.getCardId())) {
								parentCard.getChildCardsId().add(
										auxPlaceCard.getCardId());
								this.putCard(groupId, parentCard);
							}
						}
					}

					group.getPlaceToSleepCards().remove(oldPlaceCard);

					auxPlaceCard = (PlaceToSleepCard) card;
					group.getPlaceToSleepCards().add(auxPlaceCard);

				}
				break;

			case "other":
				foundCard = cardExistOnArray(card.getCardId(),
						group.getOtherCards());
				if (foundCard != null) {
					OtherCard auxOtherCard = (OtherCard) foundCard;
					group.getOtherCards().remove(auxOtherCard);

					auxOtherCard = (OtherCard) card;
					group.getOtherCards().add(auxOtherCard);
				}
				break;

			default:
				throw new CardTypeException();

			}
			if (foundCard == null) {
				throw new InvalidIdsException("La Card con el ID, "
						+ card.getCardId() + " de tipo " + card.getCardType()
						+ ", no exsiste en ");
			}

		}

		this.putGroup(group);
		return card;
	}

	public Card cardExistOnArray(String cardId, ArrayList cardsArray) {
		boolean cardExist = false;
		Card foundCard = null;
		Iterator<Card> it = cardsArray.iterator();
		while (it.hasNext() && !cardExist) {
			Card card = it.next();
			if (card.getCardId().equalsIgnoreCase(cardId)) {
				cardExist = true;
				foundCard = card;
			}
		}

		return foundCard;

	}

	public Card getCard(String cardId, String type, Group grupo) {

		Card foundCard = null;
		boolean cardExist = false;

		switch (type) {
		case "transport":
			Iterator<TransportCard> it = grupo.getTransportCards().iterator();

			while (it.hasNext() && !cardExist) {
				TransportCard tc = it.next();
				if (tc.getCardId().equalsIgnoreCase(cardId)) {
					cardExist = true;
					foundCard = tc;
				}
			}
			break;
		case "placeToSleep":
			Iterator<PlaceToSleepCard> ptsIt = grupo.getPlaceToSleepCards()
					.iterator();
			while (ptsIt.hasNext() && !cardExist) {
				PlaceToSleepCard pts = ptsIt.next();
				if (pts.getCardId().equalsIgnoreCase(cardId)) {
					cardExist = true;
					foundCard = pts;
				}
			}
			break;
		case "other":
			Iterator<OtherCard> itO = grupo.getOtherCards().iterator();

			while (itO.hasNext() && !cardExist) {
				OtherCard oCard = itO.next();
				if (oCard.getCardId().equalsIgnoreCase(cardId)) {
					cardExist = true;
					foundCard = oCard;
				}
			}
			break;
		}
		return foundCard;
	}

	public void deleteCard(String groupId, String cardId) throws Exception {
		Group group;
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}

		Card foundCard = null;
		foundCard = cardExistOnArray(cardId, group.getTransportCards());
		if (foundCard != null) {
			group.getTransportCards().remove(foundCard);

			TransportCard transCard = (TransportCard) foundCard;

			// actualizamos la lista de parentsId de las childCards
			for (String childCard : transCard.getChildCardsId()) {
				PlaceToSleepCard placeCard = (PlaceToSleepCard) cardExistOnArray(
						childCard, group.getPlaceToSleepCards());
				group.getPlaceToSleepCards().remove(placeCard);
				placeCard.getParentCardIds().remove(transCard.getCardId());
				group.getPlaceToSleepCards().add(placeCard);
			}
		} else {
			foundCard = cardExistOnArray(cardId, group.getPlaceToSleepCards());
			if (foundCard != null) {
				group.getPlaceToSleepCards().remove(foundCard);
				PlaceToSleepCard placeCard = (PlaceToSleepCard) foundCard;

				// actualizamos la lista de childCards de las parentCards
				for (String parentCard : placeCard.getParentCardIds()) {
					TransportCard transportCard = (TransportCard) cardExistOnArray(
							parentCard, group.getTransportCards());
					group.getTransportCards().remove(transportCard);
					transportCard.getChildCardsId().remove(
							placeCard.getCardId());
					group.getTransportCards().add(transportCard);
				}
			} else {
				foundCard = cardExistOnArray(cardId, group.getOtherCards());
				if (foundCard != null) {
					group.getOtherCards().remove(foundCard);
				} else {
					throw new ElementNotFoundServiceException("Card " + cardId
							+ " not found");
				}
			}
		}
		this.putGroup(group);

	}

	public Card putVote(String groupId, String cardId, Vote vote)
			throws Exception {
		UserService userService = new UserServiceImpl();
		Group group;
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}

		Card foundCard = null;
		foundCard = cardExistOnArray(cardId, group.getTransportCards());
		if (foundCard == null) {
			foundCard = cardExistOnArray(cardId, group.getPlaceToSleepCards());
			if (foundCard == null) {
				foundCard = cardExistOnArray(cardId, group.getOtherCards());
			}
		}

		if (foundCard != null) {
			boolean found = false;
			Iterator<Vote> it = foundCard.getVotes().iterator();
			while (it.hasNext() && !found) {
				Vote cardVote = it.next();
				if (cardVote.getUserId().equalsIgnoreCase(vote.getUserId())) {
					found = true;
					// si el usuario ya ha votado la carta eliminamos el voto
					// antiguo
					foundCard.getVotes().remove(cardVote);
					// y guardamos el nuevo voto
					foundCard.getVotes().add(vote);
				}
			}
			if (!found) {
				foundCard.getVotes().add(vote);
				// registramos el voto en el user
				User user = userService.getUser(vote.getUserId());
				user.getCardsVoted().add(cardId);
				userService.putUser(user);
			}
			// recalculamos el valor medio de votos
			foundCard.calculateAverage();

			this.putCard(groupId, foundCard);
			this.definePack(group);
			return foundCard;

		} else {
			throw new ElementNotFoundServiceException("Card " + cardId
					+ " not found");
		}

	}

	public void saveGroupImage(String groupId, File fileImage,
			String uploadedFileLocation) throws Exception {
		InputStream is = new FileInputStream(fileImage);
		Group group;
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			group = getGroup(groupId);

			if (!group.getFlagImage()) {
				group.setFlagImage(true);
			}
			this.putGroup(group);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	public void saveCardImage(String cardId, String groupId, String type,  File fileImage,
			String uploadedFileLocation) throws Exception {
		InputStream is = new FileInputStream(fileImage);
		Card card;
		Group group;
		
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			group = getGroup(groupId);
			card = getCard(cardId, type, group);

			if (!card.getFlagImage()) {
				card.setFlagImage(true);
			}
			this.putCard(groupId, card);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void definePack(Group group) throws Exception {
		TransportCard bestTempTransportCard = null;
		PlaceToSleepCard bestPlaceToSleepCard = null;
		double bestTempValoration = 0;
		String destino = null;
		int i = 0;
		double ponderation;

		// miramos las cartas segun los destinos que hay
		for (Destination destination : group.getDestinations()) {

			bestTempValoration = 0;
			// miramos si la card de alojamiento es del destino que estamos
			// buscando

			if (!group.getPlaceToSleepCards().isEmpty()) {

				for (PlaceToSleepCard ptsCard : group.getPlaceToSleepCards()) {
					if (ptsCard.getDestination().equals(destination.getName())) {
						ptsCard.setBestPack(false); // aprovechamos el for
														// para reiniciar los
														// packs

						// en transporte ya no miramos que sea del mismo destino
						// porque esta carta esta linkada al alojamiento
						if (!ptsCard.getParentCardIds().isEmpty()) {
							
							for (String tCardId : ptsCard.getParentCardIds()) {
								TransportCard tcCard = (TransportCard) getCard(
										tCardId, "transport", group);
								tcCard.setBestPack(false); // aprovechamos el
																// for para
																// reiniciar los
																// packs

								ponderation = calculatePackPercentage(tcCard,
										ptsCard, group);
								if (ponderation > bestTempValoration) {
									bestTempValoration = ponderation;
									bestTempTransportCard = tcCard;
									bestPlaceToSleepCard = ptsCard;
									destino = destination.getName();
								}
							}
						}
					}
				}
				// activamos el flag de best pack para las cards de transporte y
				// de alojamiento de cada destino
				if ((bestTempTransportCard != null)
						&& (bestPlaceToSleepCard != null)) {
					bestTempTransportCard.setBestPack(true);
					bestPlaceToSleepCard.setBestPack(true);
					i=0;
					for (Destination destiny : group.getDestinations()) {
						
						if (destiny.getName().equalsIgnoreCase(destino)){
							group.getDestinations().get(i).setPercentage(bestTempValoration);
							break;
						}
						
						i++;
					}
				}
				this.putGroup(group);
			}
		}
	}

	public double calculatePackPercentage(TransportCard tcCard,
			PlaceToSleepCard ptsCard, Group group) throws Exception {
		int members, numOtherCards = 0;
		double avg, avg2, resultTrans, resultAloj, otherResult = 0;
		double resultFinal = 0;
		members = group.getUsers().size();

		// Votacion transporte
		avg = tcCard.getAverage() * 0.7 * 2;
		avg2 = (tcCard.getVotes().size() / members) * 0.3 * 10;
		resultTrans = (avg + avg2) * 0.4;

		// Votacion alojamiento
		avg = ptsCard.getAverage() * 0.7 * 2;
		avg2 = (ptsCard.getVotes().size() / members) * 0.3 * 10;
		resultAloj = (avg + avg2) * 0.4;

		// Votacion other cards
		if (!group.getOtherCards().isEmpty()) {
			for (OtherCard otherCard : group.getOtherCards()) {
				if (otherCard.getDestination().equals(tcCard.getDestination())) {
					numOtherCards++;
					avg = otherCard.getAverage() * 0.7 * 2;
					avg2 = (otherCard.getVotes().size() / members) * 0.3 * 10;
					otherResult = ((avg + avg2) * 0.2) + otherResult;
				}
			}
			resultFinal = (otherResult / numOtherCards);
		}

		// calculo final
		resultFinal = resultTrans + resultAloj + resultFinal;
		resultFinal = 10 * resultFinal;
		resultFinal = (double)Math.round(resultFinal*100)/100;
		return resultFinal;
	}

	public Group finalProposition(String groupId, String idTransporte,
			String idAlojamiento) throws Exception {
		Group group;
		boolean found = false;

		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}

		for (TransportCard tranCard : group.getTransportCards()) {
			
			// esto reinicia a false la propuesta, por si se cambia de propuesta
			tranCard.setFinalProposition(false);
			if (tranCard.getCardId().equals(idTransporte)) {
				found = true;
				tranCard.setFinalProposition(found);
			}

		}

		if (found == false) {
			throw new ElementNotFoundServiceException("Transport card "
					+ idTransporte + " not found");
		}

		found = false;
		for (PlaceToSleepCard alojCard : group.getPlaceToSleepCards()) {
			// esto reinicia a false la propuesta, por si se cambia de propuesta
			alojCard.setFinalProposition(false);
			if (alojCard.getCardId().equals(idAlojamiento)) {
				found = true;
				alojCard.setFinalProposition(found);
			}
		}
		for (Card card : group.getPlaceToSleepCards()){
			card.setFinalProposition(false);
			if (card.getCardId().equals(idAlojamiento)) {
				found = true;
				card.setFinalProposition(found);
			}
			
		}
		if (found == false) {
			throw new ElementNotFoundServiceException("Place to sleep "
					+ idAlojamiento + " not found");
		}
		
		//reiniciamos los arrays de votos
		group.getNegativeVotes().clear();
		group.getPositiveVotes().clear();
		
		this.putGroup(group);
		return group;
	}

	
	public Group deleteFinalProposition(String groupId, String transportId, String placeToSleepId) throws Exception{
		Group group = this.getGroup(groupId);
		boolean found = false;
		
		for (Card card : group.getTransportCards()){
			if (card.getCardId().equals(transportId)){
				found = true;
				card.setFinalProposition(false);
			}
		}
		
		if (found == false) {
			System.out.println("je");
			throw new ElementNotFoundServiceException("Transport card "
					+ transportId + " not found");
		}
		
		found = false;
		for (Card card : group.getPlaceToSleepCards()){
			if (card.getCardId().equals(placeToSleepId)){
				found = true;
				card.setFinalProposition(false);
			}
		}
		
		if (found == false) {
			System.out.println("ja");
			throw new ElementNotFoundServiceException("Place to sleep "
					+ placeToSleepId + " not found");
		}
		
		return this.putGroup(group);
	}
	
	
	public Group putVoteFinalProposition(String groupId, String userId,
			boolean vote) throws Exception {
		Group group;
		
		//comprobamos que exista el grupo
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}
		
		//comprobamos que exista el usuario
		if (!group.getUsers().contains(userId)){
			throw new ElementNotFoundServiceException("User " + userId
					+ " not found");
		}

		//comprobamos que no existe el usuario en el array de votos negativos
		//si existe lo borramos del array
		if (group.getNegativeVotes().contains(userId)){
			group.getNegativeVotes().remove(userId);
		}else{
			if (group.getPositiveVotes().contains(userId)){
				group.getPositiveVotes().remove(userId);
			} 
		}
		
		if (vote == true){
			group.getPositiveVotes().add(userId);
		}else{
			group.getNegativeVotes().add(userId);
		}
		
		this.putGroup(group);
		return group;
	}

}
