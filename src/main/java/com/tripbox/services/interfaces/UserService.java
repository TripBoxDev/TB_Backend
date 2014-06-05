package com.tripbox.services.interfaces;

import com.tripbox.elements.User;

public interface UserService {
	
	/**
	 * Funcion que retorna un usuario a partir de su ID.
	 * @param id Identificador del usuario que se busca.
	 * @return Retorna l'usuari amb l'identificador d'entrada
	 * @throws Exception: Exception: En cas que no es trobi cap usuari amb la ID indicada.
	 */
	public User getUser(String id) throws Exception;
	
	/**
	 * Metodo con doble funcionalidad, si le enviamos un Usuario sin ID y con un facebookId o googleId o un email comprobamos si 
	 * existe en la BD, en caso afirmativo devolvemos el User entero, en caso negativo creamos un User y lo devolvemos;
	 * en otro caso, si el User que recibimos no tiene ni ID ni facebookId ni googleId ni email devolvemos una Excepcions tipo
	 * InvalidIdsException.
	 * En el caso que el User que recibimos tenga ID comprobamos que la ID sea valida (ya que el cliente no puede modificar
	 * la ID) y guardamos los cambios.
	 * El parametro name ï¿½ï¿½s obligatorio, si el User que recibimos no tiene name o esta vacio sala una excepcion de tipo
	 * RequiredParametersException.
	 * @param user Objecte usuari que ha de ser inserit o modificat a la BD.
	 * @return Devuelve el User que hemos insertado y el estado de la peticion.
	 * @throws Exception: Nos devuelve el error que ha ocurrido.
	 */
	public User putUser(User user) throws Exception;
	
	/**
	 * Funcion que borra un usuario a partir de su ID.
	 * @param id: Identificador del usuario que debe ser borrado
	 * @throws Exception: Si no se encuentra ningun usuario con la ID indicada
	 */
	public void deleteUser(String id)throws Exception;
	
	/**
	 * Funcion que añade un usuario a un grupo y un grupo a un usuario.
	 * @param userId:  Identificador del usuario que queremos añadir al grupo.
	 * @param groupId: Identificador del grupo al que queremos añadir el usuario.
	 * @throws Exception: Si el grupo o el usuario no existen (id erronea)
	 */
	public void addGroupToUser(String userId, String groupId)throws Exception;
}
