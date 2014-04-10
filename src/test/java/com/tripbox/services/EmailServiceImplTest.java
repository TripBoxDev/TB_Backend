package com.tripbox.services;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


import com.tripbox.services.interfaces.EmailService;

public class EmailServiceImplTest {

	
	EmailService emailService;
	@Before
	public void setUp(){
		emailService = new EmailServiceImpl();
	}
	
	
	
	@Test
	public void sendInvitationTest() throws Exception {

		String urlInvitation = "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-prn2/t1.0-9/s403x403/1509246_655343277854015_3223584984530361539_n.jpg";
		ArrayList<String> emails = new ArrayList<String>();
		emails.add("santi.munozm@gmail.com");
//		emails.add("jorge.francisco.ortiz@gmail.com");
//		emails.add("asanz91@gmail.com");
//		emails.add("krystyan.fs@gmail.com");
//		emails.add("amphiros@gmail.com");
//		emails.add("sr.guisado@gmail.com");
//		emails.add("correaroman@gmail.com");
//		emails.add("rubenpt91@gmail.com");
//		emails.add("alonso_gonzalo_@hotmail.com");

		
		emailService.sendInvitation(urlInvitation, emails);
			
		
	}

}
