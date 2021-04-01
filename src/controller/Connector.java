package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mail.system.DBOperator;
import mail.system.DBType;
import mail.system.Email;
import mail.system.EmailData;
import mail.system.IllegalEmailException;
import mail.system.JDBCUtil;

public class Connector implements DBOperator {

	private JDBCUtil emailsDB;
	private Connection connection;

	public Connector() {
		this.emailsDB = new JDBCUtil("root", "root", "localhost", "emails", DBType.MY_SQL);
		this.connection = emailsDB.getConnection();
	}

	@Override
	public void executeUpdate(String query) {
		// TODO Auto-generated method stub
		try {
			Statement stm = connection.createStatement();
			stm.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet executeQuery(String query) {
		// TODO Auto-generated method stub

		try {
			Statement stm = connection.createStatement();
			return stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * Check again if checkEmailAddress username and password is a correct
	 * method!!!!
	 */
	@Override
	public boolean checkEmailAddress(String username, String password) {
		try {

			ResultSet results = this.executeQuery("SELECT username,password FROM emails WHERE username='" + username
					+ "' AND password='" + password + "';");

			if (results.isBeforeFirst()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public List<EmailData> getEmailsSendTo(Email to) {

		String receiver = to.getEmail();

		ResultSet results = this.executeQuery("SELECT * FROM emailContent WHERE emailTo like '%" + receiver
				+ "%' OR bcc like '%" + receiver + "%' OR cc like '%" + receiver + "%';");
		List<EmailData> result = new ArrayList<EmailData>();

		try {
			while (results.next()) {
				// Email sender, List<Email> to, List<Email> bcc, List<Email> cc, String
				// subject, String content, String date
				result.add(new EmailData(new Email(results.getString(1)), getAllEmailsFromString(results.getString(2)),
						getAllEmailsFromString(results.getString(3)), getAllEmailsFromString(results.getString(4)),
						results.getString(5), results.getString(7), results.getString(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalEmailException e) {
			e.printStackTrace();
		}
		return result;

	}

	public List<EmailData> getEmailsSendBy(Email by) {

		String receiver = by.getEmail();

		ResultSet results = this.executeQuery("SELECT * FROM emailContent WHERE sender like '%" + receiver + "%';");

		List<EmailData> result = new ArrayList<EmailData>();

		try {
			while (results.next()) {
				// Email sender, List<Email> to, List<Email> bcc, List<Email> cc, String
				// subject, String content, String date
				result.add(new EmailData(new Email(results.getString(1)), getAllEmailsFromString(results.getString(2)),
						getAllEmailsFromString(results.getString(3)), getAllEmailsFromString(results.getString(4)),
						results.getString(5), results.getString(6), results.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalEmailException e) {
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public List<EmailData> getEmails() {
		ResultSet results = this.executeQuery("SELECT * FROM emailContent;");
		List<EmailData> result = new ArrayList<EmailData>();

		try {
			while (results.next()) {

				result.add(new EmailData(new Email(results.getString(1)), getAllEmailsFromString(results.getString(2)),
						getAllEmailsFromString(results.getString(3)), getAllEmailsFromString(results.getString(4)),
						results.getString(5), results.getString(6), results.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalEmailException e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean checkUsername(String username) {
		try {

			ResultSet results = this.executeQuery("SELECT username FROM emails WHERE username='" + username + "';");

			if (results.isBeforeFirst()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * Emails Table
	 */

	public void createEmailsTable() throws SQLException {
		this.executeUpdate(
				"CREATE TABLE IF NOT EXISTS emails(username VARCHAR(100),password VARCHAR(256),email VARCHAR(100));");
	}

	public void addANewEmail(String username, String password, Email e) throws SQLException {

		if (!checkUsername(username)) {
			this.executeUpdate("INSERT INTO emails (username, password, email) VALUES ('" + username + "','" + password
					+ "','" + e.getEmail() + "');");
		}
	}

	// Will not work because there is no unique key. Triggers warnings. To be
	// updated
	public void deleteEmail(String username) {

		this.executeUpdate("DELETE FROM emails WHERE username='" + username + "';");
	}

	public Email getEmailGivenUsername(String username) throws NullPointerException {
		ResultSet results = this.executeQuery("SELECT email FROM emails WHERE username='" + username + "'");

		try {

			if (results.next()) {
				Email email = new Email(results.getString(1));
				return email;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalEmailException e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getUsernameGivenEmail(String email) throws NullPointerException {
		ResultSet results = this.executeQuery("SELECT username FROM emails WHERE email='" + email + "'");

		try {

			if (results.next()) {
				String username = results.getString(1);
				return username;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public String getPasswordGivenEmail(String email) throws NullPointerException {
		ResultSet results = this.executeQuery("SELECT password FROM emails WHERE email='" + email + "'");

		try {

			if (results.next()) {
				String password = results.getString(1);
				return password;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	/*
	 * Email Content Table
	 */

	public void createEmailsContentTable() throws SQLException {
		this.executeUpdate(
				"CREATE TABLE IF NOT EXISTS emailContent(sender VARCHAR(200), emailTo VARCHAR(500), bcc VARCHAR(500), cc VARCHAR(500), subject VARCHAR(450), message VARCHAR(1000), date VARCHAR(100));");
	}

	public void addNewContent(Email sender, List<Email> to, List<Email> bcc, List<Email> cc, String subject,
			String message, String date) throws SQLException {

		this.executeUpdate("INSERT INTO emailContent(sender, emailTo, bcc, cc, subject, message, date) VALUES ('"
				+ sender.getEmail() + "', '" + this.getEmailListAsString(to) + "', '" + this.getEmailListAsString(bcc)
				+ "', '" + this.getEmailListAsString(cc) + "', '" + subject + "', '" + message + "', '" + date + "');");
	}

	public void deleteContent(Email sender, String subject) {
		this.executeUpdate(
				"DELETE FROM emailContent WHERE sender='" + sender.getEmail() + "' AND subject='" + subject + "';");
	}

	/*
	 * Functions used to make the code simpler to read and debug.
	 */

	private String getEmailListAsString(List<Email> to) {

		String s = "";

		for (Email e : to) {

			s += e.getEmail() + ";";

		}
		return s;
	}

	public List<Email> getAllEmailsFromString(String string) throws IllegalEmailException {

		List<Email> emails = new ArrayList<Email>();

		String[] sArray = string.split(";");

		for (String s1 : sArray) {

			emails.add(new Email(s1));

		}

		return emails;
	}

}
