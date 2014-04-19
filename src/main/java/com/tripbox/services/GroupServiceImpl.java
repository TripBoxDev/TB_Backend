package com.tripbox.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.tripbox.api.exceptions.ElementNotFoundException;
import com.tripbox.bbdd.Mock;
import com.tripbox.bbdd.interfaces.Querys;
import com.tripbox.elements.Card;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;
import com.tripbox.others.IdGenerator;
import com.tripbox.services.exceptions.CardTypeException;
import com.tripbox.services.exceptions.DestinationAlreadyExistException;
import com.tripbox.services.exceptions.IdAlreadyExistException;
import com.tripbox.services.exceptions.InvalidIdsException;
import com.tripbox.services.exceptions.UserNotExistOnGroup;
import com.tripbox.services.interfaces.GroupService;
import com.tripbox.services.interfaces.UserService;

public class GroupServiceImpl implements GroupService {

	Querys bbdd = Mock.getInstance();
	IdGenerator idGen=IdGenerator.getInstance();

	public GroupServiceImpl() {
	}

	public Group getGroup(String id) throws Exception {
		try {
			return bbdd.getGroup(id);
		} catch (Exception e) {
			throw new ElementNotFoundException("El grup no s'ha trobat.");
		}
	}

	public Group putGroup(Group group) throws Exception {
		// si el Group es nuevo le asignamos una id
		if (group.getId() == null) {

			group=putNewGroup(group);
		}else{

			try {
				//comprobamos que el id existe
				bbdd.getGroup(group.getId());
	
				//modificamos el group a la bbdd
				bbdd.putGroup(group);
				
			} catch (Exception e) {
				throw new InvalidIdsException("El Group con el ID, "+group.getId()+", no exsiste");
			}
		}
		//devolvemos el elemento Group completo
		return group;

	}

	private Group putNewGroup(Group group) throws Exception {
		String newId = idGen.generateId();
		group.setId(newId);
		while(true){
			try{
				//comprovamos si el id existe
				try{
					this.getGroup(newId);
					//generamos nueva id
					throw new IdAlreadyExistException();
				}catch (IdAlreadyExistException ex){
					throw new IdAlreadyExistException();
				}catch (Exception e){
					//insertamos el Group a la bbdd
					bbdd.putGroup(group);

					break;
				}



			} catch(IdAlreadyExistException ex){
				//si el id ya existe probamos con otro id
				newId = idGen.generateId();
				group.setId(newId);
				continue;
			}
		}
		return group;
	}

	public void deleteGroup(String id) throws Exception {
		try {
			bbdd.deleteGroup(id);
		} catch (Exception e) {
			throw new Exception();
		}

	}

	public void deleteUserToGroup(String groupId, String userId) throws Exception {
		UserService userService = new UserServiceImpl();
		try {
			this.getGroup(groupId);
		} catch (Exception e) {
			throw new InvalidIdsException("El grupo con el ID, "+groupId+", no exsiste");
		}

		try {
			userService.getUser(userId);
		} catch (Exception e) {
			throw new InvalidIdsException("El usuario con el ID, "+userId+", no exsiste");
		}

		// eliminamos el user de la lista de users del grupo
		Group group = this.getGroup(groupId);
		ArrayList<String> groupUsers = group.getUsers();
		
		try {
			groupUsers.remove(userId);
		} catch (Exception e) {
			throw new UserNotExistOnGroup("El usuario con ID: " + userId + "no existe en el grupo");
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
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundException("Group "+groupId+" not found");
		}
		if(group.getDestinations().contains(newDestination)){
			//destination already exist
			throw new DestinationAlreadyExistException();
		}else{
			group.getDestinations().add(newDestination);
			this.putGroup(group);
		}
	}


	public void deleteDestination(String groupId, String destinationToDelete)
			throws Exception {
		Group group;
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundException("Group "+groupId+" not found");
		}
		if(group.getDestinations().contains(destinationToDelete)){
			group.getDestinations().remove(destinationToDelete);
			this.putGroup(group);
		}else{
			throw new ElementNotFoundException("Destination "+destinationToDelete+" doesn't exist");
			
		}
		
	}


	public Card putCard(String groupId, Card card) throws Exception {
		Group group;
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundException("Group "+groupId+" not found");
		}

		//si o te id significa que es una nova card
		if(card.getCardId()==null){
			card.setCardId(idGen.generateId());
			switch(card.getCardType()){
				case "transport":
						group.getTransportCards().add(card);
					break;
					
				case "placeToSleep":
						group.getPlaceToSleepCards().add(card);
					break;
					
				case "other":
						group.getOtherCards().add(card);
					break;
					
				default:
					throw new CardTypeException();
			}
		}else{ //si te ID el primer que fem es comprobar que aquesta card existeixi de veritat
			Card foundCard=null;
			switch(card.getCardType()){
				case "transport":
					
					foundCard=cardExistOnArray(card.getCardId(), group.getTransportCards());
					if(foundCard!=null){ 
						group.getTransportCards().remove(foundCard);
						group.getTransportCards().add(card);
					}
					break;
					
				case "placeToSleep":
					foundCard=cardExistOnArray(card.getCardId(), group.getPlaceToSleepCards());
					if(foundCard!=null){ 
						group.getPlaceToSleepCards().remove(foundCard);
						group.getPlaceToSleepCards().add(card);
					}
					break;
					
				case "other":
					foundCard=cardExistOnArray(card.getCardId(), group.getOtherCards());
					if(foundCard!=null){ 
						group.getOtherCards().remove(foundCard);
						group.getOtherCards().add(card);
					}
					break;
					
				default:
					throw new CardTypeException();
		
		
			}
			if(foundCard==null)
				throw new InvalidIdsException("La Card con el ID, "+card.getCardId()+", no exsiste");
		}
		this.putGroup(group);
		return card;
		
		
	}
	
	public Card cardExistOnArray(String cardId, ArrayList<Card> cardsArray){
		boolean cardExist=false;
		Card foundCard=null;
		Iterator<Card> it = cardsArray.iterator();
		while(it.hasNext()&&!cardExist){
			Card transpCard = it.next();
			if(transpCard.getCardId().equalsIgnoreCase(cardId)){
				cardExist=true;
				foundCard=transpCard;
			}
		}
		
		return foundCard;
		
	}
	


	public void deleteCard(String groupId, String cardId) throws Exception {
		Group group;
		try{
			
			group=this.getGroup(groupId);
			
		}catch(Exception e){
			throw new ElementNotFoundException("Group "+groupId+" not found");
		}

		Card foundCard=null;
		foundCard=cardExistOnArray(cardId, group.getTransportCards());
		if(foundCard!=null){
			group.getTransportCards().remove(foundCard);
		}else{
			foundCard=cardExistOnArray(cardId, group.getPlaceToSleepCards());
			if(foundCard!=null){
				group.getPlaceToSleepCards().remove(foundCard);
			}else{
				foundCard=cardExistOnArray(cardId, group.getOtherCards());
				if(foundCard!=null){
					group.getOtherCards().remove(foundCard);
				}else{
					throw new ElementNotFoundException("Card "+cardId+" not found");
				}
			}
		}
		this.putGroup(group);

	}

}