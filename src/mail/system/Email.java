package mail.system;

import java.util.ArrayList;
 
import mail.system.IllegalEmailException;;

public class Email {

	/**
	 * local, domain (mund te shtohen te tjere detaje)
	 */
	private String local, domain;

	private static ArrayList<Email> emails = new ArrayList<Email>();
	
	public Email(String local, String domain) throws mail.system.IllegalEmailException {
		/**
		 * if(!isValid(local + "@" + domain)) { invalidEmailInitial(); } else {
		 * this.local = local; this.domain = domain; emails.add(this); }
		 */
		this(local + "@" + domain);
	}

	public Email(String email) throws IllegalEmailException {
		if (!isValid(email)) {
			throw new IllegalEmailException("Email i papranueshem.", email);
		}
		int index = email.indexOf("@");
		local = email.substring(0, index).toLowerCase();
		domain = email.substring(index + 1).toLowerCase();
		emails.add(this);
	}

//	private void invalidEmailInitial() {
//		local = "undefined";
//		domain = "undefined";
//		System.out.println("Duplicated undefined@undefined or invalid!");
//	}

	public static boolean isValid(String email) {
		return email.contains("@");
	}

	public String getLocal() {
		return local;
	}

	public String getDomain() {
		return domain;
	}

	public String getEmail() {
		return local + "@" + domain;
	}

	public String toString() {
		return "[local=" + local + ", domain=" + domain + "]";
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Email)) {
			return false;
		}
		Email otherObj = (Email) obj;
		if (local == null) {
			if (otherObj.local != null) {
				return false;
			}
		} else if (!local.equals(otherObj.local)) {
			return false;
		}
		// kjo strukture per cdo rast kur atributi nuk eshte vlere primitive
		if (domain == null) {
			if (otherObj.domain != null) {
				return false;
			}
		} else if (!domain.equals(otherObj.domain)) {
			return false;
		}
		return true;
	}

	/// metoda clone e klases object
	/// protected
	/// mund ti bejme override
	/// ti zgjerojme dukshmerine

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
