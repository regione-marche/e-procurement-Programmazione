package it.maggioli.ssointegrms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Cristiano Perin
 *
 */
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 915817758262156125L;

	private String done;
	private Object response;
	private List<String> messages = new ArrayList<>();

	public String getDone() {
		return done;
	}

	public void setDone(String done) {
		this.done = done;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
