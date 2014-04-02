package com.tripbox.bbdd.interfaces;

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
	 * Función que busca y devuelve un objeto User a partir de su facebookId, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el facebookId del User
	 * @return User
	 * @throws Exception De tipo NotFound
	 */
	public User getUserbyFacebookId(String facebookId) throws Exception;
	
	/**
	 * Función que busca y devuelve un objeto User a partir de su googleId, en caso de no ser encontrado retorna una excepcion.
	 * @param email String con el googleId del User
	 * @return User
	 * @throws Exception De tipo NotFound
	 */
	public User getUserbyGoogleId(String googleId) throws Exception;
	
	/**
	 * Función que busca y devuelve un objeto User a partir de su email, en caso de no ser encontrado retorna una excepcion.
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
	 * Función que elimina un grupo de la base de datos
	 * @param id
	 * @throws Exception De tipo NoExiste
	 */
	public void deleteGroup(String id) throws Exception;

}
