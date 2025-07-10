package it.maggioli.ssointegrms.exceptions.general;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = -8059035182745022343L;

	private List<String> messages = new ArrayList<>();

	public InternalServerException() {
		super();
	}

	public InternalServerException(final String message) {
		super(message);
	}

	public InternalServerException(final Throwable t) {
		super(t);
	}

	public InternalServerException(final String message, final Throwable t) {
		super(message, t);
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
