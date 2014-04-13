package com.tripbox.bbdd.interfaces;

import com.tripbox.bbdd.exceptions.ItemNotFoundException;
import com.tripbox.elements.Group;
import com.tripbox.elements.User;

public interface Querys {
	
	/**
	 * Funcio de l'objecte Mock que retorna un usuari a partir de la seva ID.
	 * @param id Identificador de l'usuari que s'ha de retornar
	 * @return Retorna l'usuari amb l'identificador d'entrada
	 * @throws Exception: En cas que no trobi cap usuari amb la ID indicada.
	 */
	public User getUser(String id) throws Exception;
	
	/**
	 * Funci������n que busca y devuelve un objeto User a partir de su facebookId, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el facebookId del User
	 * @return User
	 * @throws Exception De tipo NotFound
	 */
	public User getUserbyFacebookId(String facebookId) throws ItemNotFoundException;
	
	/**
	 * Funci������n que busca y devuelve un objeto User a partir de su googleId, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el googleId del User
	 * @return User
	 * @throws Exception De tipo NotFound
	 */
	public User getUserbyGoogleId(String googleId) throws Exception;
	
	/**
	 * Funci������n que busca y devuelve un objeto User a partir de su email, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el email del User
	 * @return User
	 * @throws Exception De tipo NotFound
	 */
	public User getUserbyEmail(String email) throws Exception;
	
	/**
	 * Funcio de l'objecte Mock que introdueix o sobreescriu un usuari.
	 * @param user Usuari que ha de ser introduit a la BD.
	 * @throws Exception
	 */
	public void putUser(User user) throws Exception;
	
	/**
	 * Funci��n que elimina un usuario de la base de datos
	 * @param id: Identificador del usuario que sse debe eliminar
	 * @throws Exception: En caso que no encuentre ningun usuario con la ID indicada.
	 */
	public void deleteUser(String id) throws Exception;
	
	/**
	 * Funcio de l'objecte Mock que retorna un grup a partir de la seva ID.
	 * @param id Identificador del grup que s'ha de retornar
	 * @return Retorna el grup amb l'identificador d'entrada
	 * @throws Exception: En cas que no trobi cap grup amb la ID indicada.
	 */
	public Group getGroup(String id) throws Exception;
	
	/**
	 * Funcio de l'objecte Mock que introdueix o sobreescriu un grup.
	 * @param group Grup que ha de ser introduit a la BD.
	 * @throws Exception
	 */
	public void putGroup(Group group) throws Exception;
	
	/**
	 * Funci������n que elimina un grupo de la base de datos
	 * @param id
	 * @throws Exception De tipo NoExiste
	 */
	public void deleteGroup(String id) throws Exception;
	
	/**
	 * Funcion que añade un usuario a un grupo y un grupo a un usuario en la base de datos
	 * @param user: Usuario introducido en el grupo
	 * @param group: Grupo del cual formara parte el usuario
	 * @throws Exception: Si el grupo o el usuario no existen son nulos
	 */
	public void addGroupToUser(User user, Group group)throws Exception;

}
