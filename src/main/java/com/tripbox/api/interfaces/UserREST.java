package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.User;

public interface UserREST {
	
	/**
	 * Funcion de la API que devuelve un usuario a partir de su ID.
	 * @param id Identificador del usuario que se ha de devolver
	 * @return Devuelve el usuario especificado con el ID
	 */
	public Response getUser(String id);
	
	/**
	 * Funcion de la API que añade un usuario a la BD o lo modifica.
	 * @param user Objeto usuario que se quiere insertar en la BD.
	 * @return Devuelve el objeto usuario modificado, con la nueva ID en caso de ser nuevo y el estado de la peticion.
	 */
	public Response putUser(User user);
	
	/**
	 * Funcion de la API que añade un usuario a un grupo y un grupo a un usuario.
	 * @param id	Identificador del usuario que queremos insertar en el nuevo grupo.
	 * @param groupId	Identificador del grupo al que se va a insertar el usuario.
	 * @param user Objeto usuario que se va a modificar en la BD (poniendolo en un nuevo grupo).
	 * @return Devuelve el estado de la petición.
	 */
	public Response addGroupToUser(String id, String groupId);
	
	/**
	 * Funcion de la API que elimina un usuario de la BD a partir de su ID.
	 * @param id Identificador del usuario que queremos eliminar.
	 */
	public void deleteUser(String id);

}
