package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mail.system.Email;
import mail.system.IllegalEmailException;

public class Setup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector connectorDB = new Connector();
		try {

			connectorDB.executeUpdate("CREATE SCHEMA IF NOT EXISTS emails;");
			connectorDB.createEmailsTable();
			connectorDB.createEmailsContentTable();
			connectorDB.addANewEmail("admin", "admin", new Email("emailsender6321@gmail.com"));
			
			List<Email> to = new ArrayList<Email>();
			to.add(new Email("drabeli2000@gmail.com"));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now(); 
			String date = dtf.format(now);
			
			connectorDB.addNewContent(new Email("emailsender6321@gmail.com"), to, to, to, "Hello", "Helloo!!", date);

		} catch (SQLException | IllegalEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
