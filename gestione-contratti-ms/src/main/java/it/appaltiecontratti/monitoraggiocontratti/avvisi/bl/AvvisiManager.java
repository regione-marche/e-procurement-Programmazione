package it.appaltiecontratti.monitoraggiocontratti.avvisi.bl;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisiNonPaginatiSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoBaseForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoDocForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoDocument;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoPubblicatoForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.DocAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ExistingDocAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.FlussiAvviso;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.PubblicazioneAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.PubblicazioneDocumentoAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.PubblicazioneTecnicoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseListaAvvisi;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseListaAvvisiNonPaginata;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseRequestPubblicazioneAvvisi;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.AvvisiMapper;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.WGenChiaviManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.UffEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.CambioSysconForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;

/**
 * Manager per la gestione della business logic.
 * 
 * @author Michele.DiNapoli
 */
@Component(value = "avvisiManager")
public class AvvisiManager {

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(AvvisiManager.class);
	/**
	 * Dao MyBatis con le primitive di estrazione dei dati.
	 */
	@Autowired
	private AvvisiMapper avvisiMapper;

	@Autowired
	private SqlMapper sqlMapper;
	
	@Autowired
	protected WGenChiaviManager wgcManager;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult insert(AvvisoInsertForm form) throws Exception {

		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		try {
			if (form.getDataScadenza() != null && !"".equals(form.getDataScadenza())) {
				form.setDataScadenzaToInsert(new SimpleDateFormat("yyyy-MM-dd").parse(form.getDataScadenza()));
			}
			if (form.getDataAvviso() != null && !"".equals(form.getDataAvviso())) {
				form.setDataAvvisoToInsert(new SimpleDateFormat("yyyy-MM-dd").parse(form.getDataAvviso()));
			}

			Integer idAvviso = this.avvisiMapper.getIdAvviso(form);
			if (idAvviso == null) {
				idAvviso = 1;
			} else {
				idAvviso++;
			}
			form.setNumeroAvviso(idAvviso);
			risultato.setData(idAvviso + "");
			this.avvisiMapper.insertAvviso(form);

			if (form.getNewDocuments() != null) {
				for (AvvisoDocument doc : form.getNewDocuments()) {
					Integer numDoc = this.avvisiMapper.getIdDocAvviso(form);
					if (numDoc == null) {
						numDoc = 0;
					} else {
						numDoc = numDoc + 1;
					}
					AvvisoDocForm docForm = new AvvisoDocForm();
					docForm.setTitolo(doc.getTitolo());
					if (StringUtils.isNotBlank(doc.getBinary())) {
						byte[] dedcodedFile = Base64.getDecoder().decode(doc.getBinary().getBytes());
						docForm.setFile_allegato(dedcodedFile);
					}
					docForm.setUrl(doc.getUrl());
					docForm.setStazioneAppaltante(form.getStazioneAppaltante());
					docForm.setNumdoc(numDoc);
					docForm.setNumeroAvviso(form.getNumeroAvviso());
					docForm.setTipoFile(doc.getTipoFile());
					this.avvisiMapper.insertDocAvviso(docForm);
				}
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato durante insert avviso: SA =" + form.getStazioneAppaltante()
					+ ", numero avviso " + form.getNumeroAvviso(), e);
			throw e;
		}

		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult update(AvvisoUpdateForm form) throws Exception {

		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		try {
			if (form.getDataScadenza() != null && !"".equals(form.getDataScadenza())) {
				form.setDataScadenzaToInsert(new SimpleDateFormat("yyyy-MM-dd").parse(form.getDataScadenza()));
			}
			if (form.getDataAvviso() != null && !"".equals(form.getDataAvviso())) {
				form.setDataAvvisoToInsert(new SimpleDateFormat("yyyy-MM-dd").parse(form.getDataAvviso()));
			}

			this.avvisiMapper.updateAvviso(form);
			AvvisoBaseForm baseForm = new AvvisoBaseForm();
			baseForm.setNumeroAvviso(form.getNumeroAvviso());
			baseForm.setStazioneAppaltante(form.getStazioneAppaltante());

			if (form.getExistingDocuments() != null && form.getExistingDocuments().size() > 0) {
				List<DocAvvisoEntry> savedDocs = this.avvisiMapper
						.getAvvisoDocumentsWithoutAllegato(form.getNumeroAvviso(), form.getStazioneAppaltante());
				if (savedDocs != null && savedDocs.size() > 0) {
					for (DocAvvisoEntry savedDoc : savedDocs) {
						boolean isPresent = form.getExistingDocuments().stream().map(ExistingDocAvvisoEntry::getNumDoc)
								.anyMatch(numDoc -> savedDoc.getNumdoc() == numDoc);
						if (!isPresent) {
							this.avvisiMapper.deleteAvvisoDoc(baseForm.getStazioneAppaltante(),
									baseForm.getNumeroAvviso(), savedDoc.getNumdoc());
						}
					}
				}
			} else {
				// delete all
				this.avvisiMapper.deleteAvvisoDocs(baseForm);
			}

			if (form.getNewDocuments() != null) {

				Integer numDoc = this.avvisiMapper.getIdDocAvvisoUpdate(form);
				numDoc++;

				for (AvvisoDocument doc : form.getNewDocuments()) {
					AvvisoDocForm docForm = new AvvisoDocForm();
					docForm.setTitolo(doc.getTitolo());
					if (StringUtils.isNotBlank(doc.getBinary())) {
						byte[] dedcodedFile = Base64.getDecoder().decode(doc.getBinary().getBytes());
						docForm.setFile_allegato(dedcodedFile);
					}
					docForm.setUrl(doc.getUrl());
					docForm.setStazioneAppaltante(form.getStazioneAppaltante());
					docForm.setNumdoc(numDoc);
					docForm.setNumeroAvviso(form.getNumeroAvviso());
					docForm.setTipoFile(doc.getTipoFile());
					this.avvisiMapper.insertDocAvviso(docForm);
					numDoc++;
				}
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato durante insert avviso: SA =" + form.getStazioneAppaltante()
					+ ", numero avviso " + form.getNumeroAvviso(), e);
			throw (e);
		}

		return risultato;
	}

	public ResponseListaAvvisi list(AvvisoSearchForm form) {

		ResponseListaAvvisi risultato = new ResponseListaAvvisi();
		risultato.setEsito(true);

		try {
			String ruolo = this.avvisiMapper.getRuolo(form.getSyscon());
			if ("A".equals(ruolo) || "C".equals(ruolo)) {
				form.setSyscon(null);
			}
			if (form.getDescrizione() != null && !"".equals(form.getDescrizione())) {
				form.setDescrizione("%" + form.getDescrizione() + "%");
			}
			if (form.getCup() != null && !"".equals(form.getCup())) {
				form.setCup("%" + form.getCup() + "%");
			}
			if("*".equals(form.getStazioneAppaltante())) {
				form.setStazioneAppaltante(null);
			}
			int totalCount = this.avvisiMapper.countSearchAvvisi(form);
			RowBounds rowBounds = new RowBounds(form.getSkip(), form.getTake());
			List<AvvisoEntry> entry = this.avvisiMapper.getAvvisi(form, rowBounds);
			for (AvvisoEntry avvisoEntry : entry) {
				avvisoEntry.setNominativoStazioneAppaltante(this.avvisiMapper.getNominativoSa(avvisoEntry.getStazioneAppaltante()));
			}
			risultato.setTotalCount(totalCount);
			risultato.setData(entry);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante ricerca avvisi avviso: numero =" + form.getStazioneAppaltante(),
					t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}

		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult delete(AvvisoBaseForm form) throws Exception {

		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);

		try {

			this.avvisiMapper.deleteAvviso(form);
			this.avvisiMapper.deleteAvvisoDocs(form);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante cancellazione avviso: SA =" + form.getStazioneAppaltante()
					+ " id =" + form.getNumeroAvviso(), e);
			throw (e);
		}

		return risultato;
	}

