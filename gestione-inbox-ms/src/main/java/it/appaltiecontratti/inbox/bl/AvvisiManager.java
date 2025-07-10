package it.appaltiecontratti.inbox.bl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.inbox.entity.avvisi.AllegatoAvvisiEntry;
import it.appaltiecontratti.inbox.entity.avvisi.AvvisoEntry;
import it.appaltiecontratti.inbox.entity.avvisi.DocAvvisoEntry;
import it.appaltiecontratti.inbox.entity.avvisi.PubblicaAvvisoEntry;
import it.appaltiecontratti.inbox.entity.contratti.DatiGeneraliTecnicoEntry;
import it.appaltiecontratti.inbox.entity.contratti.RupEntry;
import it.appaltiecontratti.inbox.mapper.AvvisiMapper;

@Component(value = "avvisiManager")
public class AvvisiManager {

	@Autowired
	private AvvisiMapper avvisiMapper;

	public PubblicaAvvisoEntry valorizzaAvviso(String codein, Long idAvviso, final String fileAllegatoAvvisiUrl) {

		PubblicaAvvisoEntry risultato = new PubblicaAvvisoEntry();
		AvvisoEntry avviso = avvisiMapper.getAvviso(idAvviso, codein);
		List<DocAvvisoEntry> docs = avvisiMapper.getdocAvviso(idAvviso, codein);
		if (avviso == null)
			return null;
		Long tipoAvviso = avviso.getTipoAvviso();
		if(tipoAvviso != null && tipoAvviso > 20L) {
			tipoAvviso = 6L;
		}
		String cfSa = avvisiMapper.getCFSAByCode(codein);
		risultato.setCodiceFiscaleSA(cfSa);
		risultato.setTipologia(tipoAvviso);
		risultato.setData(avviso.getDataAvviso());
		risultato.setDescrizione(avviso.getDescrizione());
		risultato.setCig(avviso.getCig());
		risultato.setCup(avviso.getCup());
		risultato.setCui(avviso.getCui());
		risultato.setScadenza(avviso.getDataScadenza());
		risultato.setIdRicevuto(avviso.getIdRicevuto());
		RupEntry rup = avvisiMapper.getTecnico(avviso.getRup());
		if (rup != null) {
			risultato.setRup(mappingRup(rup));
		}
		List<AllegatoAvvisiEntry> documenti = new ArrayList<AllegatoAvvisiEntry>();

		for (DocAvvisoEntry doc : docs) {
			AllegatoAvvisiEntry allegato = new AllegatoAvvisiEntry();
			if (StringUtils.isNotBlank(doc.getUrl())) {
				allegato.setUrl(doc.getUrl());
			} else {
				String replacedFileAllegatoAvvisiUrl = new String(fileAllegatoAvvisiUrl);
				replacedFileAllegatoAvvisiUrl = replacedFileAllegatoAvvisiUrl
						.replace("<IDAVVISO>", String.valueOf(idAvviso)).replace("<CODEIN>", codein)
						.replace("<NUMDOC>", String.valueOf(doc.getNumdoc()));
				allegato.setUrl(replacedFileAllegatoAvvisiUrl);
			}
			allegato.setTitolo(doc.getTitolo());
			documenti.add(allegato);
		}
		risultato.setDocumenti(documenti);
		return risultato;
	}

	private DatiGeneraliTecnicoEntry mappingRup(RupEntry rup) {
		DatiGeneraliTecnicoEntry risultato = new DatiGeneraliTecnicoEntry();
		risultato.setCap(rup.getCap());
		risultato.setCfPiva(rup.getCf());
		risultato.setCivico(rup.getNumCivico());
		risultato.setCodice(rup.getCodice());
		risultato.setCodiceSA(rup.getStazioneAppaltante());
		risultato.setCognome(rup.getCognome());
		risultato.setFax(rup.getFax());
		risultato.setIndirizzo(rup.getIndirizzo());
		risultato.setLocalita(rup.getProvincia());
		risultato.setLuogoIstat(rup.getCodIstat());
		risultato.setMail(rup.getEmail());
		risultato.setNome(rup.getNome());
		risultato.setNomeCognome(rup.getNominativo());
		risultato.setProvincia(rup.getProvincia());
		risultato.setTelefono(rup.getTelefono());
		return risultato;
	}

	public void updateIdRicevuto(Long idRicevuto, String codein, Long numeroAvviso) {
		this.avvisiMapper.updateIdRicevutoAvviso(idRicevuto, codein, numeroAvviso);

	}

}