package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Embeddable
public class VTab4Tab6PK implements Serializable {

	private static final long serialVersionUID = 8842230555114852713L;

	@Column(name = "tab46cod")
	private String tab46Cod;
	@Column(name = "tab46tip")
	private String tab46Tip;

	public String getTab46Cod() {
		return tab46Cod;
	}

	public String getTab46Tip() {
		return tab46Tip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tab46Cod, tab46Tip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VTab4Tab6PK other = (VTab4Tab6PK) obj;
		return Objects.equals(tab46Cod, other.tab46Cod) && Objects.equals(tab46Tip, other.tab46Tip);
	}

}
