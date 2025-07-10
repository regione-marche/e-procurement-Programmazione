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
public class Tab0PK implements Serializable {

	private static final long serialVersionUID = -4732326219029348290L;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab0cod")
	private String tab0cod;

	@NotBlank
	@Size(min = 1, max = 1)
	@Column(name = "tab0tip")
	private String tab0tip;

	public Tab0PK() {
	}

	public Tab0PK(@NotBlank @Size(min = 1, max = 5) String tab0cod, @NotBlank @Size(min = 1, max = 1) String tab0tip) {
		this.tab0cod = tab0cod;
		this.tab0tip = tab0tip;
	}

	public String getTab0cod() {
		return tab0cod;
	}

	public void setTab0cod(String tab0cod) {
		this.tab0cod = tab0cod;
	}

	public String getTab0tip() {
		return tab0tip;
	}

	public void setTab0tip(String tab0tip) {
		this.tab0tip = tab0tip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tab0cod, tab0tip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tab0PK other = (Tab0PK) obj;
		return Objects.equals(tab0cod, other.tab0cod) && Objects.equals(tab0tip, other.tab0tip);
	}

}
