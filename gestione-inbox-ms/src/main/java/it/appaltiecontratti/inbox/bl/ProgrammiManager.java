package it.appaltiecontratti.inbox.bl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.inbox.entity.contratti.RupEntry;
import it.appaltiecontratti.inbox.entity.programmi.AcquistoNonRipropostoPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.AcquistoPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.AltriDatiOperaIncompiutaPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.DatiGeneraliTecnicoPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.ImmobileEntry;
import it.appaltiecontratti.inbox.entity.programmi.ImmobilePubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.InterventoEntry;
import it.appaltiecontratti.inbox.entity.programmi.InterventoNonRipropostoEntry;
import it.appaltiecontratti.inbox.entity.programmi.InterventoNonRipropostoPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.InterventoPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.OperaIncompiutaEntry;
import it.appaltiecontratti.inbox.entity.programmi.OperaIncompiutaPubbEntry;
import it.appaltiecontratti.inbox.entity.programmi.ProgrammaEntry;
import it.appaltiecontratti.inbox.entity.programmi.PubblicaProgrammaFornitureServiziEntry;
import it.appaltiecontratti.inbox.entity.programmi.PubblicaProgrammaLavoriEntry;
import it.appaltiecontratti.inbox.mapper.ProgrammiMapper;

@Component(value = "programmiManager")
public class ProgrammiManager {

	@Autowired
	private ProgrammiMapper programmiMapper;

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(ProgrammiManager.class);

	public PubblicaProgrammaLavoriEntry valorizzaProgrammaLavori(Long contri) {
		try {

			ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
			String cfsa = this.programmiMapper.getCFSAByCode(programma.getStazioneappaltante());
			programma.setStazioneappaltante(cfsa);

			RupEntry rupProgramma = this.programmiMapper.getRup(programma.getResponsabile());
			programma.setRup(rupProgramma);

			List<InterventoEntry> interventi = this.programmiMapper.getInterventiProgramma(contri);
			List<InterventoNonRipropostoEntry> intyerventiNR = this.programmiMapper.getInterventiNRProgramma(contri);
			List<OperaIncompiutaEntry> opereIncompiute = this.programmiMapper.getOpereIncompiuteProgramma(contri);

			for (InterventoEntry intervento : interventi) {
				List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, intervento.getId(), null);
				intervento.setImmobili(immobili);
				RupEntry rupIntervento = this.programmiMapper.getRup(intervento.getCodiceRup());
				intervento.setRupEntry(rupIntervento);
			}

			for (OperaIncompiutaEntry opera : opereIncompiute) {
				List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, null, opera.getId());
				opera.setImmobili(immobili);
			}

