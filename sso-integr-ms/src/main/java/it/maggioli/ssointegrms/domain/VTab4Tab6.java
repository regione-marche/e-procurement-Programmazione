package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Immutable
@Table(name = "v_tab4_tab6")
public class VTab4Tab6 implements Serializable {

	private static final long serialVersionUID = 4378724434872471831L;

	@EmbeddedId
	private VTab4Tab6PK id;

	@Column(name = "tab46desc")
	private String tab46Desc;

	public VTab4Tab6PK getId() {
		return id;
	}

	public String getTab46Desc() {
		return tab46Desc;
	}

}
