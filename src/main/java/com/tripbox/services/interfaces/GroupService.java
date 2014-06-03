package com.tripbox.services.interfaces;

import java.io.File;

import com.tripbox.elements.Card;
import com.tripbox.elements.Destination;
import com.tripbox.elements.Group;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.Vote;
import com.tripbox.services.exceptions.ElementNotFoundServiceException;

public interface GroupService {

	public Group getGroup(String id) throws Exception;

	public Group putGroup(Group user) throws Exception;

	public void deleteGroup(String id) throws Exception;

	/**
	 * Función que permite eliminar un User de un Group
	 * 
	 * @param groupId
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUserToGroup(String groupId, String userId)
			throws Exception;

	/**
	 * Añadimos un destino al array destinations de un Group.
	 * 
	 * @param groupId
	 *            Id del Group donde queremos añadir el destino.
	 * @param newDestination
	 *            Nueva destinación que queremos añadir al Group.
	 * @throws Exception
	 */
	public Destination putDestination(String groupId, String newDestination)
			throws Exception;

	/**
	 * Eliminamos un destino de un Group i todas las cards relacionadas con ese
	 * destino.
	 * 
	 * @param groupId
	 *            Id del Group al que queremos eliminar el destino.
	 * @param destinationToDelete
	 *            Destino que queremos eliminar.
	 * @throws Exception
	 */
	public void deleteDestination(String groupId, String destinationToDelete)
			throws Exception;

	/**
	 * Añadimos una Card a un Group. A partir del atributo cardType de la Card
	 * la añadimos al array correspondiente del Group. Si cardType=="transport"
	 * añadimos la Card al array transportCards del Group. Si
	 * cardType=="placeToSleep" añadimos la Card al array placeToSleepCards del
	 * Group. Si cardType=="other" añadimos la Card al array otherCards del
	 * Group.
	 * 
	 * @param groupId
	 *            Id del Group donde queremos añadir la Card
	 * @param card
	 *            Card que queremos añadir. El campo cardType es obligatorio.
	 * @return
	 * @throws Exception
	 */
	public Card putCard(String groupId, Card card) throws Exception;

	/**
	 * Eliminamos la card especificada del Group.
	 * 
	 * @param groupId
	 *            Id del Group
	 * @param cardId
	 *            Id de la Card que queremos eliminar
	 * @throws Exception
	 */
	public void deleteCard(String groupId, String cardId) throws Exception;

	/**
	 * Añadimos un voto a una card. Si el usuario ya tiene un voto registrado lo
	 * sobreescribimos.
	 * 
	 * @param groupId
	 * @param cardId
	 * @param vote
	 * @return
	 * @throws Exception
	 */
	public Card putVote(String groupId, String cardId, Vote vote)
			throws Exception;

	/**
	* Función que permite subir una imagen al servidor.
	* @param groupId String Id del Group.
	* @param fileImage File Archivo en si.
	* @param uploadedFileLocation String Localització on guardar l'arxiu.
	*/
	public void saveGroupImage(String groupId, File fileImage,
	String uploadedFileLocation) throws Exception;
	
	/**
	* Función que permite subir una imagen al servidor.
	* @param groupId String Id del Group.
	* @param fileImage File Archivo en si.
	* @param uploadedFileLocation String Localització on guardar l'arxiu.
	*/
	public void saveCardImage(String cardId, String groupId, String type, File fileImage,
	String uploadedFileLocation) throws Exception;
	
	/**
	 * Funcion que permite definir el pack mejor valorado de un destino.
	 */
	public void definePack(Group group) throws Exception;
	
	/**
	 * Funcion que permite calcular el porcentaje de destino.
	 */
	public double calculatePackPercentage(TransportCard tcCard, PlaceToSleepCard ptsCard, Group group) throws Exception;


	/**
	* Funcion para marcar cual es la propuesta definitiva
	* @param groupId String indica que grupo es.
	* @param idTransporte String indica que transporte es.
	* @param idAlojamiento String indica que alojamiento es.
	* @return 
	*/
	public Group finalProposition(String groupId, String idTransporte,
			String idAlojamiento) throws Exception;

	/**
	* Funcion para eliminar una proposicion final
	* @param groupId String indica en que grupo estan las cards.
	* @param transportId String indica que transporte es.
	* @param placeToSleepId String indica que alojamiento es. 
	 * @return 
	* @throws Exception 
	*/
	public Group deleteFinalProposition(String groupId, String transportId,
			String placeToSleepId) throws Exception;
	
	/**
	 * Funcion para votar una propuesta final.
	 *
	 * @param groupId String indica que grupo es.
	 * @param vote boolean indica si el voto es positivo (true) o negativo (false)
	 * @param userId String id del usuario que vota
	 * @return Group grupo actualizado
	 * @throws Exception 
	 */
	public Group putVoteFinalProposition(String groupId, String userId,
			boolean vote) throws Exception;

}