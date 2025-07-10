package it.maggioli.ssointegrms.exceptions.internalAuthentication;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ChangePasswordException extends RuntimeException {

	private static final long serialVersionUID = -8059035182745022343L;

	private List<String> messages = new ArrayList<>();

	public ChangePasswordException() {
		super();
	}

	public ChangePasswordException(final String message) {
		super(message);
	}

	public ChangePasswordException(final Throwable t) {
		super(t);
	}

	public ChangePasswordException(final String message, final Throwable t) {
		super(message, t);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
