package com.tripbox.api.interfaces;

import javax.ws.rs.core.Response;

import com.tripbox.elements.Email;

public interface EmailREST {
	
	/**
	 * Funcion de la API que nos permite enviar una URL de invitacion de unirse a un Group a un listado de emails.
	 * @param email Objeto de tipo Email que contiene la URL de inivitacion y la lista de mails donde enviarla.
	 * @return Devuelve una respuesta de estado.
	 */
	public Response invitation(Email email);
}
