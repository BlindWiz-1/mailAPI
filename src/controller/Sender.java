package controller;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mail.system.Email;
import mail.system.EmailAuthenticator;
import mail.system.EmailSender;

public class Sender implements EmailSender, EmailAuthenticator {

	private Session session;
	private String from;
	private String pass;
	private Properties properties;

	public Sender(String from, String pass) {

		this.properties = new Properties();

		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		});

		this.from = from;
		this.pass = pass;
	}

	public void send(String to, String subject, String msg) throws AddressException, MessagingException {

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(from));
		message.setSubject(subject);
		message.setText(msg);

		message.setRecipients(RecipientType.TO, InternetAddress.parse(to));

		Transport.send(message);

	}

	@Override
	public void send(Email to, String subject, String content) {

		try {
			this.send(to.getEmail(), subject, content);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void send(List<Email> to, String subject, String content) {

		for (Email e : to) {
			this.send(e, subject, content);
		}

	}

	@Override
	public void send(List<Email> to, List<Email> bcc, String subject, String content) {

		this.send(to, subject, content);

		for (Email e : bcc) {
			this.send(e, subject, content);
		}

	}

	@Override
	public void send(List<Email> to, List<Email> bcc, List<Email> cc, String subject, String content) {

		this.send(to, bcc, subject, content);

		for (Email e : cc) {
			this.send(e, subject, content);
		}

	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.from;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.pass;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return this.properties;
	}

}
