package mail.system;

public class IllegalEmailException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email; 
	
	public IllegalEmailException(String msg, String email) {
		super(msg);
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
}
