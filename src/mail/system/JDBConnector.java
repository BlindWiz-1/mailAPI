package mail.system;

import java.sql.Connection;

public interface JDBConnector {

	DBType getDBType();
	String getServer();
	String getDatabaseName();
	String getUsername();
	String getPassword();
	Connection getConnection();
	
}
