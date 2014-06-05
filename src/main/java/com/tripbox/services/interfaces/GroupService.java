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

	/**
	 * Funcion de la API que retorna un grupo a partir de la id del grupo.
	 * 
	 * @param id Identificador del grupo que se ha de retornar
	 * @return Devuelve el estado de la petición y el grupo seleccionado gracias a la id
	 */
	public Group getGroup(String id) throws Exception;

	/**
	 * Funcion de la API que modifica un grupo en la BD si el grupo ya existe o le asigna una ID i
	 * lo inserta si el grupo es nuevo.
	 * 
	 * @param group Objeto grupo que tiene que ser modificado o insertado nuevo en la BD.
	 * @return Devuelve el estado de la peticion y el grupo modificado, con la nueva ID si el grupo es nuevo
	 */
	public Group putGroup(Group user) throws Exception;

	/**
	 * Funcion de la API que borra un grupo de la BD a partir de su ID.
	 *
	 * @param id  Identificador del grupo que se tiene que borrar
	 * @return Devuelve el estado de la peticion.
	 */
	public void deleteGroup(String id) throws Exception;

	/**
	 * Función que permite eliminar un User de un Group buscando el id del usuario en el grupo elegido 
	 * por el id del grupo.
	 * 
	 * @param groupId Identificador del grupo al que pertenece el usuario a el que queremos borrar.
	 * @param userId Identificador del usuario a borrar del grupo
	 * @return Devuelve el estado de la peticion.
	 */
	public void deleteUserToGroup(String groupId, String userId)
			throws Exception;

	/**
	 * Añadimos un destino al array destinations de un Group, el destino no puede existir.
	 * 
	 * @param groupId  Id del Group donde queremos añadir el destino.
	 * @param newDestination  Nueva destinación que queremos añadir al Group.
	 * @return Devuelve el destino que hemos añadido.
	 */
	public Destination putDestination(String groupId, String newDestination)
			throws Exception;

	/**
	 * Eliminamos un destino de un Group i todas las cards relacionadas con ese
	 * destino.
	 * 
	 * @param groupId Id del Group al que queremos eliminar el destino.
	 * @param destinationToDelete Nombre de destino que queremos eliminar.
	 * @return Devuelve el estado de la peticion.
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
	 * @param groupId Id del Group donde queremos añadir la Card
	 * @param card Card que queremos añadir. El campo cardType es obligatorio.
	 * @return Devuelve la Card que hemos añadido.
	 */
	public Card putCard(String groupId, Card card) throws Exception;

	/**
	 * Eliminamos la card especificada del Group.
	 * 
	 * @param groupId Id del Group en el que esta la Card que queremos eliminar
	 * @param cardId Id de la Card que queremos eliminar
	 * @return Devuelve el estado de la peticion.
	 */
	public void deleteCard(String groupId, String cardId) throws Exception;

	/**
	 * Añadimos un voto a una card. Si el usuario ya tiene un voto registrado lo
	 * sobreescribimos. Una vez votado hace el calculo de la media y hace una definicion
	 * de mejor pack, ya que con la votación puede haber cambiado el mejor pack.
	 * 
	 * @param groupId Id del Group en el que esta la Card que queremos votar.
	 * @param cardId Id de la Card que queremos votar.
	 * @param vote objeto Vote que contiene el id de usuario a quien pertenece el voto y su valor.
	 * @return Devuelve el estado de la petición y la Card que hemos votado, actualizada.
	 */
	public Card putVote(String groupId, String cardId, Vote vote)
			throws Exception;

	/**
	* Función que permite subir una imagen para el grupo al servidor. Una vez subida cambia el flag flagImage para
	* saber que existe una imagen en el servidor para este Group.
	* 
	* @param groupId Id del Group al que pertenecerá la imagen.
	* @param fileImage Archivo que vamos a subir al servidor como la imagen del grupo.
	* @param uploadedFileLocation Localización donde queremos guardar la imagen en el servidor.
	* @return Devuelve el estado de la petición.
	*/
	public void saveGroupImage(String groupId, File fileImage,
	String uploadedFileLocation) throws Exception;
	
	/**
	* Función que permite subir una imagen para la Card al servidor. Una vez subida cambia el flag flagImage para
	* saber que existe una imagen en el servidor para esta Card.
	* 
	* @param cardId Id de la Card al que pertenecerá la imagen.
	* @param groupId Id del Group al que pertenece la Card a la que se le va a subir la imagen.
	* @param type Tipo de Card
	* @param fileImage Archivo que vamos a subir al servidor como la imagen del grupo.
	* @param uploadedFileLocation Localización donde queremos guardar la imagen en el servidor.
	* @return Devuelve el estado de la petición.
	*/
	public void saveCardImage(String cardId, String groupId, String type, File fileImage,
	String uploadedFileLocation) throws Exception;
	
	/**
	 * Funcion que permite definir el pack mejor valorado de un destino. Para definir un pack necesitamos 2
	 * Cards linkadas, si no estan linkadas entre ellas no pueden ser pack. Esta funcion sirve para definir los
	 * packs mejor valorados de cada destino perteneciente al grupo.
	 * 
	 * @param group Objeto Group en el cual definiremos el mejor pack para cada destino.
	 * @return Devuelve el estado de la petición.
	 */
	public void definePack(Group group) throws Exception;
	
	/**
	 * Funcion que permite calcular el porcentaje del destino con una formula para darle importancia a aspectos como
	 * el numero de gente que ha votado o los eventos que hay en el destino. Este calculo se hace mediante las votaciones
	 * de los usuarios a las cards.
	 * 
	 * @param group Grupo al que pertenecen las cards y el destino a calcular.
	 * @param tcCard Card de tansporte a calcular.
	 * @param ptsCard Card de alojamiento a calcular.
	 * @return Devuelve el estado de la petición y el porcentaje del destino.
	 */
	public double calculatePackPercentage(TransportCard tcCard, PlaceToSleepCard ptsCard, Group group) throws Exception;


	/**
	* Funcion para marcar cual es la propuesta definitiva de un grupo. El usuario envia las 2 cards que formarán
	* parte de la propuesta definitiva y cambia el flag finalProposition a True para indicar que esa Card forma
	* parte de la propuesta definitiva.
	* 
	* @param groupId Id del grupo al que pertenece la propuesta definitiva.
	* @param idTransporte Id de la Card  de transporte propuesta.
	* @param idAlojamiento Id de la Card de alojamiento propuesta.
	* @return Devuelve el estado de la petición y el grupo actualizado con la propuesta final.
	*/
	public Group finalProposition(String groupId, String idTransporte,
			String idAlojamiento) throws Exception;

	/**
	* Funcion para eliminar una propuesta final, cambia el flag finalProposition a False de las Cards elegidas
	* como propuesta definitiva.
	* 
	* @param groupId Id del grupo al que pertenece la propuesta definitiva.
	* @param idTransporte Id de la Card  de transporte que estaba propuesta.
	* @param idAlojamiento Id de la Card de alojamiento que estaba propuesta.
	* @return Devuelve el estado de la petición y el grupo actualizado sin la propuesta final.
	*/
	public Group deleteFinalProposition(String groupId, String transportId,
			String placeToSleepId) throws Exception;
	
	/**
	 * Funcion para votar una propuesta final. Una vez tenemos un pack como propuesta definitiva los usuario lo
	 * votan. Si el voto es positivo lo pone en un array para votos positivos, sino lo pone en el array de votos
	 * negativos.
	 *
	 * @param groupId Id del grupo al que pertenece la propuesta definitiva.
	 * @param vote boolean indica si el voto es positivo (true) o negativo (false)
	 * @param userId Id del usuario que ha votado la propuesta definitiva.
	 * @return Devuelve el estado de la petición y el grupo actualizado con la nueva votación.
	 */
	public Group putVoteFinalProposition(String groupId, String userId,
			boolean vote) throws Exception;

}