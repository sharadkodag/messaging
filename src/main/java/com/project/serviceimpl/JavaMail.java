package com.project.serviceimpl;

import com.project.entity.User;
import com.sun.mail.pop3.POP3Store;
import com.vaadin.flow.server.VaadinSession;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class JavaMail {
//	public static void main(String[] args) {
//		String to = "kodagmultiservices@gmail.com";
//		new JavaMail().sendMail(to);
////		new JavaMail().receiveMail();
//
//	}

	public void sendMail(String to) {
		final String from = "sharadskodag@gmail.com";
		final String password = "obuecgatqeiaznzg";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			InternetAddress[] address = new InternetAddress[3];
//			address[0] = new InternetAddress("j.gupta@direction.biz");
//			address[1] = new InternetAddress("p.dasamane@direction.biz");
//			address[2] = new InternetAddress("kodagsharad99@gmail.com");
			message.addRecipients(Message.RecipientType.CC, to);
			message.setSubject("Facebook Account OTP");

			int otp = new Random().nextInt(999999);
			User user = (User)VaadinSession.getCurrent().getAttribute("user");
			System.out.println(user.toString());
			message.setText("Dear " + user.getFirstName() + " " + user.getLastName() + " Your OTP : " + otp);
			VaadinSession.getCurrent().setAttribute("otp",otp);

			Transport.send(message);
			System.out.println("Message sent successfully");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public void receiveMail() {
		String host = "pop.gmail.com";
		String mailStoreType = "pop3s";
		String username = "sharadskodag@gmail.com";
		String password = "iprttlxusrvazspf";

		try {
			// 1) get the session object
			Properties properties = new Properties();
			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			properties.put("mail.store.protocol", "pop3");
			Session emailSession = Session.getDefaultInstance(properties);

			// 2) create the POP3 store object and connect with the pop server
			POP3Store emailStore = (POP3Store) emailSession.getStore(mailStoreType);
			emailStore.connect(host, username, password);

			// 3) create the folder object and open it
			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// 4) retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
//			System.out.println(messages.length);
			for (int i = 0; i <2; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + message.getContent().toString());
			}
			
			System.out.println( );

			// 5) close the store and folder objects
			emailFolder.close(false);
			emailStore.close();
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
