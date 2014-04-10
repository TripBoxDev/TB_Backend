package com.tripbox.services.interfaces;

import java.util.ArrayList;


public interface EmailService {
	
	public void sendInvitation(String invitationURL, ArrayList<String> emails) throws Exception;

}
