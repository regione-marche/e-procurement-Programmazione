package it.appaltiecontratti.programmi.entity;

public class ExportSogg {

	 private String codiceFiscaleEnte; 
	 private String codiceIPA; 
	 private String amministrazione; 
	 private String ufficio; 
	 private String dipartimento; 
	 private String regione; 
	 private String provincia; 
	 private String indirizzoEnte; 
	 private String telefonoEnte; 
	 private String mailEnte; 
	 private String pecEnte; 
	 private String nomeReferente; 
	 private String cognomeReferente; 
	 private String codiceFiscaleReferente; 
	 private String telefonoReferente; 
	 private String mailReferente; 
	 private String numeroInterventoCUI; 
	 private String codiceFiscaleAmministrazione; 
	 private String primaAnnualita; 
	 private Long anno; 
	 private String identificativoProceduraAcq; 
	 private String codiceCUP; 
	 private String lottoFunzionale; 
	 private Long importoStimatoLotto; 
	 private String ambitoGeografico; 
	 private String codiceCUPmaster; 
	 private String settore; 
	 private String CPV; 
	 private String descrizioneAcquisto; 
	 private String conformitaAmbientale; 
	 private String priorita; 
	 private String cfResponsabileProcedimento; 
	 private String cognomeResponsProcedimento; 
	 private String nomeResponsabileProcedimento; 
	 private Long quantita; 
	 private String unitaMisura; 
	 private Long durataContratto;
	 private Double stimaCostiProgrammaPrimoAnno;
	 private Double stimaCostiProgrammaSecondoAnno;
	 private Double costiAnnualitaSuccessive;
	 private Double stimaCostiProgrammaTotale;
	 private Double importoApportoCapitalePrivato;
	 private String tipoApportoCapitalePrivato; 
	 private String delega; 
	 private String codiceAUSA; 
	 private String denomAmministrazioneDelegata;
	 private String acquistoRicompreso;
	 private String cuiLavoro;
	 private String contrattoInEssere;

	public Double getStimaCostiProgrammaTerzoAnno() {
		return stimaCostiProgrammaTerzoAnno;
	}

	public void setStimaCostiProgrammaTerzoAnno(Double stimaCostiProgrammaTerzoAnno) {
		this.stimaCostiProgrammaTerzoAnno = stimaCostiProgrammaTerzoAnno;
	}

	public Double getImportoApportoCapitalePrivato() {
		return importoApportoCapitalePrivato;
	}

	public Double getStimaCostiProgrammaTotale() {
		return stimaCostiProgrammaTotale;
	}

	public Double getCostiAnnualitaSuccessive() {
		return costiAnnualitaSuccessive;
	}

	public Double getStimaCostiProgrammaSecondoAnno() {
		return stimaCostiProgrammaSecondoAnno;
	}

	private Double stimaCostiProgrammaTerzoAnno;
	 private String aquistoAggVar;

	public void setStimaCostiProgrammaPrimoAnno(Double stimaCostiProgrammaPrimoAnno) {
		this.stimaCostiProgrammaPrimoAnno = stimaCostiProgrammaPrimoAnno;
	}

	public void setStimaCostiProgrammaSecondoAnno(Double stimaCostiProgrammaSecondoAnno) {
		this.stimaCostiProgrammaSecondoAnno = stimaCostiProgrammaSecondoAnno;
	}

	public void setCostiAnnualitaSuccessive(Double costiAnnualitaSuccessive) {
		this.costiAnnualitaSuccessive = costiAnnualitaSuccessive;
	}

	public void setStimaCostiProgrammaTotale(Double stimaCostiProgrammaTotale) {
		this.stimaCostiProgrammaTotale = stimaCostiProgrammaTotale;
	}

	public void setImportoApportoCapitalePrivato(Double importoApportoCapitalePrivato) {
		this.importoApportoCapitalePrivato = importoApportoCapitalePrivato;
	}

	public String getAcquistoRicompreso() {
		return acquistoRicompreso;
	}

