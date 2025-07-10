package it.appaltiecontratti.programmi.rl.dto;

import java.io.Serializable;
import java.util.List;

public class FornitureAcquistiDto implements Serializable {
    private Long id;
    private String responsabileNome;
    private String responsabileCognome;
    private String responsabileCdFiscale;
    private Integer anno;
    private String enteCdFiscale;
    private String stato;
    private DatiTrasmissioneDto datiTrasmissione;
    private List<AcquistoDto> listaAcquisti;
    private List<AcquistoNonRipropostoDto> listaAcquistiNonRiproposti;

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getResponsabileNome() { return responsabileNome; }
    public void setResponsabileNome(String responsabileNome) { this.responsabileNome = responsabileNome; }
    
    public String getResponsabileCognome() { return responsabileCognome; }
    public void setResponsabileCognome(String responsabileCognome) { this.responsabileCognome = responsabileCognome; }
    
    public String getResponsabileCdFiscale() { return responsabileCdFiscale; }
    public void setResponsabileCdFiscale(String responsabileCdFiscale) { this.responsabileCdFiscale = responsabileCdFiscale; }
    
    public Integer getAnno() { return anno; }
    public void setAnno(Integer anno) { this.anno = anno; }
    
    public String getEnteCdFiscale() { return enteCdFiscale; }
    public void setEnteCdFiscale(String enteCdFiscale) { this.enteCdFiscale = enteCdFiscale; }
    
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    
    public DatiTrasmissioneDto getDatiTrasmissione() { return datiTrasmissione; }
    public void setDatiTrasmissione(DatiTrasmissioneDto datiTrasmissione) { this.datiTrasmissione = datiTrasmissione; }
    
    public List<AcquistoDto> getListaAcquisti() { return listaAcquisti; }
    public void setListaAcquisti(List<AcquistoDto> listaAcquisti) { this.listaAcquisti = listaAcquisti; }
    
    public List<AcquistoNonRipropostoDto> getListaAcquistiNonRiproposti() { return listaAcquistiNonRiproposti; }
    public void setListaAcquistiNonRiproposti(List<AcquistoNonRipropostoDto> listaAcquistiNonRiproposti) {
        this.listaAcquistiNonRiproposti = listaAcquistiNonRiproposti;
    }

    @Override
    public String toString() {
        return "FornitureAcquistiDto{" +
                "id=" + id +
                ", responsabileNome='" + responsabileNome + '\'' +
                ", responsabileCognome='" + responsabileCognome + '\'' +
                ", responsabileCdFiscale='" + responsabileCdFiscale + '\'' +
                ", anno=" + anno +
                ", enteCdFiscale='" + enteCdFiscale + '\'' +
                ", stato='" + stato + '\'' +
                ", datiTrasmissione=" + datiTrasmissione +
                ", listaAcquisti=" + listaAcquisti +
                ", listaAcquistiNonRicompresi=" + listaAcquistiNonRiproposti +
                '}';
    }
}