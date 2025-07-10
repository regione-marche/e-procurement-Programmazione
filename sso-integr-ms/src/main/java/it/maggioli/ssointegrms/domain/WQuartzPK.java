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
public class WQuartzPK implements Serializable {

	private static final long serialVersionUID = 4112346687170833426L;

	@NotBlank
	@Size(min = 1, max = 10)
	@Column(name = "codapp")
	private String codApp;

	@NotBlank
	@Size(min = 1, max = 200)
	@Column(name = "bean_id")
	private String beanId;

	public WQuartzPK() {
	}

	public WQuartzPK(@NotBlank @Size(min = 1, max = 10) String codApp,
					 @NotBlank @Size(min = 1, max = 200) String beanId) {
		this.codApp = codApp;
		this.beanId = beanId;
	}

	public String getCodApp() {
		return codApp;
	}

	public void setCodApp(String codApp) {
		this.codApp = codApp;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(beanId, codApp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WQuartzPK other = (WQuartzPK) obj;
		return Objects.equals(beanId, other.beanId) && Objects.equals(codApp, other.codApp);
	}

}
