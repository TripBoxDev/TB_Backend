package com.tripbox.services.interfaces;

import java.util.ArrayList;

/**
 * Servicio de mailing con multiples funciones.
 * @author santi
 *
 */
public interface EmailService {
	
	/**
	 * Función que permite enviar un email de invitación a un Group.
	 * @param invitationURL URL para poder unirse al Group
	 * @param emails Conjunto de emails destinatarios
	 * @throws Exception
	 */
	public void sendInvitation(String invitationURL, ArrayList<String> emails) throws Exception;

}
