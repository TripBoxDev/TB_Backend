package com.tripbox.bbdd.interfaces;

import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;

public interface Querys {
	
	/**
	 * Funcion del objeto Mock que devuelve un usuario a partir de su ID.
	 * @param id Identificador del usuario que se ha de devolver
	 * @return Devuelve el usuario buscado y el estado de la petición.
	 * @throws Exception: En caso de que no encuentre ningún usuario con la ID indicada.
	 */
	public User getUser(String id) throws Exception;
	
	/**
	 * Funcion que busca y devuelve un objeto User a partir de su facebookId, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el facebookId del User
	 * @return Devuelve el usuario buscado y el estado de la petición.
	 * @throws Exception: En caso de que no encuentre ningún usuario con la facebookID indicada.
	 */
	public User getUserbyFacebookId(String facebookId) throws ItemNotFoundException;
	
	/**
	 * Funcion que busca y devuelve un objeto User a partir de su GoogleId, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el facebookId del User
	 * @return Devuelve el usuario buscado y el estado de la petición.
	 * @throws Exception: En caso de que no encuentre ningún usuario con la GoogleID indicada.
	 */
	public User getUserbyGoogleId(String googleId) throws Exception;
	
	/**
	 * Funcion que busca y devuelve un objeto User a partir de su email, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el email del User
	 * @return Devuelve el usuario buscado y el estado de la petición.
	 * @throws Exception: En caso de que no encuentre ningún usuario con el email indicada.
	 */
	public User getUserbyEmail(String email) throws Exception;
	
	/**
	 * Funcion del objeto Mock que introduce o sobreescribe un usuario.
	 * @param user Usuario el cual se quiere introducir en la BD.
	 * @throws Exception: Nos devuelve el error que ha ocurrido.
	 */
	public void putUser(User user) throws Exception;
	
	/**
	 * Funcion que elimina un usuario de la base de datos
	 * @param id: Identificador del usuario que se debe eliminar
	 * @throws Exception: En caso que no encuentre ningun usuario con la ID indicada.
	 */
	public void deleteUser(String id) throws Exception;
	
	/**
	 * Funcion del objeto Mock que retorna un grupo a partir de su ID.
	 * @param id Identificador del grupo que buscamos
	 * @return Retorna el grupo segun el identificador de grupo.
	 * @throws Exception: En caso de que no encuentre ningún grupo con la ID indicada.
	 */
	public Group getGroup(String id) throws Exception;
	
	/**
	 * Funcion del objeto Mock que introduce o sobreescribe un grupo.
	 * @param group Grupo que queremos introducir a la BD.
	 * @throws Exception: En caso de que no encuentre el grupo.
	 */
	public void putGroup(Group group) throws Exception;
	
	/**
	 * Funcion que elimina un grupo de la base de datos
	 * @param id Identificador del grupo que buscamos
	 * @throws Exception: En caso de que no encuentre ningún grupo con la ID indicada.
	 */
	public void deleteGroup(String id) throws Exception;
	
	

}
