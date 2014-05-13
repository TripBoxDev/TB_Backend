package com.tripbox.services.interfaces;


import com.tripbox.elements.Card;
import com.tripbox.elements.Group;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.Vote;

public interface GroupService {
	
	public Group getGroup(String id) throws Exception;
	public Group putGroup(Group user) throws Exception;
	public void deleteGroup(String id) throws Exception;
	
	/**
	 * Funci��n que permite eliminar un User de un Group
	 * @param groupId
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUserToGroup(String groupId, String userId) throws Exception;
	
	/**
	 * A��adimos un destino al array destinations de un Group.
	 * @param groupId Id del Group donde queremos a��adir el destino.
	 * @param newDestination Nueva destinaci��n que queremos a��adir al Group. 
	 * @throws Exception
	 */
	public void putDestination(String groupId, String newDestination) throws Exception;
	
	/**
	 * Eliminamos un destino de un Group i todas las cards relacionadas con ese destino.
	 * @param groupId Id del Group al que queremos eliminar el destino.
	 * @param destinationToDelete Destino que queremos eliminar.
	 * @throws Exception
	 */
	public void deleteDestination( String groupId, String destinationToDelete)throws Exception;
	
	/**
	 * A��adimos una Card a un Group. A partir del atributo cardType de la Card la a��adimos al array correspondiente del Group.
	 * Si cardType=="transport" a��adimos la Card al array transportCards del Group.
	 * Si cardType=="placeToSleep" a��adimos la Card al array placeToSleepCards del Group.
	 * Si cardType=="other" a��adimos la Card al array otherCards del Group.
	 * @param groupId Id del Group donde queremos a��adir la Card
	 * @param card Card que queremos a��adir. El campo cardType es obligatorio.
	 * @return
	 * @throws Exception
	 */
	public Card putCard(String groupId, Card card)throws Exception;
	
	
	/**
	 * Eliminamos la card especificada del Group.
	 * @param groupId Id del Group
	 * @param cardId Id de la Card que queremos eliminar
	 * @throws Exception
	 */
	public void deleteCard(String groupId,  String cardId)throws Exception;
	
	/**
	 * A��adimos un voto a una card. Si el usuario ya tiene un voto registrado lo sobreescribimos.
	 * @param groupId
	 * @param cardId
	 * @param vote
	 * @return
	 * @throws Exception
	 */
	public Card putVote(String groupId, String cardId, Vote vote) throws Exception;

	/**
	 * Funcion que retorna una card a partir de su Id.
	 * @param cardId: ID de la card que queremos cojer
	 * @param type: tipo de card (transport, placeToSleep, other)
	 * @param grupo: Grupo en el que se encuentra la card
	 * @return: Card con ID del input
	 */
	public Card getCard(String cardId, String type, Group grupo);
	
	/**
	 * Funcion que permite definir el pack mejor valorado de un destino.
	 */
	public void definePack(Group group) throws Exception;
	
	/**
	 * Funcion que permite calcular el porcentaje de destino.
	 */
	public double calculatePackPercentage(TransportCard tcCard, PlaceToSleepCard ptsCard, Group group) throws Exception;

}
