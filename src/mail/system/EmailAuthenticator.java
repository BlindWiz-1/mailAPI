package mail.system;

import java.util.Properties;

public interface EmailAuthenticator {
	
	String getUsername();
	String getPassword();	
	
		/**
		 * ruajtja ne db e password te behet
		 * e kriptuar
		 * */
	
	Properties getProperties();

}
