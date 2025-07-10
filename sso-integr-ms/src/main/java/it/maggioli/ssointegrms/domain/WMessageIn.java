package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_message_in")
public class WMessageIn implements Serializable {

	private static final long serialVersionUID = -8383365557983161936L;

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

	@Column(name = "message_recipient_syscon")
	private Long sysconRecipient;

	@Column(name = "message_recipient_read")
	private Integer read;

	@Column(name = "message_recipient_archive")
	private Integer archive;

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

	public Long getSysconRecipient() {
		return sysconRecipient;
	}

	public void setSysconRecipient(Long sysconRecipient) {
		this.sysconRecipient = sysconRecipient;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getArchive() {
		return archive;
	}

	public void setArchive(Integer archive) {
		this.archive = archive;
	}

}
