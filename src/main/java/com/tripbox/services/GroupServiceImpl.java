package com.tripbox.services;

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


	//Querys bbdd = Mock.getInstance();
	//Querys bbdd=new MongoDB();
	IdGenerator idGen=IdGenerator.getInstance();

	MongoDB mongo;
			



	public GroupServiceImpl() {
		
			
		
	}

	public Group getGroup(String id) throws Exception {
		mongo=MongoDB.getInstance();
		try {
			return mongo.getGroup(id);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("El grup no s'ha trobat.");
		}
	}

	public Group putGroup(Group group) throws Exception {

		// si el Group es nuevo le asignamos una id
		if (group.getId() == null) {

			group=putNewGroup(group);
		}else{

			try {
				//comprobamos que el id existe
				this.getGroup(group.getId());
	
				//modificamos el group a la bbdd
				//bbdd.putGroup(group);
				mongo=MongoDB.getInstance();
				mongo.putGroup(group);
				
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
					//bbdd.putGroup(group);
					mongo=MongoDB.getInstance();
					mongo.putGroup(group);
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
		mongo=MongoDB.getInstance();
		try {
			mongo.deleteGroup(id);
		} catch (Exception e) {
			throw new Exception();
		}

	}


	public void deleteUserToGroup(String groupId, String userId) throws Exception {
		UserService userService = new UserServiceImpl();
		User user;
		Group group;
		try {
			group=this.getGroup(groupId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("El grupo con el ID, "+groupId+", no exsiste");
		}

		try {
			user=userService.getUser(userId);
		} catch (Exception e) {
			throw new ElementNotFoundServiceException("El usuario con el ID, "+userId+", no exsiste");
		}

		// eliminamos el user de la lista de users del grupo
		group = this.getGroup(groupId);
		ArrayList<String> groupUsers = group.getUsers();
		
		try {
			groupUsers.remove(userId);
		} catch (Exception e) {
			throw new UserNotExistOnGroup("El usuario con ID: " + userId + "no existe en el grupo");
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


		
		
		
		
		//eliminamos los votos del user en las cards del grupo
		ArrayList<String> cardsVoted = user.getCardsVoted();
		ArrayList<String> cardsVoteDelete=new ArrayList<String>(); //guardamos las cards encontradas en este grupo
		for(String cardVoted:cardsVoted){ 
			//buscamos la card dentro de los tres array: transportCards, placeToSleepCards y otherCards
			Card cardFound=null;
			cardFound=cardExistOnArray(cardVoted, group.getTransportCards());
			if(cardFound==null){
				cardFound=cardExistOnArray(cardVoted, group.getPlaceToSleepCards());
				if(cardFound==null){
					cardFound=cardExistOnArray(cardVoted, group.getOtherCards());
				}
			}
			
			if(cardFound!=null){//si encontramos la card guardamos su id  y buscamos y eliminamos el voto del user
				cardsVoteDelete.add(cardVoted);
				
				boolean voteFound=false;
				Vote foundVote=null;
				Iterator<Vote> it = cardFound.getVotes().iterator();
				while(it.hasNext()&&!voteFound){ //buscamos el voto
					Vote vote = it.next();
					if(vote.getUserId().equalsIgnoreCase(userId)){
						voteFound=true;
						foundVote=vote;
					}
				}
				
				cardFound.getVotes().remove(foundVote);//eliminamos el voto
				cardFound.calculateAverage();
				this.putCard(groupId, cardFound);
			}
		}
		
		for(String deleteVoteOnUser: cardsVoteDelete){
			user.getCardsVoted().remove(deleteVoteOnUser);
		}
		userService.putUser(user);
	}


	public void putDestination(String groupId, String newDestination)
			throws Exception {
		
		Group group;
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundServiceException("Group "+groupId+" not found");
		}
		if(group.getDestinations().contains(newDestination)){
			//destination already exist. do nothing
			//throw new DestinationAlreadyExistException();
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
			throw new ElementNotFoundServiceException("Group "+groupId+" not found");
		}
		if(group.getDestinations().contains(destinationToDelete)){
			group.getDestinations().remove(destinationToDelete);
			
			ArrayList<TransportCard> transCards = group.getTransportCards();
			ArrayList<TransportCard> transCardsToDelete = new ArrayList<TransportCard>();
			for(TransportCard card:transCards){
				if(card.getDestination().equalsIgnoreCase(destinationToDelete)){
					
					transCardsToDelete.add(card);
				}
			}
			for(TransportCard cardToDelete:transCardsToDelete){
				group.getTransportCards().remove(cardToDelete);
			}
			
			
			ArrayList<PlaceToSleepCard> placetoCards = group.getPlaceToSleepCards();
			ArrayList<PlaceToSleepCard> placetoCardsToDelete = new ArrayList<PlaceToSleepCard>();
			for(PlaceToSleepCard card:placetoCards){
				if(card.getDestination().equalsIgnoreCase(destinationToDelete)){
					placetoCardsToDelete.add(card);
				}
			}
			for(PlaceToSleepCard cardToDelete:placetoCardsToDelete){
				group.getPlaceToSleepCards().remove(cardToDelete);
			}
			
			ArrayList<OtherCard> otherCards = group.getOtherCards();
			ArrayList<OtherCard> otherCardsToDelete = new ArrayList<OtherCard>();
			for(OtherCard card:otherCards){
				if(card.getDestination().equalsIgnoreCase(destinationToDelete)){
					otherCardsToDelete.add(card);
				}
			}
			for(OtherCard cardToDelete:otherCardsToDelete){
				group.getOtherCards().remove(cardToDelete);
			}
			
			this.putGroup(group);
		}else{
			throw new ElementNotFoundServiceException("Destination "+destinationToDelete+" doesn't exist");

		}
		
	}
	
	public Card putCard(String groupId, Card card) throws Exception {
		UserService userService = new UserServiceImpl();
		Group group;
		
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundServiceException("Group "+groupId+" not found");
		}
		
		//comprobem que el desti existeixi
		if( card.getDestination()==null || !group.getDestinations().contains(card.getDestination()))
			throw new DestinationDoesntExistException();
		
		//comprobem que l'usuari que ha creat la card existeix
		try{
			userService.getUser(card.getUserIdCreator());
		}catch(Exception e){
			throw new ElementNotFoundServiceException("User "+card.getUserIdCreator()+" not found");
		}
				
		//si o te id significa que es una nova card
		if(card.getCardId()==null){
			card.setCardId(idGen.generateId());
			card.setCreationDate((new Date()).getTime());
			
			switch(card.getCardType()){
				case "transport":
						TransportCard auxTransCard = (TransportCard) card;
						group.getTransportCards().add(auxTransCard);
					break;
					
				case "placeToSleep":
						PlaceToSleepCard auxPlaceCard = (PlaceToSleepCard) card;
						
						//comprobem que els parentCards existeixin
						for(String parentId:auxPlaceCard.getParentCardIds()){
							TransportCard parentCard = (TransportCard) cardExistOnArray(parentId,group.getTransportCards());
							if(parentCard==null){
								throw new ElementNotFoundServiceException("ParentCard "+parentId+" not found");
							}else{
								//heretem les dates de init i final del parent
								auxPlaceCard.setInitDate(parentCard.getInitDate());
								auxPlaceCard.setFinalDate(parentCard.getFinalDate());
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
		}else{ //si te ID el primer que fem es comprobar que aquesta card existeixi de veritat
			Card foundCard=null;
			switch(card.getCardType()){
				case "transport":
					
					foundCard=cardExistOnArray(card.getCardId(), group.getTransportCards());
					if(foundCard!=null){
						TransportCard auxTransCard = (TransportCard) foundCard;
						group.getTransportCards().remove(auxTransCard);
						
						auxTransCard=(TransportCard) card;
						group.getTransportCards().add(auxTransCard);
					}
					break;
					
				case "placeToSleep":
					foundCard=cardExistOnArray(card.getCardId(), group.getPlaceToSleepCards());
					if(foundCard!=null){ 
						PlaceToSleepCard auxPlaceCard = (PlaceToSleepCard) foundCard;
						//comprobem que els parentCards existeixin
						for(String parentId:auxPlaceCard.getParentCardIds()){
							TransportCard parentCard = (TransportCard) cardExistOnArray(parentId,group.getTransportCards());
							if(parentCard==null){
								throw new ElementNotFoundServiceException("ParentCard "+parentId+" not found");
							}
						}
						group.getPlaceToSleepCards().remove(auxPlaceCard);
						
						auxPlaceCard = (PlaceToSleepCard) card;
						group.getPlaceToSleepCards().add(auxPlaceCard);
					}
					break;
					
				case "other":
					foundCard=cardExistOnArray(card.getCardId(), group.getOtherCards());
					if(foundCard!=null){ 
						OtherCard auxOtherCard = (OtherCard) foundCard;
						group.getOtherCards().remove(auxOtherCard);
						
						auxOtherCard = (OtherCard) card;
						group.getOtherCards().add(auxOtherCard);
					}
					break;
					
				default:
					throw new CardTypeException();
		
		
			}
			if(foundCard==null)
				throw new InvalidIdsException("La Card con el ID, "+card.getCardId()+" de tipo "+card.getCardType()+", no exsiste en ");
		}
		this.putGroup(group);
		return card;
		
		
	}
	
	public Card cardExistOnArray(String cardId, ArrayList cardsArray){
		boolean cardExist=false;
		Card foundCard=null;
		Iterator<Card> it = cardsArray.iterator();
		while(it.hasNext()&&!cardExist){
			Card card = it.next();
			if(card.getCardId().equalsIgnoreCase(cardId)){
				cardExist=true;
				foundCard=card;
			}
		}
		
		return foundCard;
		
	}
	


	public void deleteCard(String groupId, String cardId) throws Exception {
		Group group;
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundServiceException("Group "+groupId+" not found");
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
					throw new ElementNotFoundServiceException("Card "+cardId+" not found");
				}
			}
		}
		this.putGroup(group);

	}

	public Card putVote(String groupId, String cardId, Vote vote)
			throws Exception {
		UserService userService = new UserServiceImpl();
		Group group;
		try{
			group=this.getGroup(groupId);
		}catch(Exception e){
			throw new ElementNotFoundServiceException("Group "+groupId+" not found");
		}
		Card foundCard=null;
		foundCard=cardExistOnArray(cardId, group.getTransportCards());
		if(foundCard==null){
			foundCard=cardExistOnArray(cardId, group.getPlaceToSleepCards());
			if(foundCard==null){
				foundCard=cardExistOnArray(cardId, group.getOtherCards());
			}
		}
		if(foundCard!=null){
			boolean found=false;
			Iterator<Vote> it = foundCard.getVotes().iterator();
			while(it.hasNext()&&!found){
				Vote cardVote = it.next();
				if(cardVote.getUserId().equalsIgnoreCase(vote.getUserId())){
					found=true;
					//si el usuario ya ha votado la carta eliminamos el voto antiguo
					foundCard.getVotes().remove(cardVote);
					//y guardamos el nuevo voto
					foundCard.getVotes().add(vote);
				}
			}
			if(!found){
				foundCard.getVotes().add(vote);
				//registramos el voto en el user
				User user =userService.getUser(vote.getUserId());
				user.getCardsVoted().add(cardId);
				userService.putUser(user);
			}
			//recalculamos el valor medio de votos
			foundCard.calculateAverage();	
			
			this.putCard(groupId, foundCard);
			
			
			return foundCard;
			
		}else{
			throw new ElementNotFoundServiceException("Card "+cardId+" not found");
		}

	}

	public void definePack(Group group) throws Exception {
		TransportCard bestTempTransportCard = null;
		PlaceToSleepCard bestPlaceToSleepCard = null;
		double bestTempValoration = 0;
		double avgPts;
		double avgTc;
		double ponderation;
		
		for (PlaceToSleepCard ptsCard : group.getPlaceToSleepCards()){
			avgPts = ptsCard.getAverage();
			for (String ptsId : ptsCard.getParentCardIds()) {
				TransportCard tcCard = (TransportCard) cardExistOnArray(ptsId, ptsCard.getParentCardIds());
				avgTc = tcCard.getAverage();
				
				//Pensar en la varianza
				ponderation = calculatePackPercentage(tcCard, ptsCard);
				
				if (ponderation > bestTempValoration){
					bestTempTransportCard = tcCard;
					bestPlaceToSleepCard = ptsCard;
				}	
			}
		}
		
		if (bestTempTransportCard.getBestPack() == false){
			bestTempTransportCard.setBestPack();
		}
		
		if (bestPlaceToSleepCard.getBestPack() == false){
			bestPlaceToSleepCard.setBestPack();
		}
	}

	public double calculatePackPercentage(TransportCard tcCard, PlaceToSleepCard ptsCard) throws Exception {

		return 0.5;
	}

}
