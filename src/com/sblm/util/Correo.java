package com.sblm.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {

	public void enviarCorreo(String mailDestino,String asunto, String mensaje) {
		System.out.println("mailDestino::"+mailDestino);
		System.out.println("asunto::"+asunto);
		System.out.println("mensaje::"+mensaje);
		
		try {
			Properties props = new Properties();

			props.setProperty("mail.smtp.host", "mail.sblm.gob.pe");
			props.setProperty("mail.smtp.starttls.enable", "false");
			props.setProperty("mail.smtp.port", "26");
			props.setProperty("mail.smtp.user", "sgi@sblm.gob.pe");
			props.setProperty("mail.smtp.auth", "false");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(
					"sgi@sblm.gob.pe"));
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(mailDestino));

			message.setSubject(asunto);

			message.setText(mensaje,"ISO-8859-1","html");

			Transport t = session.getTransport("smtp");

			t.connect("mail.sblm.gob.pe", "sgi@sblm.gob.pe",
					"123456");
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		} catch (Exception e) {
			System.out.println("error correo:::"+e.getMessage());
		}
		
	}
}
