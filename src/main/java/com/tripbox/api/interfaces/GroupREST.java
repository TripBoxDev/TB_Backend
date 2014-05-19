package com.tripbox.api.interfaces;

import java.io.File;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Group;
import com.tripbox.elements.OtherCard;
import com.tripbox.elements.PlaceToSleepCard;
import com.tripbox.elements.TransportCard;
import com.tripbox.elements.Vote;

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
	 * @param id
	 *            Identificador del grup que s'ha d'esborrar
	 */
	public Response deleteGroup(String id);

	/**
	 * Funcion de la API que nos permite eliminar un User de un Group. En el
	 * caso que el Group se quede sin Users se elimina.
	 * 
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public Response deleteUserToGroup(String groupId, String userId);

	/**
	 * Funcion de la API que nos permite anadir un destino al Group.
	 * 
	 * @param id
	 *            Id del Group donde queremos anadir el destino.
	 * @param newDestination
	 *            Nueva destinacion que queremos anadir al Group.
	 * @return
	 */
	public Response putDestination(String id, String newDestination);

	/**
	 * Funcion de la API que nos permite eliminar un destino de un Group.
	 * 
	 * @param id
	 *            Id del Group al que queremos eliminar el destino.
	 * @param idDestination
	 *            Destino que queremos eliminar.
	 * @return
	 */
	public Response deleteDestination(String groupId, String idDestination);

	/**
	 * Funcion de la API que nos permite agregar una Card a un Group segun el
	 * tipo de Card que es.
	 * 
	 * @param id
	 *            Id del Group donde queremos anadir la Card.
	 * @param card
	 *            Card que queremos anadir. El campo cardType es obligatorio.
	 * @return
	 */
	public Response putCard(String id, TransportCard card);

	public Response putCard(String id, PlaceToSleepCard card);

	public Response putCard(String id, OtherCard card);

	/**
	 * Funcion de la API que nos permite eliminar una Card de un Group
	 * 
	 * @param groupId
	 *            Id del Group.
	 * @param cardId
	 *            Id de la Card que queremos eliminar.
	 * @return
	 */
	public Response deleteCard(String groupId, String cardId);

	/**
	 * Función de la API que nos permite añadir un voto a una card
	 * 
	 * @param groupId
	 * @param cardId
	 * @param vote
	 * @return
	 */
	public Response putVote(String groupId, String cardId, Vote vote);
	
	/**
	* Funcion para subir una imagen al servidor, el nombre de la imagen es el ID.
	* @param groupId String indica que grupo es.
	* @param fileImage Archivo en si.
	*/
	public Response saveGroupImage(String groupId, File fileImage);
	
	/**
	* Funcion para marcar cual es la propuesta definitiva
	* @param groupId String indica que grupo es.
	* @param idTransporte String indica que transporte es.
	* @param idAlojamiento String indica que alojamiento es.
	*/
	public Response finalProposition(String groupId, String idTransporte, String idAlojamiento);
}