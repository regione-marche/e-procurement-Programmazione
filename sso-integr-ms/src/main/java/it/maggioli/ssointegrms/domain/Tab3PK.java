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
public class Tab3PK implements Serializable {

	private static final long serialVersionUID = -2041728602166739887L;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab3cod")
	private String tab3cod;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab3tip")
	private String tab3tip;

	public Tab3PK() {
	}

	public Tab3PK(@NotBlank @Size(min = 1, max = 5) String tab3cod, @NotBlank @Size(min = 1, max = 5) String tab3tip) {
		this.tab3cod = tab3cod;
		this.tab3tip = tab3tip;
	}

	public String getTab3cod() {
		return tab3cod;
	}

	public void setTab3cod(String tab3cod) {
		this.tab3cod = tab3cod;
	}

	public String getTab3tip() {
		return tab3tip;
	}

	public void setTab3tip(String tab3tip) {
		this.tab3tip = tab3tip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tab3cod, tab3tip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tab3PK other = (Tab3PK) obj;
		return Objects.equals(tab3cod, other.tab3cod) && Objects.equals(tab3tip, other.tab3tip);
	}

}
