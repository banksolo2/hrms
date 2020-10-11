package com.cust.hrms.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class HrmsEmail {
	private HrmsAccount ha = new HrmsAccount();
	public void sendEmail(String recepient) {
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
		Message message = prepareMessage(session, ha.getUserName(), recepient);
		try {
			Transport.send(message);
			System.out.println("Email message sent.....");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Message prepareMessage(Session session, String emailAccount, String recepient){
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("My first email from java");
			message.setText("Hi Dear, \n Jesus love you.");
			return message;
		}
		catch(Exception ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return null;
	} 
}