	public void setAcquistoRicompreso(String acquistoRicompreso) {
		this.acquistoRicompreso = acquistoRicompreso;
	}

	public String getCuiLavoro() {
		return cuiLavoro;
	}

	public void setCuiLavoro(String cuiLavoro) {
		this.cuiLavoro = cuiLavoro;
	}

	public String getContrattoInEssere() {
		return contrattoInEssere;
	}

	public void setContrattoInEssere(String contrattoInEssere) {
		this.contrattoInEssere = contrattoInEssere;
	}



	public String getAquistoAggVar() {
		return aquistoAggVar;
	}

	public void setAquistoAggVar(String aquistoAggVar) {
		this.aquistoAggVar = aquistoAggVar;
	}

	public String getCodiceFiscaleEnte() {
		return codiceFiscaleEnte;
	}
	public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
		this.codiceFiscaleEnte = codiceFiscaleEnte;
	}
	public String getCodiceIPA() {
		return codiceIPA;
	}
	public void setCodiceIPA(String codiceIPA) {
		this.codiceIPA = codiceIPA;
	}
	public String getAmministrazione() {
		return amministrazione;
	}
	public void setAmministrazione(String amministrazione) {
		this.amministrazione = amministrazione;
	}
	public String getUfficio() {
		return ufficio;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	public String getDipartimento() {
		return dipartimento;
	}
	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIndirizzoEnte() {
		return indirizzoEnte;
	}
	public void setIndirizzoEnte(String indirizzoEnte) {
		this.indirizzoEnte = indirizzoEnte;
	}
	public String getTelefonoEnte() {
		return telefonoEnte;
	}
	public void setTelefonoEnte(String telefonoEnte) {
		this.telefonoEnte = telefonoEnte;
	}
	public String getMailEnte() {
		return mailEnte;
	}
	public void setMailEnte(String mailEnte) {
		this.mailEnte = mailEnte;
	}
	public String getPecEnte() {
		return pecEnte;
	}
	public void setPecEnte(String pecEnte) {
		this.pecEnte = pecEnte;
	}
	public String getNomeReferente() {
		return nomeReferente;
	}
	public void setNomeReferente(String nomeReferente) {
		this.nomeReferente = nomeReferente;
	}
	public String getCognomeReferente() {
		return cognomeReferente;
	}
	public void setCognomeReferente(String cognomeReferente) {
		this.cognomeReferente = cognomeReferente;
	}
	public String getCodiceFiscaleReferente() {
		return codiceFiscaleReferente;
	}
	public void setCodiceFiscaleReferente(String codiceFiscaleReferente) {
		this.codiceFiscaleReferente = codiceFiscaleReferente;
	}
	public String getTelefonoReferente() {
		return telefonoReferente;
	}
	public void setTelefonoReferente(String telefonoReferente) {
		this.telefonoReferente = telefonoReferente;
	}
	public String getMailReferente() {
		return mailReferente;
	}
	public void setMailReferente(String mailReferente) {
		this.mailReferente = mailReferente;
	}
	public String getNumeroInterventoCUI() {
		return numeroInterventoCUI;
	}
	public void setNumeroInterventoCUI(String numeroInterventoCUI) {
		this.numeroInterventoCUI = numeroInterventoCUI;
	}
	public String getCodiceFiscaleAmministrazione() {
		return codiceFiscaleAmministrazione;
	}
	public void setCodiceFiscaleAmministrazione(String codiceFiscaleAmministrazione) {
		this.codiceFiscaleAmministrazione = codiceFiscaleAmministrazione;
	}
	public String getPrimaAnnualita() {
		return primaAnnualita;
	}
	public void setPrimaAnnualita(String primaAnnualita) {
		this.primaAnnualita = primaAnnualita;
	}
	public Long getAnno() {
		return anno;
	}
	public void setAnno(Long anno) {
		this.anno = anno;
	}
	public String getIdentificativoProceduraAcq() {
		return identificativoProceduraAcq;
	}
	public void setIdentificativoProceduraAcq(String identificativoProceduraAcq) {
		this.identificativoProceduraAcq = identificativoProceduraAcq;
	}
	public String getCodiceCUP() {
		return codiceCUP;
	}
	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}
	public String getLottoFunzionale() {
		return lottoFunzionale;
	}
	public void setLottoFunzionale(String lottoFunzionale) {
		this.lottoFunzionale = lottoFunzionale;
	}
	public Long getImportoStimatoLotto() {
		return importoStimatoLotto;
	}
	public void setImportoStimatoLotto(Long importoStimatoLotto) {
		this.importoStimatoLotto = importoStimatoLotto;
	}
	public String getAmbitoGeografico() {
		return ambitoGeografico;
	}
	public void setAmbitoGeografico(String ambitoGeografico) {
		this.ambitoGeografico = ambitoGeografico;
	}
	public String getCodiceCUPmaster() {
		return codiceCUPmaster;
	}
	public void setCodiceCUPmaster(String codiceCUPmaster) {
		this.codiceCUPmaster = codiceCUPmaster;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getCPV() {
		return CPV;
	}
	public void setCPV(String cPV) {
		CPV = cPV;
	}
	public String getDescrizioneAcquisto() {
		return descrizioneAcquisto;
	}
	public void setDescrizioneAcquisto(String descrizioneAcquisto) {
		this.descrizioneAcquisto = descrizioneAcquisto;
	}
	public String getConformitaAmbientale() {
		return conformitaAmbientale;
	}
	public void setConformitaAmbientale(String conformitaAmbientale) {
		this.conformitaAmbientale = conformitaAmbientale;
	}
	public String getPriorita() {
		return priorita;
	}
	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}
	public String getCfResponsabileProcedimento() {
		return cfResponsabileProcedimento;
	}
	public void setCfResponsabileProcedimento(String cfResponsabileProcedimento) {
		this.cfResponsabileProcedimento = cfResponsabileProcedimento;
	}
	public String getCognomeResponsProcedimento() {
		return cognomeResponsProcedimento;
	}
	public void setCognomeResponsProcedimento(String cognomeResponsProcedimento) {
		this.cognomeResponsProcedimento = cognomeResponsProcedimento;
	}
	public String getNomeResponsabileProcedimento() {
		return nomeResponsabileProcedimento;
	}
	public void setNomeResponsabileProcedimento(String nomeResponsabileProcedimento) {
		this.nomeResponsabileProcedimento = nomeResponsabileProcedimento;
	}
	public Long getQuantita() {
		return quantita;
	}
	public void setQuantita(Long quantita) {
		this.quantita = quantita;
	}
	public String getUnitaMisura() {
		return unitaMisura;
	}
	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}
	public Long getDurataContratto() {
		return durataContratto;
	}
	public void setDurataContratto(Long durataContratto) {
		this.durataContratto = durataContratto;
	}
	public Double getStimaCostiProgrammaPrimoAnno() {
		return stimaCostiProgrammaPrimoAnno;
	}

	public String getTipoApportoCapitalePrivato() {
		return tipoApportoCapitalePrivato;
	}
	public void setTipoApportoCapitalePrivato(String tipoApportoCapitalePrivato) {
		this.tipoApportoCapitalePrivato = tipoApportoCapitalePrivato;
	}
	public String getDelega() {
		return delega;
	}
	public void setDelega(String delega) {
		this.delega = delega;
	}
	public String getCodiceAUSA() {
		return codiceAUSA;
	}
	public void setCodiceAUSA(String codiceAUSA) {
		this.codiceAUSA = codiceAUSA;
	}
	public String getDenomAmministrazioneDelegata() {
		return denomAmministrazioneDelegata;
	}
	public void setDenomAmministrazioneDelegata(String denomAmministrazioneDelegata) {
		this.denomAmministrazioneDelegata = denomAmministrazioneDelegata;
	}
	 
	 
	 
}