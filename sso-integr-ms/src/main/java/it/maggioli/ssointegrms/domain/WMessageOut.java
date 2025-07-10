package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_message_out")
public class WMessageOut implements Serializable {

	private static final long serialVersionUID = -9010026927054271334L;

	@Id
	@NotNull
	@Column(name = "message_id")
	private Long id;

	@Column(name = "message_date")
	private Date date;

	@Column(name = "message_subject")
	private String subject;

	@Column(name = "message_body")
	private String body;

	@Column(name = "message_sender_syscon")
	private Long sysconSender;

	@Size(min = 0, max = 5)
	@Column(name = "message_recipient_type")
	private String recipientType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getSysconSender() {
		return sysconSender;
	}

	public void setSysconSender(Long sysconSender) {
		this.sysconSender = sysconSender;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

}
