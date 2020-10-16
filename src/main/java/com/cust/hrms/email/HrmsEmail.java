package com.cust.hrms.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class HrmsEmail {
	private final boolean IS_EMAIL_ENABLE = true;
	private final String SITE_URL = "http://localhost:8087/hrms/";
	
	private HrmsAccount ha = new HrmsAccount();
	public void sendEmail(String recepient[], String emailMessage, String subject) {
		System.out.println("preparing to send email.......");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		
		Session session = Session.getInstance(props, new Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ha.getUserName(), ha.getPassword());
			}
		});
		Message message = prepareMessage(session, ha.getUserName(), recepient, emailMessage, subject);
		try {
			Transport.send(message);
			System.out.println("Email message sent.....");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Message prepareMessage(Session session, String emailAccount, String recepient[], String emailMessage, String subject){
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailAccount));
			InternetAddress[] recipientAddress = new InternetAddress[recepient.length];
			int counter = 0;
			for (String recipient : recepient) {
			    recipientAddress[counter] = new InternetAddress(recipient.trim());
			    counter++;
			}
			message.setRecipients(Message.RecipientType.TO, recipientAddress);
			message.setSubject(subject);
			message.setContent(emailMessage, "text/html");
			return message;
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return null;
	} 
	
	public boolean isEmailEnable() {
		return IS_EMAIL_ENABLE;
	}
	
	public String getSiteUrl() {
		return SITE_URL;
	}
}
