package com.tripbox.api.interfaces;

import java.io.File;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Group;
import com.tripbox.elements.OtherCard;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.User;
import com.tripbox.elements.Vote;

// TODO: Auto-generated Javadoc
/**
 * The Interface GroupREST.
 */
public interface GroupREST {

	/**
	 * Funcio de la API que retorna un grup a partir de la seva ID.
	 * 
	 * @param id
	 *            Identificador del grup que s'ha de retornar
	 * @return Retorna el grup amb l'identificador d'entrada
	 */
	public Response getGroup(String id);

	/**
	 * Funcio de la API que modifica un grup a la BD o li assigna una ID i
	 * l'inserta si es nou.
	 * 
	 * @param group
	 *            Objecte grup que ha de ser inserit o modificat a la BD.
	 * @return Retorna el grup modificat, amb la nova ID si correspont, i una
	 *         resposta 200 si s'ha fet l'operacio correctament.
	 */
	public Response putGroup(Group group);

	/**
	 * Funcio de la API que esborra un grup a partir de la seva ID.
	 *
	 * @param id            Identificador del grup que s'ha d'esborrar
	 * @return the response
	 */
	public Response deleteGroup(String id);

	/**
	 * Funcion de la API que nos permite eliminar un User de un Group. En el
	 * caso que el Group se quede sin Users se elimina.
	 *
	 * @param groupId the group id
	 * @param userId the user id
	 * @return the response
	 */
	public Response deleteUserToGroup(String groupId, String userId);

	/**
	 * Funcion de la API que nos permite anadir un destino al Group.
	 *
	 * @param id            Id del Group donde queremos anadir el destino.
	 * @param newDestination            Nueva destinacion que queremos anadir al Group.
	 * @return the response
	 */
	public Response putDestination(String id, String newDestination);

	/**
	 * Funcion de la API que nos permite eliminar un destino de un Group.
	 *
	 * @param groupId the group id
	 * @param idDestination            Destino que queremos eliminar.
	 * @return the response
	 */
	public Response deleteDestination(String groupId, String idDestination);

	/**
	 * Funcion de la API que nos permite agregar una Card a un Group segun el
	 * tipo de Card que es.
	 *
	 * @param id            Id del Group donde queremos anadir la Card.
	 * @param card            Card que queremos anadir. El campo cardType es obligatorio.
	 * @return the response
	 */
	public Response putCard(String id, TransportCard card);

	/**
	 * Put card.
	 *
	 * @param id the id
	 * @param card the card
	 * @return the response
	 */
	public Response putCard(String id, PlaceToSleepCard card);

	/**
	 * Put card.
	 *
	 * @param id the id
	 * @param card the card
	 * @return the response
	 */
	public Response putCard(String id, OtherCard card);

	/**
	 * Funcion de la API que nos permite eliminar una Card de un Group.
	 *
	 * @param groupId            Id del Group.
	 * @param cardId            Id de la Card que queremos eliminar.
	 * @return the response
	 */
	public Response deleteCard(String groupId, String cardId);

	/**
	 * Función de la API que nos permite añadir un voto a una card.
	 *
	 * @param groupId the group id
	 * @param cardId the card id
	 * @param vote the vote
	 * @return the response
	 */
	public Response putVote(String groupId, String cardId, Vote vote);
	
	/**
	 * Funcion para subir una imagen de grupo al servidor, el nombre de la imagen es el ID.
	 *
	 * @param groupId String indica que grupo es.
	 * @param fileImage Archivo en si.
	 * @return the response
	 */
	public Response saveGroupImage(String groupId, File fileImage);
	
	/**
	 * Funcion para subir una imagen de Card al servidor, el nombre de la imagen es el ID.
	 *
	 * @param groupId String indica que grupo es.
	 * @param fileImage Archivo en si.
	 * @return the response
	 */
	public Response saveCardImage(String groupId, File fileImage);
	
	/**
	 * Funcion para marcar cual es la propuesta definitiva.
	 *
	 * @param groupId String indica que grupo es.
	 * @param idTransporte String indica que transporte es.
	 * @param idAlojamiento String indica que alojamiento es.
	 * @return the response
	 */
	public Response finalProposition(String groupId, String idTransporte, String idAlojamiento);
	
	
	/**
	 * Funcion para votar una propuesta final.
	 *
	 * @param groupId String indica que grupo es.
	 * @param vote boolean indica si el voto es positivo (true) o negativo (false)
	 * @param userId String id del usuario que vota
	 * @return Response estado de la petición
	 */
	public Response putVoteFinalProposition(String groupId, String userId, boolean vote);
}