			PubblicaProgrammaLavoriEntry pubblicazioneEntry = this.mappingForPubLavori(programma, interventi,
					intyerventiNR, opereIncompiute);
			return pubblicazioneEntry;

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la ricerca dei dati di pubblicazione: contri= " + contri);
			return null;
		}

	}

	private PubblicaProgrammaLavoriEntry mappingForPubLavori(ProgrammaEntry p, List<InterventoEntry> i,
			List<InterventoNonRipropostoEntry> inr, List<OperaIncompiutaEntry> o) {

		List<OperaIncompiutaPubbEntry> operePubb = mappingOpereForPubb(o);
		List<InterventoPubbEntry> intervPubb = mappingInterventiForPubb(i);
		List<InterventoNonRipropostoPubbEntry> intervNRPubb = mappingInterventiNRForPubb(inr);
		PubblicaProgrammaLavoriEntry programma = mappingProgrammaForPubb(p);
		programma.setInterventi(intervPubb);
		programma.setOpereIncompiute(operePubb);
		programma.setInterventiNonRiproposti(intervNRPubb);
		return programma;
	}

	private PubblicaProgrammaLavoriEntry mappingProgrammaForPubb(ProgrammaEntry p) {

		PubblicaProgrammaLavoriEntry risultato = new PubblicaProgrammaLavoriEntry();

		risultato.setAnno(p.getAnnoInizio());

		risultato.setClientId("clientId");
		risultato.setCodiceFiscaleSA(p.getStazioneappaltante());
		risultato.setCodiceReferente(p.getResponsabile());
		risultato.setCodiceSA(p.getStazioneappaltante());
		risultato.setContri(p.getId());
		risultato.setDataAdozione(p.getDataAtto());
		risultato.setDataApprovazione(p.getDataApprovazione());
		risultato.setDataPubblicazioneAdozione(p.getDataPubblicazioneAdo());
		risultato.setDataPubblicazioneApprovazione(p.getDataPubblicazioneAppr());
		risultato.setDescrizione(p.getDescrizioneBreve());
		risultato.setId(p.getIdProgramma());
		risultato.setIdRicevuto(p.getIdRicevuto());
		risultato.setIdUfficio(p.getIdUfficio());
		risultato.setNumeroAdozione(p.getNumeroAtto());
		risultato.setNumeroApprovazione(p.getNumeroApprovazione());
		if (p.getRup() != null) {
			risultato.setReferente(mappingRup(p.getRup()));
		}
		risultato.setSyscon(p.getSyscon());
		risultato.setTitoloAttoAdozione(p.getTitoloAdozione());
		risultato.setTitoloAttoApprovazione(p.getTitoloApprovazione());
		risultato.setUrlAttoAdozione(p.getUrlAdozione());
		risultato.setUrlAttoApprovazione(p.getUrlApprovazione());
		return risultato;
	}

	private List<OperaIncompiutaPubbEntry> mappingOpereForPubb(List<OperaIncompiutaEntry> o) {
		if (o != null) {
			List<OperaIncompiutaPubbEntry> risultato = new ArrayList<OperaIncompiutaPubbEntry>();
			for (OperaIncompiutaEntry opera : o) {
				OperaIncompiutaPubbEntry out = new OperaIncompiutaPubbEntry();

				out.setAmbito(opera.getAmbitoInt());
				out.setAnno(opera.getAnnoUltimoQe());
				out.setCausa(opera.getCausa());
				out.setCessione(opera.getCessione());
				// in opera incompiuta l'idProgramma e' contri
				out.setContri(opera.getIdProgramma());
				out.setCup(opera.getCup());
				out.setDemolizione(opera.getDemolizione());
				out.setDescrizione(opera.getDescrizione());
				out.setDestinazioneUso(opera.getDestinazioneUso());
				out.setDeterminazioneAmministrazione(opera.getDeterminazioni());
				out.setFruibile(opera.getFruibile());
				out.setImmobili(mappingImmobiliForPubb(opera.getImmobili()));
				out.setImportoAvanzamento(opera.getImportoSal());
				out.setImportoIntervento(opera.getImportoInt());
				out.setImportoLavori(opera.getImportoLav());
				out.setInfrastruttura(opera.getInfrastruttura());
				out.setNumoi(opera.getId());
				out.setOneri(opera.getOneriUlt());
				out.setOneriSito(opera.getOneriSito());
				out.setPercentualeAvanzamento(opera.getAvanzamento());
				out.setPrevistaVendita(opera.getVendita());
				out.setRidimensionato(opera.getUtilizzoRid());
				out.setStato(opera.getStatoReal());
				out.setAltriDati(mappingOperaIncompiutaAltriDatiForPubb(opera));
				out.setDiscontinuitaRete(opera.getDiscontinuitaRete());
				risultato.add(out);
			}
			return risultato;
		}
		return null;
	}

	private AltriDatiOperaIncompiutaPubbEntry mappingOperaIncompiutaAltriDatiForPubb(OperaIncompiutaEntry opera) {
		if (opera != null) {
			AltriDatiOperaIncompiutaPubbEntry risultato = new AltriDatiOperaIncompiutaPubbEntry();

			risultato.setCategoriaIntervento(opera.getCategoriaIntervento());
			risultato.setContri(opera.getIdProgramma());
			risultato.setCoperturaAltro(opera.getCopAltraPubblica());
			risultato.setCoperturaComunale(opera.getCopComunale());
			risultato.setCoperturaComunitaria(opera.getCopComunitaria());
			risultato.setCoperturaPrivata(opera.getCopPrivata());
			risultato.setCoperturaProvinciale(opera.getCopProvinciale());
			risultato.setCoperturaRegionale(opera.getCopRegionale());
			risultato.setCoperturaStatale(opera.getCopStatale());
			risultato.setCostoProgetto(opera.getCosto());
			risultato.setDimensione(opera.getDimensionamentoValore());
			risultato.setFinanzaDiProgetto(opera.getFinanzaProgetto());
			risultato.setFinanziamento(opera.getFinanziamento());
			risultato.setIstat(opera.getCodIstat());
			risultato.setNumoi(opera.getId());
			risultato.setNuts(opera.getCodNuts());
			risultato.setRequisitiApprovato(opera.getRequisitiProgetto());
			risultato.setRequisitiCapitolato(opera.getRequisitiCapitolato());
			risultato.setSponsorizzazione(opera.getSponsorizzazione());
			risultato.setTipologiaIntervento(opera.getTipologiaIntervento());
			risultato.setUnitaMisura(opera.getDimensionamentoUm());

			return risultato;
		}
		return null;
	}

	private List<ImmobilePubbEntry> mappingImmobiliForPubb(List<ImmobileEntry> immobili) {
		if (immobili != null) {
			List<ImmobilePubbEntry> risultato = new ArrayList<ImmobilePubbEntry>();
			for (ImmobileEntry imm : immobili) {
				ImmobilePubbEntry out = new ImmobilePubbEntry();

				out.setAlienati(imm.getAlienati());
				out.setConint(imm.getIdIntervento());
				out.setContri(imm.getIdProgramma());
				out.setCui(imm.getCui());
				out.setDescrizione(imm.getDescrizione());
				out.setImmobileDisponibile(imm.getDirittoGodimento());
				out.setInclusoProgrammaDismissione(imm.getProgDismiss());
				out.setIstat(imm.getCodIstat());
				out.setNumimm(imm.getId());
				out.setNumoi(imm.getIdOpera());
				out.setNuts(imm.getNuts());
				out.setTipoDisponibilita(imm.getTipoDisp());
//				out.setTipoProprieta();
				out.setTrasferimentoTitoloCorrispettivo(imm.getTrasfImmCorrisp());
				out.setValoreStimato1(imm.getValStimato1());
				out.setValoreStimato2(imm.getValStimato2());
				out.setValoreStimato3(imm.getValStimato3());
				out.setValoreStimatoSucc(imm.getValAnnoSucc());

				risultato.add(out);
			}
			return risultato;
		}
		return null;
	}

	private List<InterventoPubbEntry> mappingInterventiForPubb(List<InterventoEntry> interventi) {
		List<InterventoPubbEntry> risultato = new ArrayList<InterventoPubbEntry>();
		for (InterventoEntry i : interventi) {
			InterventoPubbEntry entry = new InterventoPubbEntry();

			entry.setAcquistoMaterialiRiciclati(i.getAcquistoMaterialiRiciclati());
			if (i.getAcqVerdi() != null) {
				entry.setAcquistoVerdi(i.getAcqVerdi());
			}
			entry.setAnno(i.getAnnoAvvio());
			entry.setCategoria(i.getCategoriaIntervento());
			entry.setCodiceInterno(i.getCodiceInterno());
			entry.setCodiceRup(i.getCodiceRup());
			entry.setCodiceSoggettoDelegato(i.getCodiceAusa());
			entry.setConformitaAmbientale(i.getVerificaConfAmbiente());
			entry.setConformitaUrbanistica(i.getVerificaConfUrbanistica());
			entry.setConint(i.getId());
			entry.setContri(i.getIdProgramma());
			entry.setCoperturaFinanziaria(i.getCoperturaFinanziaria());
			entry.setCpv(i.getCodCpv());
			entry.setCpvMatRic(i.getMrcpv());
			entry.setCpvVerdi(i.getAvcpv());
			entry.setCui(i.getNumeroCui());
			entry.setCup(i.getCodiceCup());
			entry.setDelega(i.getDelegaProcAff());
			entry.setDescrizione(i.getDescrizione());
			entry.setDirezioneGenerale(i.getDirezioneGenerale());
			entry.setDirigenteResponsabile(i.getDirigenteUfficio());
			entry.setEsenteCup(i.getEsenteCup());
			entry.setFinalita(i.getFinalitaIntervento());
			if (i.getImmobili() != null) {
				entry.setImmobili(mappingImmobiliForPubb(i.getImmobili()));
			}
			entry.setImportoIvaMatRic(i.getImportoIvaMr());
			entry.setImportoIvaVerdi(i.getImportoIvaAv());
			entry.setImportoNettoIvaMatRic(i.getImportoAlnettoIvaMr());
			entry.setImportoNettoIvaVerdi(i.getImportoAlnettoIvaMr());
			entry.setImportoTotMatRic(i.getImportoTotaleMr());
			entry.setImportoTotVerdi(i.getImportoTotaleAv());
			entry.setIstat(i.getCodIstatComune());
			entry.setLavoroComplesso(i.getLavoroComplesso());
			entry.setLottoFunzionale(i.getLottoFunzionale());
			entry.setMeseAvvioProcedura(i.getMeseAvvioProc());
			entry.setNomeSoggettoDelegato(i.getSoggettoDelegato());
			entry.setNormativaRiferimento(i.getNormativaRiferimento());
			entry.setNote(i.getNote());
			entry.setNumeroProgressivo(i.getNumProgetto());
			entry.setNuts(i.getCodiceNuts());
			entry.setOggettoMatRic(i.getOggettoMr());
			entry.setOggettoVerdi(i.getOggettoAv());
			entry.setPriorita(i.getLivelloPriorita());
			entry.setProceduraAffidamento(i.getProceduraAffidamento());
			entry.setReferenteDati(i.getReferenteDatiComunicati());
			entry.setRisorseAltro1(i.getAltreRisorseDisp1());
			entry.setRisorseAltro2(i.getAltreRisorseDisp2());
			entry.setRisorseAltro3(i.getAltreRisorseDisp3());
			entry.setRisorseAltroSucc(i.getAltreRisorseDispSucc());
			entry.setRisorseArt3_1(i.getImportoFinanz1());
			entry.setRisorseArt3_2(i.getImportoFinanz2());
			entry.setRisorseArt3_3(i.getImportoFinanz3());
			entry.setRisorseArt3_Succ(i.getImportoFinanzSucc());
			entry.setRisorseBilancio1(i.getStanziamentiBilancio1());
			entry.setRisorseBilancio2(i.getStanziamentiBilancio2());
			entry.setRisorseBilancio3(i.getStanziamentiBilancio3());
			entry.setRisorseBilancioSucc(i.getStanziamentiBilancioSucc());
			entry.setRisorseImmobili1(i.getTrasfImmobili1());
			entry.setRisorseImmobili2(i.getTrasfImmobili2());
			entry.setRisorseImmobili3(i.getTrasfImmobili3());
			entry.setRisorseImmobiliSucc(i.getTrasfImmobiliSucc());
			entry.setRisorseMutuo1(i.getImportoAcqMututo1());
			entry.setRisorseMutuo2(i.getImportoAcqMututo2());
			entry.setRisorseMutuo3(i.getImportoAcqMututo3());
			entry.setRisorseMutuoSucc(i.getImportoAcqMututoSucc());
			entry.setRisorsePrivati1(i.getImportoCapPriv1());
			entry.setRisorsePrivati2(i.getImportoCapPriv2());
			entry.setRisorsePrivati3(i.getImportoCapPriv3());
			entry.setRisorsePrivatiSucc(i.getImportoCapPrivSucc());
			entry.setRisorseVincolatePerLegge1(i.getImpDestVincolo1());
			entry.setRisorseVincolatePerLegge2(i.getImpDestVincolo2());
			entry.setRisorseVincolatePerLegge3(i.getImpDestVincolo3());
			entry.setRisorseVincolatePerLeggeSucc(i.getImpDestVincoloSucc());
			if (i.getRupEntry() != null) {
				entry.setRup(mappingRup(i.getRupEntry()));
			}
			entry.setScadenzaFinanziamento(i.getScadenzaUtilizzoFinanziamento());
			entry.setSpeseSostenute(i.getSpeseSostenute());
			entry.setStatoProgettazione(i.getStatoProgettazioneApprovata());
			entry.setStrutturaOperativa(i.getStrutturaOperativa());
			entry.setTipologia(i.getClassificazioneIntervento());
			entry.setTipologiaCapitalePrivato(i.getTipologiaCapitalePrivato());
			if (i.getValutazioneResp() != null) {
				entry.setValutazione(Long.parseLong(i.getValutazioneResp()));
			}
			entry.setCigAccordoQuadro(i.getCigAccordoQuadro());
			entry.setVariato(i.getVariato());
			risultato.add(entry);
		}

		return risultato;

	}

	private List<InterventoNonRipropostoPubbEntry> mappingInterventiNRForPubb(List<InterventoNonRipropostoEntry> inr) {
		if (inr != null) {
			List<InterventoNonRipropostoPubbEntry> risultato = new ArrayList<InterventoNonRipropostoPubbEntry>();
			for (InterventoNonRipropostoEntry intervento : inr) {
				InterventoNonRipropostoPubbEntry out = new InterventoNonRipropostoPubbEntry();

				out.setConint(intervento.getIdIntervento());
				out.setContri(intervento.getIdProgramma());
				out.setCui(intervento.getCui());
				out.setCup(intervento.getCup());
				out.setDescrizione(intervento.getDescrizione());
				out.setImporto(intervento.getImportoComplessivo());
				out.setMotivo(intervento.getMotivo());
				out.setPriorita(intervento.getPriorita());

				risultato.add(out);
			}
			return risultato;
		}
		return null;
	}

	private DatiGeneraliTecnicoPubbEntry mappingRup(RupEntry rup) {
		DatiGeneraliTecnicoPubbEntry risultato = new DatiGeneraliTecnicoPubbEntry();
		risultato.setCap(rup.getCap());
		risultato.setCfPiva(rup.getCf());
		risultato.setCivico(rup.getNumCivico());
		risultato.setCodice(rup.getCodice());
		risultato.setCodiceSA(rup.getStazioneAppaltante());
		risultato.setCognome(rup.getCognome());
		risultato.setFax(rup.getNome());
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

	public void updateIdRicevutoProgramma(Long idRicevuto, Long contri) {
		this.programmiMapper.updateIdRicevutoProgramma(idRicevuto, contri);
	}

	public PubblicaProgrammaFornitureServiziEntry valorizzaProgrammaFornitureServizi(Long contri) {
		try {

			ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
			String cfsa = this.programmiMapper.getCFSAByCode(programma.getStazioneappaltante());
			programma.setStazioneappaltante(cfsa);
			RupEntry rupProgramma = this.programmiMapper.getRup(programma.getResponsabile());
			programma.setRup(rupProgramma);

			List<InterventoEntry> interventi = this.programmiMapper.getInterventiProgramma(contri);
			List<InterventoNonRipropostoEntry> intyerventiNR = this.programmiMapper.getInterventiNRProgramma(contri);

			for (InterventoEntry intervento : interventi) {
				List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, intervento.getId(), null);
				intervento.setImmobili(immobili);
				RupEntry rupIntervento = this.programmiMapper.getRup(intervento.getCodiceRup());
				intervento.setRupEntry(rupIntervento);
			}

			PubblicaProgrammaFornitureServiziEntry pubblicazioneEntry = this.mappingForPubServizi(programma, interventi,
					intyerventiNR);
			return pubblicazioneEntry;
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la ricerca dei dati di pubblicazione: contri= " + contri);
			return null;
		}
	}

	private PubblicaProgrammaFornitureServiziEntry mappingForPubServizi(ProgrammaEntry p, List<InterventoEntry> i,
			List<InterventoNonRipropostoEntry> inr) {
		List<AcquistoPubbEntry> intervPubb = mappingAcquistiForPubb(i);
		List<AcquistoNonRipropostoPubbEntry> intervNRPubb = mappingAcquistiNRForPubb(inr);
		PubblicaProgrammaFornitureServiziEntry programma = mappingProgrammaFornitureForPubb(p);
		programma.setAcquisti(intervPubb);
		programma.setAcquistiNonRiproposti(intervNRPubb);
		return programma;
	}

	private List<AcquistoPubbEntry> mappingAcquistiForPubb(List<InterventoEntry> interventi) {
		List<AcquistoPubbEntry> risultato = new ArrayList<AcquistoPubbEntry>();
		for (InterventoEntry i : interventi) {
			AcquistoPubbEntry entry = new AcquistoPubbEntry();
			entry.setAcquistoMaterialiRiciclati(i.getAcquistoMaterialiRiciclati());

			entry.setAcquistoRicompreso(i.getAcquistoRicompreso());
			if (i.getAcqVerdi() != null) {
				entry.setAcquistoVerdi(i.getAcqVerdi());
			}
			entry.setAnno(i.getAnnoAvvio());
			entry.setCodiceInterno(i.getCodiceInterno());
			entry.setCodiceRup(i.getCodiceRup());
			entry.setCodiceSoggettoDelegato(i.getCodiceAusa());
			entry.setConint(i.getId());
			entry.setContri(i.getIdProgramma());
			entry.setCoperturaFinanziaria(i.getCoperturaFinanziaria());
			entry.setCpv(i.getCodCpv());
			entry.setCpvMatRic(i.getMrcpv());
			entry.setCpvVerdi(i.getAvcpv());
			entry.setCui(i.getNumeroCui());
			entry.setCuiCollegato(i.getCuiCollegato());
			entry.setCup(i.getCodiceCup());
			entry.setDelega(i.getDelegaProcAff());
			entry.setDescrizione(i.getDescrizione());
			entry.setDirezioneGenerale(i.getDirezioneGenerale());
			entry.setDirigenteResponsabile(i.getDirigenteUfficio());
			entry.setDurataInMesi(i.getDurataContratto());
			entry.setEsenteCup(i.getEsenteCup());
			entry.setImportoIva1(i.getImportoIva1());
			entry.setImportoIva2(i.getImportoIva2());
			entry.setImportoIva3(i.getImportoIva3());
			entry.setImportoIvaSucc(i.getImportoIvaSucc());
			entry.setImportoIvaMatRic(i.getImportoIvaMr());
			entry.setImportoIvaVerdi(i.getImportoIvaAv());
			entry.setImportoNettoIvaMatRic(i.getImportoAlnettoIvaMr());
			entry.setImportoNettoIvaVerdi(i.getImportoAlnettoIvaAv());
			entry.setImportoRisorseFinanziarie(i.getImportoRisorseFinanz());
			entry.setImportoRisorseFinanziarieAltro(i.getImportoRisorseFinanzAltro());
			entry.setImportoRisorseFinanziarieRegionali(i.getImportoRisorseRegionali());
			entry.setImportoTotMatRic(i.getImportoTotaleMr());
			entry.setImportoTotVerdi(i.getImportoTotaleAv());
			entry.setIstat(i.getCodIstatComune());
			entry.setLottoFunzionale(i.getLottoFunzionale());
			entry.setMeseAvvioProcedura(i.getMeseAvvioProc());
			entry.setNomeSoggettoDelegato(i.getSoggettoDelegato());
			entry.setNormativaRiferimento(i.getNormativaRiferimento());
			entry.setNote(i.getNote());
			entry.setNuovoAffidamento(i.getNuovoAffidamento());
			entry.setNuts(i.getCodiceNuts());
			entry.setOggettoMatRic(i.getOggettoMr());
			entry.setOggettoVerdi(i.getOggettoAv());
			entry.setPriorita(i.getLivelloPriorita());
			entry.setProceduraAffidamento(i.getProceduraAffidamento());
			entry.setQuantita(i.getQuantita());
			entry.setReferenteDati(i.getReferenteDatiComunicati());
			entry.setRisorseAltro1(i.getAltreRisorseDisp1());
			entry.setRisorseAltro2(i.getAltreRisorseDisp2());
			entry.setRisorseAltro3(i.getAltreRisorseDisp3());
			entry.setRisorseAltroSucc(i.getAltreRisorseDispSucc());
			entry.setRisorseArt3_1(i.getImportoFinanz1());
			entry.setRisorseArt3_2(i.getImportoFinanz2());
			entry.setRisorseArt3_3(i.getImportoFinanz3());
			entry.setRisorseArt3_Succ(i.getImportoFinanzSucc());
			entry.setRisorseBilancio1(i.getStanziamentiBilancio1());
			entry.setRisorseBilancio2(i.getStanziamentiBilancio2());
			entry.setRisorseBilancio3(i.getStanziamentiBilancio3());
			entry.setRisorseBilancioSucc(i.getStanziamentiBilancioSucc());
			entry.setRisorseImmobili1(i.getTrasfImmobili1());
			entry.setRisorseImmobili2(i.getTrasfImmobili2());
			entry.setRisorseImmobili3(i.getTrasfImmobili3());
			entry.setRisorseImmobiliSucc(i.getTrasfImmobiliSucc());
			entry.setRisorseMutuo1(i.getImportoAcqMututo1());
			entry.setRisorseMutuo2(i.getImportoAcqMututo2());
			entry.setRisorseMutuo3(i.getImportoAcqMututo3());
			entry.setRisorseMutuoSucc(i.getImportoAcqMututoSucc());
			entry.setRisorsePrivati1(i.getImportoCapPriv1());
			entry.setRisorsePrivati2(i.getImportoCapPriv2());
			entry.setRisorsePrivati3(i.getImportoCapPriv3());
			entry.setRisorsePrivatiSucc(i.getImportoCapPrivSucc());
			entry.setRisorseVincolatePerLegge1(i.getImpDestVincolo1());
			entry.setRisorseVincolatePerLegge2(i.getImpDestVincolo2());
			entry.setRisorseVincolatePerLegge3(i.getImpDestVincolo3());
			entry.setRisorseVincolatePerLeggeSucc(i.getImpDestVincoloSucc());
			if (i.getRupEntry() != null) {
				entry.setRup(mappingRup(i.getRupEntry()));
			}
			entry.setSettore(i.getSettore());
			entry.setSpeseSostenute(i.getSpeseSostenute());
			entry.setStrutturaOperativa(i.getStrutturaOperativa());
			if (i.getValutazioneResp() != null) {
				entry.setValutazione(Long.parseLong(i.getValutazioneResp()));
			}
			entry.setVariato(i.getVariato());
			entry.setTipologiaCapitalePrivato(i.getTipologiaCapitalePrivato());
			entry.setUnitaMisura(i.getUnitaMisura());
			entry.setCigAccordoQuadro(i.getCigAccordoQuadro());
			entry.setVariato(i.getVariato());
			risultato.add(entry);
		}
		return risultato;

	}

	private List<AcquistoNonRipropostoPubbEntry> mappingAcquistiNRForPubb(
			List<InterventoNonRipropostoEntry> interventi) {
		List<AcquistoNonRipropostoPubbEntry> risultato = new ArrayList<AcquistoNonRipropostoPubbEntry>();
		for (InterventoNonRipropostoEntry i : interventi) {
			AcquistoNonRipropostoPubbEntry entry = new AcquistoNonRipropostoPubbEntry();
			entry.setConint(i.getIdIntervento());
			entry.setContri(i.getIdProgramma());
			entry.setCui(i.getCui());
			entry.setCup(i.getCup());
			entry.setDescrizione(i.getDescrizione());
			entry.setImporto(i.getImportoComplessivo());
			entry.setMotivo(i.getMotivo());
			entry.setPriorita(i.getPriorita());
			risultato.add(entry);
		}
		return risultato;
	}

	private PubblicaProgrammaFornitureServiziEntry mappingProgrammaFornitureForPubb(ProgrammaEntry p) {
		PubblicaProgrammaFornitureServiziEntry risultato = new PubblicaProgrammaFornitureServiziEntry();
		risultato.setAnno(p.getAnnoInizio());
		risultato.setClientId("clientId");
		risultato.setCodiceFiscaleSA(p.getStazioneappaltante());
		risultato.setCodiceReferente(p.getResponsabile());
		risultato.setCodiceSA(p.getStazioneappaltante());
		risultato.setContri(p.getId());
		risultato.setDataApprovazione(p.getDataApprovazione());
		risultato.setDataPubblicazioneApprovazione(p.getDataPubblicazioneAppr());
		risultato.setDescrizione(p.getDescrizioneBreve());
		risultato.setId(p.getIdProgramma());
		risultato.setIdRicevuto(p.getIdRicevuto());
		risultato.setIdUfficio(p.getIdUfficio());
		risultato.setNumeroApprovazione(p.getNumeroApprovazione());
		risultato.setReferente(mappingRup(p.getRup()));
		risultato.setSyscon(p.getSyscon());
		risultato.setTitoloAttoApprovazione(p.getTitoloApprovazione());
		risultato.setUrlAttoApprovazione(p.getUrlApprovazione());		
		return risultato;
	}

}
