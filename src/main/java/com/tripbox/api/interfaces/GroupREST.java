package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Group;

public interface GroupREST {
	
	/**
	 * Funcio de la API que retorna un grup a partir de la seva ID.
	 * @param id: Identificador del grup que s'ha de retornar
	 * @return: Retorna el grup amb l'identificador d'entrada
	 */
	public Response getGroup(String id);
	
	/**
	 * Funcio de la API que modifica un grup a la BD o li assigna una ID i l'inserta si es nou.
	 * @param group: Objecte grup que ha de ser inserit o modificat a la BD.
	 * @return: Retorna una resposta 200 si s'ha fet l'operacio correctament.
	 */
	public Response putGroup(Group group);
	
	/**
	 * Funcio de la API que esborra un grup a partir de la seva ID.
	 * @param id: Identificador del grup que s'ha d'esborrar
	 */
	public void deleteGroup(String id);
}
