package com.tripbox.services.interfaces;

import com.tripbox.elements.User;

public interface UserService {
	
	/**
	 * Funcio que retorna un usuari a partir de la seva ID.
	 * @param id Identificador del'usuari que s'ha de retornar
	 * @return Retorna l'usuari amb l'identificador d'entrada
	 * @throws Exception: Exception: En cas que no es trobi cap usuari amb la ID indicada.
	 */
	public User getUser(String id) throws Exception;
	
	/**
	 * Funcio de la API que modifica un usuari a la BD o li assigna una ID i l'inserta si es nou.
	 * @param user Objecte usuari que ha de ser inserit o modificat a la BD.
	 * @return Retorna una resposta 200 si s'ha fet l'operacio correctament.
	 * @throws Exception
	 */
	public User putUser(User user) throws Exception;
	
	/**
	 * Funcio de la API que esborra un usuari a partir de la seva ID.
	 * @param id Identificador de l'usuari que s'ha d'esborrar
	 * @throws Exception: Si no es troba cap usuari amb la ID indicada.
	 */
	public void deleteUser(String id)throws Exception;
}
