package com.tripbox.services;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.tripbox.elements.Email;
import com.tripbox.services.interfaces.EmailService;

public class EmailServiceImplTest {

	
	EmailService emailService;
	@Before
	public void setUp(){
		emailService = new EmailServiceImpl();
	}
	
	
	
	@Test
	public void sendInvitationTest() throws Exception {
		Email email = new Email();
		email.setInvitationUrl("https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-prn2/t1.0-9/s403x403/1509246_655343277854015_3223584984530361539_n.jpg");
		ArrayList<String> emails = new ArrayList<String>();
		
		//si algú ha de testejar sisplau que posi aqui el seu email ;) 
		emails.add("sete15santi@hotmail.com");
		
//		emails.add("jorge.francisco.ortiz@gmail.com");
//		emails.add("asanz91@gmail.com");
//		emails.add("krystyan.fs@gmail.com");
//		emails.add("amphiros@gmail.com");
//		emails.add("sr.guisado@gmail.com");
//		emails.add("correaroman@gmail.com");
//		emails.add("rubenpt91@gmail.com");
//		emails.add("alonso_gonzalo_@hotmail.com");
//		emails.add("santi.munozm@gmail.com");

		email.setEmails(emails);
		emailService.sendInvitation(email.getInvitationUrl(), email.getEmails());
			
		
	}

}
