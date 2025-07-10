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
public class Tab5PK implements Serializable {

	private static final long serialVersionUID = -3170182866882685713L;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab5cod")
	private String tab5cod;

	@NotBlank
	@Size(min = 1, max = 15)
	@Column(name = "tab5tip")
	private String tab5tip;

	public Tab5PK() {
	}

	public Tab5PK(@NotBlank @Size(min = 1, max = 5) String tab5cod, @NotBlank @Size(min = 1, max = 15) String tab5tip) {
		this.tab5cod = tab5cod;
		this.tab5tip = tab5tip;
	}

	public String getTab5cod() {
		return tab5cod;
	}

	public void setTab5cod(String tab5cod) {
		this.tab5cod = tab5cod;
	}

	public String getTab5tip() {
		return tab5tip;
	}

	public void setTab5tip(String tab5tip) {
		this.tab5tip = tab5tip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tab5cod, tab5tip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tab5PK other = (Tab5PK) obj;
		return Objects.equals(tab5cod, other.tab5cod) && Objects.equals(tab5tip, other.tab5tip);
	}

}
