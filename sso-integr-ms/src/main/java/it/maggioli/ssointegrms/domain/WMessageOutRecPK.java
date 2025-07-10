package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Embeddable
public class WMessageOutRecPK implements Serializable {

	private static final long serialVersionUID = -5763701044211935984L;

	@NotNull
	@Column(name = "recipient_id")
	private Long recipientId;

	@NotNull
	@Column(name = "message_id")
	private Long messageId;

	public WMessageOutRecPK() {
	}

	public WMessageOutRecPK(@NotNull Long recipientId, @NotNull Long messageId) {
		this.recipientId = recipientId;
		this.messageId = messageId;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(messageId, recipientId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WMessageOutRecPK other = (WMessageOutRecPK) obj;
		return Objects.equals(messageId, other.messageId) && Objects.equals(recipientId, other.recipientId);
	}

}
