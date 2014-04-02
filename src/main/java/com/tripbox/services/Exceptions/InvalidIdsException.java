package com.tripbox.services.Exceptions;

/**
 * Excepción utilizada para indicar que el elemento que ha recibido el servidor no tiene ningún identificador definido o válido.
 * @author santi
 *
 */
@SuppressWarnings("serial")
public class InvalidIdsException extends Exception {

	public InvalidIdsException(String msg){
		super(msg);
	}

}
