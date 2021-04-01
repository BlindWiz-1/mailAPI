package mail.system;

import java.sql.ResultSet;
import java.util.List;

public interface DBOperator {

	void executeUpdate(String query);
	ResultSet executeQuery(String query);
	
	List<EmailData> getEmailsSendTo(Email to);
	List<EmailData> getEmails();
	boolean checkEmailAddress(String username, String password);
	
}
