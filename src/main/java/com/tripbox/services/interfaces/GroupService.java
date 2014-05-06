package com.tripbox.services.interfaces;

import java.io.File;

import com.tripbox.elements.Card;
import com.tripbox.elements.Group;

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
	public void putDestination(String groupId, String newDestination)
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
	 * Funci�n que permite subir una imagen al servidor.
	 * @param groupId String Id del Group.
	 * @param fileImage File Archivo en si.
	 * @param uploadedFileLocation String Localitzaci� on guardar l'arxiu.
	 */
	public void saveGroupImage(String groupId, File fileImage,
			String uploadedFileLocation) throws Exception;

}
