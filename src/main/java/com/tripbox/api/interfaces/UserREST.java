package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.User;

public interface UserREST {
	
	/**
	 * Funcio de la API que retorna un usuari a partir de la seva ID.
	 * @param id: Identificador del'usuari que s'ha de retornar
	 * @return: Retorna l'usuari amb l'identificador d'entrada
	 */
	public Response getUser(String id);
	
	/**
	 * Funcio de la API que modifica un usuari a la BD o li assigna una ID i l'inserta si es nou.
	 * @param user: Objecte usuari que ha de ser inserit o modificat a la BD.
	 * @return: Retorna el user modificat, amb la nova ID si correspont, i una resposta 200 si s'ha fet l'operacio correctament.
	 */
	public Response putUser(User user);
	
	/**
	 * Funcio de la API que esborra un usuari a partir de la seva ID.
	 * @param id: Identificador de l'usuari que s'ha d'esborrar
	 */
	public void deleteUser(String id);

}
