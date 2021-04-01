package mail.system;

import java.util.List;

public class EmailData {

	/**
	 * to -> marresin/marresit e email, bcc, cc, subject, content, date and time
	 */

	private Email sender;
	private List<Email> to;
	private List<Email> bcc;
	private List<Email> cc;
	private String subject;
	private String content;
	private String date;

	/*
	 * Constructor using all fields and the other one with single recipient ;
	 */

	public EmailData(Email sender, List<Email> to, List<Email> bcc, List<Email> cc, String subject, String content,
			String date) {

		this.sender = sender;
		this.to = to;
		this.bcc = bcc;
		this.cc = cc;
		this.subject = subject;
		this.content = content;
		this.date = date;

	}

	public EmailData(Email sender, List<Email> to, String subject, String content, String date) {

		this.sender = sender;
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.date = date;

	}

	/*
	 * We only need getters because we cannot change the content of email after it
	 * is sent
	 */

	public List<Email> getTo() {
		return to;
	}

	public List<Email> getBcc() {
		return bcc;
	}

	public List<Email> getCc() {
		return cc;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		return date;
	}

	public Email getSender() {
		return sender;
	}

}
