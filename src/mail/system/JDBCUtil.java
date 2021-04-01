package mail.system;

import java.sql.Connection;
import java.sql.SQLException;
import com.mysql.cj.jdbc.*;

/*
 * Util Class for the creation and utilisation of database.
 * */

public class JDBCUtil implements JDBConnector {

	private String username;
	private String password;
	private String serverName;
	private String databaseName;
	private DBType d;
	private Connection connection;

	/*
	 * Constructor of object that creates this type of database
	 */

	public JDBCUtil(String username, String password, String serverName, String databaseName, DBType d) {

		this.username = username;
		this.password = password;
		this.serverName = serverName;
		this.databaseName = databaseName;
		this.d = d;

		if (d == DBType.MY_SQL) {
			
			try {

				MysqlDataSource dataSource;
				dataSource = new MysqlDataSource();
				dataSource.setUser(this.username);
				dataSource.setPassword(this.password);
				dataSource.setUseSSL(false);
				dataSource.setServerName(this.serverName);
				dataSource.setDatabaseName(this.databaseName);
				connection = dataSource.getConnection();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (d == DBType.SQL_SERVER) {

		} else if (d == DBType.ORACLE_SQL_SERVER) {

		}

	}

	@Override
	public DBType getDBType() {
		return this.d;
	}

	@Override
	public String getServer() {
		return this.serverName;
	}

	@Override
	public String getDatabaseName() {
		return this.databaseName;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}
}
