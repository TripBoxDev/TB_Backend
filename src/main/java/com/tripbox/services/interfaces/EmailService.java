package com.tripbox.services.interfaces;

import java.util.ArrayList;

/**
 * Servicio de mailing con multiples funciones.
 * @author santi
 *
 */
public interface EmailService {
	
	/**
	 * Funcion que permite enviar un email de invitacion a un Group, añadiendo el URL al mail y 
	 * enviandolo a los mails de la lista.
	 * @param invitationURL URL para poder unirse al Group, el cual se añade al mensaje de invitacion.
	 * @param emails Conjunto de emails destinatarios.
	 * @throws Exception: Nos devuelve el error que ha ocurrido.
	 */
	public void sendInvitation(String invitationURL, ArrayList<String> emails) throws Exception;

}
