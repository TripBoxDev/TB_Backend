package com.tripbox.api.interfaces;

import java.io.File;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Card;
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
	 * Funcion de la API que retorna un grupo a partir de la id del grupo.
	 * 
	 * @param id Identificador del grupo que se ha de retornar
	 * @return Devuelve el estado de la petición y el grupo seleccionado gracias a la id
	 */
	public Response getGroup(String id);

	/**
	 * Funcion de la API que modifica un grupo en la BD si el grupo ya existe o le asigna una ID i
	 * lo inserta si el grupo es nuevo.
	 * 
	 * @param group Objeto grupo que tiene que ser modificado o insertado nuevo en la BD.
	 * @return Devuelve el estado de la peticion y el grupo modificado, con la nueva ID si el grupo es nuevo
	 */
	public Response putGroup(Group group);

	/**
	 * Funcion de la API que borra un grupo de la BD a partir de su ID.
	 *
	 * @param id  Identificador del grupo que se tiene que borrar
	 * @return Devuelve el estado de la peticion
	 */
	public Response deleteGroup(String id);

	/**
	 * Funcion de la API que nos permite eliminar un User de un Group. En el
	 * caso que el Group se quede sin Users se elimina el grupo.
	 *
	 * @param groupId Identificador del grupo del cual queremos borrar al usuario
	 * @param userId Identificador del usuario que queremos borrar del grupo
	 * @return Devuelve el estado de la peticion
	 */
	public Response deleteUserToGroup(String groupId, String userId);

	/**
	 * Funcion de la API que nos permite anadir un destino al Group indicado.
	 *
	 * @param id Id del Group donde queremos anadir el destino.
	 * @param newDestination Nueva destinacion que queremos anadir al Group.
	 * @return Devuelve el estado de la petición y el nuevo objeto Destination creado.
	 */
	public Response putDestination(String id, String newDestination);

	/**
	 * Funcion de la API que nos permite eliminar un destino de un Group.
	 *
	 * @param groupId Identificador del grupo al que pertenece el destino que queremos borrar.
	 * @param idDestination  Id del destino que queremos eliminar.
	 * @return Devuelve el estado de la petición
	 */
	public Response deleteDestination(String groupId, String idDestination);

	/**
	 * Funcion de la API que nos permite agregar una Card a un Group segun el
	 * tipo de Card que es (Transport, PlaceToSleep, Other).
	 *
	 * @param id    Id del Group donde queremos anadir la Card.
	 * @param card  Card que queremos anadir. El campo cardType es obligatorio.
	 * @return Devuelve la Card añadida
	 */
	public Response putCard(String id, TransportCard card);

	/**
	 * Funcion de la API que nos permite agregar una Card a un Group segun el
	 * tipo de Card que es (Transport, PlaceToSleep, Other).
	 *
	 * @param id    Id del Group donde queremos anadir la Card.
	 * @param card  Card que queremos anadir. El campo cardType es obligatorio.
	 * @return Devuelve la Card añadida
	 */
	public Response putCard(String id, PlaceToSleepCard card);

	/**
	 * Funcion de la API que nos permite agregar una Card a un Group segun el
	 * tipo de Card que es (Transport, PlaceToSleep, Other).
	 *
	 * @param id    Id del Group donde queremos anadir la Card.
	 * @param card  Card que queremos anadir. El campo cardType es obligatorio.
	 * @return Devuelve la Card añadida
	 */
	public Response putCard(String id, OtherCard card);

	/**
	 * Funcion de la API que nos permite eliminar una Card de un Group.
	 *
	 * @param groupId   Id del Group al que pertenece la Card que queremos eliminar.
	 * @param cardId   Id de la Card que queremos eliminar.
	 * @return Devuelve el estado de la peticion
	 */
	public Response deleteCard(String groupId, String cardId);

	/**
	 * Función de la API que nos permite añadir un voto a una card.
	 *
	 * @param groupId Id del Group al que pertenece la Card que queremos votar.
	 * @param cardId Id de la Card que queremos votar.
	 * @param vote Objeto Vote que contiene el valor del voto y el id del usuario que vota.
	 * @return Devuelve la card votada y el estado de la peticion.
	 */
	public Response putVote(String groupId, String cardId, Vote vote);
	
	/**
	 * Funcion para subir una imagen de grupo al servidor, el nombre de la imagen es el ID del grupo.
	 *
	 * @param groupId Identificador del grupo al que le queremos subir la imagen.
	 * @param fileImage Archivo que queremos subir como imagen.
	 * @return Devuelve el estado de la peticion.
	 */
	public Response saveGroupImage(String groupId, File fileImage);
	
	/**
	 * Funcion para subir una imagen para una Card al servidor, el nombre de la imagen es el ID de la Card.
	 *
	 * @param groupId Identificador del grupo al que le queremos subir la imagen.
	 * @param fileImage Archivo que queremos subir como imagen.
	 * @return Devuelve el estado de la peticion.
	 */
	public Response saveCardImage(String groupId, String cardId, String type, File fileImage);
	
	/**
	 * Funcion para marcar cuales son las cards que conforman la propuesta definitiva
	 *
	 * @param groupId Identificador del grupo al que pertenecen las cards propuestas como plan final.
	 * @param idTransporte Identificador de la Card de transporte.
	 * @param idAlojamiento Identificador de la Card de PlaceToSleep.
	 * @return Devuelve el grupo actualizado con la propuesta final marcada y el estado de la peticion.
	 */
	public Response finalProposition(String groupId, String idTransporte, String idAlojamiento);
	
	/**
	 * Funcio de la API que elimina la propuesta final de un grupo (las cards que la forman no).
	 *
	 * @param groupId    Identificador del grupo donde estan las cards que hay que borrar de la propuesta final
	 * @param transportId    Identificador de la card de transporte que hay que borrar de la propuesta final
	 * @param placeToSleepId	Identificador de la card de alojamiento que hay que borrar de la propuesta final
	 * @return the response
	 */
	public Response deleteProposition(String groupId, String transportId, String placeToSleepId);
	
	
	/**
	 * Funcion de la API para votar una propuesta final.
	 *
	 * @param groupId Identificador del grupo donde estan las cards a votar.
	 * @param vote Booleano que indica si el voto del usuario es positivo (true) o negativo (false).
	 * @param userId Identificador del usuario que vota.
	 * @return Devuelve el estado de la petición y el grupo actualizado.
	 */
	public Response putVoteFinalProposition(String groupId, String userId, boolean vote);
}