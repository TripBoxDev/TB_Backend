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
import com.tripbox.elements.Group;
import com.tripbox.elements.OtherCard;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.User;
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
		try {
			this.getGroup(groupId);
		} catch (Exception e) {
			throw new InvalidIdsException("El grupo con el ID, " + groupId
					+ ", no exsiste");
		}

		try {
			userService.getUser(userId);
		} catch (Exception e) {
			throw new InvalidIdsException("El usuario con el ID, " + userId
					+ ", no exsiste");
		}

		// eliminamos el user de la lista de users del grupo
		Group group = this.getGroup(groupId);
		ArrayList<String> groupUsers = group.getUsers();

		try {
			groupUsers.remove(userId);
		} catch (Exception e) {
			throw new UserNotExistOnGroup("El usuario con ID: " + userId
					+ "no existe en el grupo");
		}

		group.setUsers(groupUsers);
		// eliminamos el grup de la lista de grupos del usuario
		User user = userService.getUser(userId);
		ArrayList<String> userGroups = user.getGroups();
		userGroups.remove(groupId);
		user.setGroups(userGroups);

		// actualizamos la bbdd
		if (group.getUsers().isEmpty()) {
			this.deleteGroup(groupId);
		} else {
			this.putGroup(group);
		}

		userService.putUser(user);
	}

	public void putDestination(String groupId, String newDestination)
			throws Exception {

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
			group.getDestinations().add(newDestination);
			this.putGroup(group);
		}
	}

	public void deleteDestination(String groupId, String destinationToDelete)
			throws Exception {
		Group group;
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}
		if (group.getDestinations().contains(destinationToDelete)) {
			group.getDestinations().remove(destinationToDelete);

			ArrayList<TransportCard> transCards = group.getTransportCards();
			ArrayList<TransportCard> transCardsToDelete = new ArrayList<TransportCard>();
			for (TransportCard card : transCards) {
				if (card.getDestination().equalsIgnoreCase(destinationToDelete)) {

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
				if (card.getDestination().equalsIgnoreCase(destinationToDelete)) {
					placetoCardsToDelete.add(card);
				}
			}
			for (PlaceToSleepCard cardToDelete : placetoCardsToDelete) {
				group.getPlaceToSleepCards().remove(cardToDelete);
			}

			ArrayList<OtherCard> otherCards = group.getOtherCards();
			ArrayList<OtherCard> otherCardsToDelete = new ArrayList<OtherCard>();
			for (OtherCard card : otherCards) {
				if (card.getDestination().equalsIgnoreCase(destinationToDelete)) {
					otherCardsToDelete.add(card);
				}
			}
			for (OtherCard cardToDelete : otherCardsToDelete) {
				group.getOtherCards().remove(cardToDelete);
			}

			this.putGroup(group);
		} else {
			throw new ElementNotFoundServiceException("Destination "
					+ destinationToDelete + " doesn't exist");

		}

	}

	public Card putCard(String groupId, Card card) throws Exception {
		Group group;
		UserServiceImpl userService = new UserServiceImpl();
		try {
			group = this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("Group " + groupId
					+ " not found");
		}

		// comprobem que el desti existeixi
		if (card.getDestination() == null
				|| !group.getDestinations().contains(card.getDestination()))
			throw new DestinationDoesntExistException();

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

				// no es pot afegir elements manualment a childCardsId
				auxTransCard.setChildCardsId(new ArrayList<String>());

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
					PlaceToSleepCard auxPlaceCard = (PlaceToSleepCard) foundCard;
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
					group.getPlaceToSleepCards().remove(auxPlaceCard);

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
			if (foundCard == null)
				throw new InvalidIdsException("La Card con el ID, "
						+ card.getCardId() + " de tipo " + card.getCardType()
						+ ", no exsiste en ");
		}
		this.putGroup(group);
		return card;

	}

	public Card cardExistOnArray(String cardId, ArrayList cardsArray) {
		boolean cardExist = false;
		Card foundCard = null;
		Iterator<Card> it = cardsArray.iterator();
		while (it.hasNext() && !cardExist) {
			Card transpCard = it.next();
			if (transpCard.getCardId().equalsIgnoreCase(cardId)) {
				cardExist = true;
				foundCard = transpCard;
			}
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

	public void saveGroupImage(String groupId, File fileImage,
			String uploadedFileLocation) throws Exception {
		InputStream is = new FileInputStream (fileImage);
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
			if (group.getImage() == false) {
				group.setImage();
				putGroup(group);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
