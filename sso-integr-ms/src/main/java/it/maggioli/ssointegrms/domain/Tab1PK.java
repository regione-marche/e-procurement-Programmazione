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
public class Tab1PK implements Serializable {

	private static final long serialVersionUID = 3369690915882233532L;

	@NotBlank
	@Size(min = 1, max = 5)
	@Column(name = "tab1cod")
	private String tab1cod;

	@NotBlank
	@Column(name = "tab1tip")
	private Long tab1tip;

	public Tab1PK() {
	}

	public Tab1PK(@NotBlank @Size(min = 1, max = 5) String tab1cod, @NotBlank Long tab1tip) {
		this.tab1cod = tab1cod;
		this.tab1tip = tab1tip;
	}

	public String getTab1cod() {
		return tab1cod;
	}

	public void setTab1cod(String tab1cod) {
		this.tab1cod = tab1cod;
	}

	public Long getTab1tip() {
		return tab1tip;
	}

	public void setTab1tip(Long tab1tip) {
		this.tab1tip = tab1tip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tab1cod, tab1tip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tab1PK other = (Tab1PK) obj;
		return Objects.equals(tab1cod, other.tab1cod) && Objects.equals(tab1tip, other.tab1tip);
	}

}
