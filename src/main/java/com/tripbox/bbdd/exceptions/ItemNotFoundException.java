package com.tripbox.bbdd.exceptions;

/**
 * Excepción utilizada para indicar que no se ha encontrado un objeto en la bbdd.
 * @author santi
 *
 */
@SuppressWarnings("serial")
public class ItemNotFoundException extends Exception {
	public ItemNotFoundException(String msg){
		super(msg);
	}
}
