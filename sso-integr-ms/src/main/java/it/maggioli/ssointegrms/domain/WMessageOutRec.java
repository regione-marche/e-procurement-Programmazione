package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_message_out_rec")
public class WMessageOutRec implements Serializable {

	private static final long serialVersionUID = 1838586721240401523L;

	@EmbeddedId
	private WMessageOutRecPK id;

	@Column(name = "recipient_syscon")
	private Long sysconRecipient;

	public WMessageOutRecPK getId() {
		return id;
	}

	public void setId(WMessageOutRecPK id) {
		this.id = id;
	}

	public Long getSysconRecipient() {
		return sysconRecipient;
	}

	public void setSysconRecipient(Long sysconRecipient) {
		this.sysconRecipient = sysconRecipient;
	};

}
