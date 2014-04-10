package com.tripbox.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

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
        //creamos una sesión
        Session session = Session.getInstance(properties, null);
        //creamos el message a enviar
        Message message = new MimeMessage(session);
        //agregamos la dirección que envía el email
        message.setFrom(new InternetAddress(properties.getProperty("mail.from")));
		
        for(String email:emails){
           
            
                //agregamos las direcciones de email que reciben el email, en el primer parametro envíamos el tipo de receptor
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //Message.RecipientType.TO;  para
                //Message.RecipientType.CC;  con copia
                //Message.RecipientType.BCC; con copia oculta
                
                message.setSubject(EMAIL_SUBJECT);
                //agregamos una fecha al email
                message.setSentDate(new Date());
//                MimeBodyPart messageBodyPart = createBody(invitationURL);
//               
//                Multipart multipart = new MimeMultipart();
//                multipart.addBodyPart(messageBodyPart);
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
	
	private MimeBodyPart createBody (String invitationURL) throws MessagingException{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("<html><head></head><body><img align='middle' src='http://i.imgur.com/xPRvaZh.png' alt='Tripbox'></body></html>", "text/html");
		//messageBodyPart.setText("Accept the invitation by clicking this link: "+invitationURL);
		
		return messageBodyPart;
		
	}
	
	private String makeHtmlSource(String invitationURL){
		String firstPart="<html><head></head><body><img align='middle' src='http://i.imgur.com/xPRvaZh.png' alt='Tripbox'></body></html>";
		return firstPart;
	}

}


