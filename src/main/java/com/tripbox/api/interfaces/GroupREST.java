package com.tripbox.api.interfaces;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.tripbox.elements.Card;
import com.tripbox.elements.Group;

public interface GroupREST {
	
	/**
	 * Funcio de la API que retorna un grup a partir de la seva ID.
	 * @param id Identificador del grup que s'ha de retornar
	 * @return Retorna el grup amb l'identificador d'entrada
	 */
	public Response getGroup(String id);
	
	/**
	 * Funcio de la API que modifica un grup a la BD o li assigna una ID i l'inserta si es nou.
	 * @param group Objecte grup que ha de ser inserit o modificat a la BD.
	 * @return Retorna el grup modificat, amb la nova ID si correspont, i una resposta 200 si s'ha fet l'operacio correctament.
	 */
	public Response putGroup(Group group);
	
	/**
	 * Funcio de la API que esborra un grup a partir de la seva ID.
	 * @param id Identificador del grup que s'ha d'esborrar
	 */
	public Response deleteGroup(String id);

	/**
	 * Función de la API que nos permite eliminar un User de un Group. En el caso que el Group se quede sin Users
	 * se elimina.
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public Response deleteUserToGroup(String groupId, String userId);
	
	/**
	 * Función de la API que nos permite añadir un destino al Group.
	 * @param id Id del Group donde queremos añadir el destino.
	 * @param newDestination Nueva destinación que queremos añadir al Group. 
	 * @return
	 */
	public Response putDestination(String id, String newDestination);
	
	/**
	 * Función de la API que nos permite eliminar un destino de un Group.
	 * @param id Id del Group al que queremos eliminar el destino.
	 * @param destinationToDelete Destino que queremos eliminar.
	 * @return
	 */
	public Response deleteDestination( String id, String destinationToDelete);
	
	/**
	 * Función de la API que nos permite agregar una Card a un Group según el tipo de Card que es. 
	 * @param id Id del Group donde queremos añadir la Card.
	 * @param card Card que queremos añadir. El campo cardType es obligatorio.
	 * @return
	 */
	public Response putCard(String id, Card card);
	
	
	/**
	 * Función de la API que nos permite eliminar una Card de un Group
	 * @param groupId Id del Group.
	 * @param cardId Id de la Card que queremos eliminar.
	 * @return
	 */
	public Response deleteCard(String groupId,  String cardId);
	

}
