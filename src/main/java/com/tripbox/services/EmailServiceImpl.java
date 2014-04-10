package com.tripbox.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.sun.mail.smtp.SMTPTransport;
import com.tripbox.services.interfaces.EmailService;

public class EmailServiceImpl implements EmailService {

	private static String SERVER_TYPE = "gmail";
	private static String EMAIL_SUBJECT = "Tripbox: You are invited to join a group";

	
	public void sendInvitation(String invitationURL, ArrayList<String> emails) throws Exception {
		try{
		//objeto donde almacenamos la configuración para conectarnos al servidor
        Properties properties = new Properties();
        //cargamos el archivo de configuracion
        properties.load(getClass().getResourceAsStream("/com/tripbox/emailproperties/"+SERVER_TYPE+".properties"));
        
        for(String email:emails){
		        //creamos una sesión
		        Session session = Session.getInstance(properties, null);
		        //creamos el message a enviar
		        Message message = new MimeMessage(session);
		        //agregamos la dirección que envía el email
		        message.setFrom(new InternetAddress(properties.getProperty("mail.from")));

                //agregamos las direcciones de email que reciben el email, en el primer parametro envíamos el tipo de receptor
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //Message.RecipientType.TO;  para
                //Message.RecipientType.CC;  con copia
                //Message.RecipientType.BCC; con copia oculta
                
                message.setSubject(EMAIL_SUBJECT);
                //agregamos una fecha al email
                message.setSentDate(new Date());

               String htmlPage=makeHtmlSource(invitationURL);
                message.setContent(htmlPage, "text/html; charset=utf-8");
                SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
                try {
                    //conectar al servidor
                    transport.connect(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
                    //enviar el message
                    transport.sendMessage(message, message.getAllRecipients());
                } finally {
                    //cerrar la conexión
                    transport.close();
                }
            
        	}
		}catch(Exception ex){
            throw new Exception();
        }
        
    }
	

	private String makeHtmlSource(String invitationURL){
		String hmtlCode="<img align='middle' style='display: block;margin-left: auto;margin-right: auto;width:150px;height:211px;' src='http://tripbox.uab.cat/resources/logo.png'><br><h3 style='text-align:center;'><b >Accept the invitation by clicking <a href='"+invitationURL+"'> this link</a></b></h3>";
		return hmtlCode;
	}

}


