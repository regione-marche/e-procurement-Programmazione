package it.maggioli.ssointegrms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Entity
@Table(name = "w_accgrp")
public class WAccgrp implements Serializable {

	private static final long serialVersionUID = -1013246873648111960L;

	@EmbeddedId
	private WAccgrpPK id;

	@NotNull
	@Column(name = "priorita")
	private Integer priorita;

	public WAccgrpPK getId() {
		return id;
	}

	public void setId(WAccgrpPK id) {
		this.id = id;
	}

	public Integer getPriorita() {
		return priorita;
	}

	public void setPriorita(Integer priorita) {
		this.priorita = priorita;
	}

	@Override
	public String toString() {
		return "WAccgrp [id=" + id + ", priorita=" + priorita + "]";
	}

}