	public ResponseAvvisoEntry dettaglio(AvvisoBaseForm form) {

		ResponseAvvisoEntry risultato = new ResponseAvvisoEntry();
		risultato.setEsito(true);

		try {

			AvvisoEntry avvisoEntry = this.avvisiMapper.getAvviso(form);
			if (avvisoEntry != null) {
				RupEntry rupEntry = this.avvisiMapper.getRupBycod(avvisoEntry.getRup());
				if (rupEntry != null) {
					avvisoEntry.setRupEntry(rupEntry);
					avvisoEntry.setRup(rupEntry.getNominativo() + " (" + rupEntry.getCf() + ")");
				}
				if(avvisoEntry.getIdUfficio() != null) {
					UffEntry ufficio = this.avvisiMapper.getUfficio(avvisoEntry.getIdUfficio());
					avvisoEntry.setUfficio(ufficio);
					if(ufficio != null && ufficio.getDenominazione() != null) {
						avvisoEntry.setUfficioDesc(ufficio.getDenominazione());
					}
				}

				
				form.setNumeroAvviso(avvisoEntry.getNumeroAvviso());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				if (avvisoEntry.getDataScadenza() != null && !"".equals(avvisoEntry.getDataScadenza())) {
					avvisoEntry.setDataScadenza(sdf.format(sdf.parse(avvisoEntry.getDataScadenza())));
				}
				if (avvisoEntry.getDataAvviso() != null && !"".equals(avvisoEntry.getDataAvviso())) {
					avvisoEntry.setDataAvviso(sdf.format(sdf.parse(avvisoEntry.getDataAvviso())));
				}

				List<DocAvvisoEntry> docEntry = this.avvisiMapper.getdocAvviso(form);
				List<ExistingDocAvvisoEntry> existingDocuments = new ArrayList<>();

				if (docEntry != null) {
					for (DocAvvisoEntry docAvvisoEntry : docEntry) {
						ExistingDocAvvisoEntry existingDocument = new ExistingDocAvvisoEntry();
						existingDocument.setCodein(docAvvisoEntry.getCodein());
						existingDocument.setIdAvviso(docAvvisoEntry.getIdavviso());
						existingDocument.setNumDoc(docAvvisoEntry.getNumdoc());
						existingDocument.setUrl(docAvvisoEntry.getUrl());
						existingDocument.setTitolo(docAvvisoEntry.getTitolo());
						existingDocument.setCodSistema(docAvvisoEntry.getCodsistema());

						if (docAvvisoEntry.getFile_allegato() != null) {
							existingDocument.setTipoFile(docAvvisoEntry.getTipoFile());
						}
						existingDocuments.add(existingDocument);
					}
				}

				avvisoEntry.setExistingDocuments(existingDocuments);
				List<FlussiAvviso> pubblicazioni = this.avvisiMapper.getPubblicazioni(form);
				avvisoEntry.setPubblicazioni(pubblicazioni);

				avvisoEntry.setNominativoStazioneAppaltante(this.avvisiMapper.getNominativoSa(avvisoEntry.getStazioneAppaltante()));
			}
			risultato.setData(avvisoEntry);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante ricerca avvisi avviso: SA =" + form.getStazioneAppaltante(), t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}

		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseResult updateIdRicevuto(AvvisoPubblicatoForm form,Long syscon) throws Exception {
		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		try {
			AvvisoBaseForm avvisoBaseForm = new AvvisoBaseForm();
			avvisoBaseForm.setNumeroAvviso(form.getNumeroAvviso());
			avvisoBaseForm.setStazioneAppaltante(form.getStazioneAppaltante());
			AvvisoEntry avviso = this.avvisiMapper.getAvviso(avvisoBaseForm);
			Long tinvio = 1L;
			if(avviso != null && avviso.getIdRicevuto() != null) {
				tinvio = 2L;
			}

			this.avvisiMapper.updateIdGenerato(form);
			FlussoEntry flusso = new FlussoEntry();
			Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
			flusso.setId(idFlusso);
			flusso.setArea(new Long(3));
			flusso.setKey01(new Long(form.getNumeroAvviso()));
			flusso.setKey02(new Long(1));
			flusso.setKey03(new Long(989));
			flusso.setTipoInvio(tinvio);
			flusso.setDataInvio(new Date());
			flusso.setCodiceFiscaleSA(form.getStazioneAppaltante());
			flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
			flusso.setJson(form.getPayload());
			this.avvisiMapper.insertFlusso(flusso);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante update id_generato: SA=" + form.getStazioneAppaltante()
					+ " Num avviso=" + form.getNumeroAvviso(), e);
			throw (e);
		}
		return risultato;
	}

	public ResponseResult health() {
		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		try {
			this.avvisiMapper.health();

		} catch (Throwable t) {
			logger.error("Health: Errore inaspettato.", t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}
		return risultato;
	}

	public ResponseResult cambiaSyscon(CambioSysconForm form) {
		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		try {
			this.avvisiMapper.updateSysconAvviso(form.getSyscon(), form.getStazioneAppaltante(), form.getIdAvviso());

		} catch (Throwable t) {
			logger.error("cambiaSyscon: Errore inaspettato.", t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}
		return risultato;
	}

	public ResponseRequestPubblicazioneAvvisi getRequestPubblicazioneAvviso(final Long idAvviso,
			final String codiceStazioneAppaltante,Long syscon) {
		ResponseRequestPubblicazioneAvvisi result = new ResponseRequestPubblicazioneAvvisi();

		try {

			PubblicazioneAvvisoEntry pubbEntry = new PubblicazioneAvvisoEntry();


			if (StringUtils.isBlank(codiceStazioneAppaltante)) {
				throw new IllegalArgumentException("Stazione Appaltante non trovata");
			}

			AvvisoBaseForm form = new AvvisoBaseForm();
			form.setNumeroAvviso(idAvviso.intValue());
			form.setStazioneAppaltante(codiceStazioneAppaltante);			
			// Avviso
			AvvisoEntry avvisoEntry = this.avvisiMapper.getAvviso(form);
			if (avvisoEntry != null) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				if (avvisoEntry.getDataScadenza() != null && !"".equals(avvisoEntry.getDataScadenza())) {
					avvisoEntry.setDataScadenza(sdf.format(sdf.parse(avvisoEntry.getDataScadenza())));
				}
				if (avvisoEntry.getDataAvviso() != null && !"".equals(avvisoEntry.getDataAvviso())) {
					avvisoEntry.setDataAvviso(sdf.format(sdf.parse(avvisoEntry.getDataAvviso())));
				}

				String codiceFiscaleStazioneAppaltante = this.avvisiMapper.getCFSAByCode(codiceStazioneAppaltante);
				
				pubbEntry.setCodiceFiscaleSA(codiceFiscaleStazioneAppaltante);
				pubbEntry.setTipologia(avvisoEntry.getTipoAvviso());
				pubbEntry.setData(avvisoEntry.getDataAvviso());
				pubbEntry.setDescrizione(avvisoEntry.getDescrizione());
				pubbEntry.setCig(avvisoEntry.getCig());
				pubbEntry.setCup(avvisoEntry.getCup());
				pubbEntry.setCui(avvisoEntry.getCui());
				pubbEntry.setScadenza(avvisoEntry.getDataScadenza());
				pubbEntry.setIdRicevuto(avvisoEntry.getIdRicevuto() == null? null : avvisoEntry.getIdRicevuto() + "");				
				pubbEntry.setAutore(sqlMapper.getNameBySyscon(syscon));
				pubbEntry.setIndirizzo(avvisoEntry.getIndirizzo());
				pubbEntry.setProvincia(avvisoEntry.getProvincia());
				pubbEntry.setComune(avvisoEntry.getComune());
				if(avvisoEntry.getIdUfficio() != null) {
					UffEntry entry = this.avvisiMapper.getUfficio(avvisoEntry.getIdUfficio());
					if(entry != null && entry.getDenominazione() != null) {
						pubbEntry.setUfficio(entry.getDenominazione());
					}
					
				}
				
				// RUP
				RupEntry rupEntry = this.avvisiMapper.getRupBycod(avvisoEntry.getRup());
				if (rupEntry != null) {
					PubblicazioneTecnicoEntry pubbTecnico = new PubblicazioneTecnicoEntry();
					pubbTecnico.setCodice(rupEntry.getCodice());
					pubbTecnico.setCognome(rupEntry.getCognome());
					pubbTecnico.setNome(rupEntry.getNome());
					pubbTecnico.setNomeCognome(rupEntry.getNominativo());
					pubbTecnico.setIndirizzo(rupEntry.getIndirizzo());
					pubbTecnico.setCivico(rupEntry.getNumCivico());
					pubbTecnico.setLocalita(rupEntry.getComune());
					pubbTecnico.setProvincia(rupEntry.getProvincia());
					pubbTecnico.setCap(rupEntry.getCap());
					pubbTecnico.setLuogoIstat(rupEntry.getCodIstat());
					pubbTecnico.setTelefono(rupEntry.getTelefono());
					pubbTecnico.setFax(rupEntry.getFax());
					pubbTecnico.setCfPiva(rupEntry.getCf());
					pubbTecnico.setMail(rupEntry.getEmail());
					pubbEntry.setRup(pubbTecnico);
				}

				// Documenti

				List<PubblicazioneDocumentoAvvisoEntry> listaPubbDocumenti = new ArrayList<>();

				List<DocAvvisoEntry> listaDocumenti = this.avvisiMapper.getdocAvviso(form);
				if (listaDocumenti != null && listaDocumenti.size() > 0) {
					listaPubbDocumenti = listaDocumenti.stream().map(d -> {
						PubblicazioneDocumentoAvvisoEntry docPubbEntry = new PubblicazioneDocumentoAvvisoEntry();

						docPubbEntry.setUrl(d.getUrl());
						docPubbEntry.setTitolo(d.getTitolo());
						docPubbEntry.setTipoFile(d.getTipoFile());

						if (d.getFile_allegato() != null) {
							byte[] encodedFile = Base64.getEncoder().encode(d.getFile_allegato());
							docPubbEntry.setFile_allegato(new String(encodedFile, StandardCharsets.UTF_8));
						}

						return docPubbEntry;
					}).collect(Collectors.toList());
				}

				pubbEntry.setDocumenti(listaPubbDocumenti);
			}

			result.setData(pubbEntry);
			result.setEsito(true);
		} catch (IllegalArgumentException e) {
			logger.error("Errore durante il recupero dell'avviso da pubblicare: Id Avviso [ " + idAvviso + " ] e SA [ "
					+ codiceStazioneAppaltante + " ]", e);
			throw e;
		} catch (Exception e) {
			logger.error("Errore inaspettato durante il recupero dell'avviso da pubblicare: Id Avviso [ " + idAvviso
					+ " ] e SA [ " + codiceStazioneAppaltante + " ]", e);
			result.setEsito(false);
			result.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + e.getMessage());
		}

		return result;
	}

	public DocAvvisoEntry downloadDocumentoAvviso(final Integer idAvviso, final String codiceStazioneAppaltante,
			final Integer numDoc) {

		if (idAvviso != null && StringUtils.isNotBlank(codiceStazioneAppaltante) && numDoc != null) {
			DocAvvisoEntry doc = this.avvisiMapper.getAvvisoDocument(idAvviso, codiceStazioneAppaltante, numDoc);
			if (doc != null) {
				if (doc.getFile_allegato() != null) {
					byte[] encodedFile = Base64.getEncoder().encode(doc.getFile_allegato());
					doc.setBinary(new String(encodedFile, StandardCharsets.UTF_8));
				}
				return doc;
			}
		}

		return null;
	}

	public ResponseListaAvvisiNonPaginata getListaAvvisiNonPaginata(final AvvisiNonPaginatiSearchForm searchForm) {
		ResponseListaAvvisiNonPaginata risultato = new ResponseListaAvvisiNonPaginata();
		try {
			List<AvvisoEntry> entries = new ArrayList<AvvisoEntry>();

			if (StringUtils.isBlank(searchForm.getSearchString())) {
				risultato.setData(entries);
				risultato.setEsito(true);
				return risultato;
			} else {
				searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
			}

			String ruolo = this.avvisiMapper.getRuolo(searchForm.getSyscon());
			if ("A".equals(ruolo) || "C".equals(ruolo)) {
				searchForm.setSyscon(null);
			}
			if(searchForm.getStazioneAppaltante() != null && "*".equals(searchForm.getStazioneAppaltante())) {
				searchForm.setStazioneAppaltante(null);
			}
			entries = this.avvisiMapper.getListaAvvisiNonPaginata(searchForm);
			risultato.setData(entries);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore durante il recupero della lista avvisi non paginata", e);
			risultato.setErrorData(ResponseListaAvvisiNonPaginata.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		return risultato;
	}
	
	public BaseResponse deleteFlussoAvviso(Long idAvviso) {
		BaseResponse risultato = new BaseResponse();
		try {
			this.avvisiMapper.deleteFlussoAvviso(idAvviso);			
			risultato.setEsito(true);

		} catch (Exception e) {
			logger.error("Errore inaspettato durante la delete del flusso della pubblicazione degli avviso: " + idAvviso, e);
			throw e;
		}
		return risultato;
	}


	public Long getSysconFromCf(String cf) {
		return this.avvisiMapper.getSysconFromCf(cf);
	}

}