package com.tripbox.services.interfaces;

import com.tripbox.elements.Group;

public interface GroupService {
	
	/**
	 * Funcio que retorna un grup a partir de la seva ID.
	 * @param id: Identificador del grup que s'ha de retornar
	 * @return: Retorna el grup amb l'identificador d'entrada
	 * @throws Exception: En cas que no es trobi cap grup amb la ID indicada.
	 */
	public Group getGroup(String id) throws Exception;
	
	/**
	 * Funcio que modifica un grup a la BD o li assigna una ID i l'inserta si es nou.
	 * @param group: Objecte grup que ha de ser inserit o modificat a la BD.
	 * @return: Retorna una resposta 200 si s'ha fet l'operacio correctament.
	 * @throws Exception
	 */
	public Group putGroup(Group group) throws Exception;
	
	/**
	 * Funcio que esborra un grup a partir de la seva ID.
	 * @param id: Identificador del grup que s'ha d'esborrar
	 * @throws Exception: Si no es troba cap grup amb la ID indicada.
	 */
	public void deleteGroup(String id)throws Exception;
}
