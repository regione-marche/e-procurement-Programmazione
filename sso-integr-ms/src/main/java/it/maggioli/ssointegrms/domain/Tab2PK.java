package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Embeddable
public class Tab2PK implements Serializable {

	private static final long serialVersionUID = -5841659432353256490L;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab2cod")
	private String tab2cod;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab2tip")
	private String tab2tip;

	public Tab2PK() {
	}

	public Tab2PK(@NotBlank @Size(min = 1, max = 5) String tab2cod, @NotBlank @Size(min = 1, max = 5) String tab2tip) {
		this.tab2cod = tab2cod;
		this.tab2tip = tab2tip;
	}

	public String getTab2cod() {
		return tab2cod;
	}

	public void setTab2cod(String tab2cod) {
		this.tab2cod = tab2cod;
	}

	public String getTab2tip() {
		return tab2tip;
	}

	public void setTab2tip(String tab2tip) {
		this.tab2tip = tab2tip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tab2cod, tab2tip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tab2PK other = (Tab2PK) obj;
		return Objects.equals(tab2cod, other.tab2cod) && Objects.equals(tab2tip, other.tab2tip);
	}

}
