package it.maggioli.ssointegrms.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Embeddable
public class GConfCodPK implements Serializable {

	private static final long serialVersionUID = -7285303438916657739L;

	@NotBlank
	@Size(min = 1, max = 20)
	@Column(name = "noment")
	private String nomEnt;

	@NotBlank
	@Size(min = 1, max = 20)
	@Column(name = "nomcam")
	private String nomCam;

	@NotNull
	@Column(name = "tipcam")
	private Long tipCam;

	public GConfCodPK() {
	}

	public GConfCodPK(@NotBlank @Size(min = 1, max = 20) String nomEnt,
					  @NotBlank @Size(min = 1, max = 20) String nomCam, @NotNull Long tipCam) {
		this.nomEnt = nomEnt;
		this.nomCam = nomCam;
		this.tipCam = tipCam;
	}

	public String getNomEnt() {
		return nomEnt;
	}

	public void setNomEnt(String nomEnt) {
		this.nomEnt = nomEnt;
	}

	public String getNomCam() {
		return nomCam;
	}

	public void setNomCam(String nomCam) {
		this.nomCam = nomCam;
	}

	public Long getTipCam() {
		return tipCam;
	}

	public void setTipCam(Long tipCam) {
		this.tipCam = tipCam;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nomCam, nomEnt, tipCam);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GConfCodPK other = (GConfCodPK) obj;
		return Objects.equals(nomCam, other.nomCam) && Objects.equals(nomEnt, other.nomEnt)
				&& Objects.equals(tipCam, other.tipCam);
	}

}
