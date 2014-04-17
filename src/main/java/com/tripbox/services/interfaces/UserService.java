package com.tripbox.services.interfaces;

import com.tripbox.elements.User;

public interface UserService {

	/**
	 * Funcio que retorna un usuari a partir de la seva ID.
	 * 
	 * @param id
	 *            Identificador del'usuari que s'ha de retornar
	 * @return Retorna l'usuari amb l'identificador d'entrada
	 * @throws Exception
	 *             : Exception: En cas que no es trobi cap usuari amb la ID
	 *             indicada.
	 */
	public User getUser(String id) throws Exception;

	/**
	 * Metodo con doble funcionalidad, si le enviamos un Usuario sin ID y con un
	 * facebookId o googleId o un emial comprovamos si existe en la bbdd, en
	 * caso afirmativo devolvemos el User entero, en caso negativo creamos un
	 * User y lo devolvemos; en otro caso, si el User que recibimos no tiene ni
	 * ID ni facebookId ni googleId ni email devolvemos una Excepcions tipo
	 * InvalidIdsException. En el caso que el User que recibimos tenga ID
	 * comprobamos que la ID sea valida (ya que el cliente no puede modificar la
	 * ID) y guardamos los cambios. El parametro name Ã©s obligatorio, si el
	 * User que recibimos no tiene name o esta vacio sala una excepcion de tipo
	 * RequiredParametersException.
	 * 
	 * @param user
	 *            Objecte usuari que ha de ser inserit o modificat a la BD.
	 * @return Retorna una resposta 200 si s'ha fet l'operacio correctament.
	 * @throws Exception
	 */
	public User putUser(User user) throws Exception;

	/**
	 * Funcio de la API que esborra un usuari a partir de la seva ID.
	 * 
	 * @param id
	 *            Identificador de l'usuari que s'ha d'esborrar
	 * @throws Exception
	 *             : Si no es troba cap usuari amb la ID indicada.
	 */
	public void deleteUser(String id) throws Exception;

	/**
	 * Funcion que añade un usuario a un grupo y un grupo a un usuario.
	 * 
	 * @param userId
	 *            : Identificador del usuario
	 * @param groupId
	 *            : Identificador del grupo
	 * @throws Exception
	 *             : Si el grupo o el usuario no existen (id erronea)
	 */
	public void addGroupToUser(String id, String groupId) throws Exception;

}
