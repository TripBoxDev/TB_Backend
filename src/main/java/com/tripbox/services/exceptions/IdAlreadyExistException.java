package com.tripbox.services.exceptions;

/**
 * Excepción utilizada para indicar qu la id generada por la clase IdGenerator ya existe en la bbdd.
 * @author santi
 *
 */
@SuppressWarnings("serial")
public class IdAlreadyExistException  extends Exception{
	
	public IdAlreadyExistException(){
		super();
	}

}
