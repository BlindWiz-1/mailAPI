package mail.system;

import java.util.List;

public interface EmailSender {

	void send(Email to, String subject, String content);

	void send(List<Email> to, String subject, String content);

	void send(List<Email> to, List<Email> bcc, String subject, String content);

	void send(List<Email> to, List<Email> bcc, List<Email> cc, String subject, String content);

}
