package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Email;

public interface EmailREST {
	
	/**
	 * Función de la API que nos permite enviar una URL de invitación de unirse a un Group a un listado de emails.
	 * @param email
	 * @return
	 */
	public Response invitation(Email email);
}
