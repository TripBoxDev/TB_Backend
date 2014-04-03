package com.tripbox.services.exceptions;

/**
 * Exceptción utilitzada para indicar que un User especifico no existe dentro del campo users de un Group
 * @author santi
 *
 */
@SuppressWarnings("serial")
public class UserNotExistOnGroup extends Exception {

	

		public UserNotExistOnGroup(String msg){
			super("User "+msg+" not exist on this group.");
		}

	
}
