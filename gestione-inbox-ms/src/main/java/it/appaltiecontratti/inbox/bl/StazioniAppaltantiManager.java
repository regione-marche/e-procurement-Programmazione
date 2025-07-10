package it.appaltiecontratti.inbox.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.inbox.entity.DatiGeneraliStazioneAppaltanteEntry;
import it.appaltiecontratti.inbox.entity.SAEntry;
import it.appaltiecontratti.inbox.mapper.StazioniAppaltantiMapper;

@Component(value = "stazioniAppaltantiManager")
public class StazioniAppaltantiManager {
	
	@Autowired
	StazioniAppaltantiMapper stazioniAppaltantiMapper;

	public DatiGeneraliStazioneAppaltanteEntry valorizzaStazioneAppaltante(String codein) {

		DatiGeneraliStazioneAppaltanteEntry risultato = new DatiGeneraliStazioneAppaltanteEntry();
		SAEntry stazioneAppaltante = stazioniAppaltantiMapper.getSAInfo(codein);
		risultato.setCap(stazioneAppaltante.getCap());
		risultato.setCfAnac(stazioneAppaltante.getCfAnac());
		risultato.setCivico(stazioneAppaltante.getNumCivico());
		risultato.setCodiceFiscale(stazioneAppaltante.getCodFiscale());
		risultato.setCodiceIstat(stazioneAppaltante.getCodistat());
		risultato.setDenominazione(stazioneAppaltante.getNome());
		risultato.setEmail(stazioneAppaltante.getEmail());
		risultato.setFax(stazioneAppaltante.getFax());
		risultato.setIndirizzo(stazioneAppaltante.getIndirizzo());
		risultato.setLocalita(stazioneAppaltante.getComune());
		risultato.setProvincia(stazioneAppaltante.getProvincia());
		risultato.setTelefono(stazioneAppaltante.getTelefono());
		risultato.setPec(stazioneAppaltante.getPec());
		risultato.setIscuc(stazioneAppaltante.getIscuc());
		return risultato;
		
	}
	
	

}
