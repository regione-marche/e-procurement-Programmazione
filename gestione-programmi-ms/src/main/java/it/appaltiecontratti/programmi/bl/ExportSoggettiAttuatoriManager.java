package it.appaltiecontratti.programmi.bl;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appaltiecontratti.programmi.entity.BaseResponse;
import it.appaltiecontratti.programmi.entity.ExportSogg;
import it.appaltiecontratti.programmi.entity.ResponseResult;
import it.appaltiecontratti.programmi.mapper.ProgrammiMapper;
import it.appaltiecontratti.programmi.mapper.SqlMapper;
import it.appaltiecontratti.programmi.utils.DizionarioStiliExcel;

@SuppressWarnings("java:S3749")
@Component(value = "exportSoggettiAttuatoriManager")
public class ExportSoggettiAttuatoriManager {

	public final String MODELLO_ADEMPIMENTI_LEGGE_190 = "soggettiAggregatori.xls";
	public final String FOGLIO_MODELLO_DATI_ENTE = "Dati Ente";
	public final String FOGLIO_MODELLO_SCHEDA_B = "Scheda H";
	
	private final int START_ROW = 2;
	
	// Foglio Dati Ente
	private final String F1_COL_AMMINISTRAZIONE = "Amministrazione";
	private final String F1_COL_REFERENTE = "Referente dei dati di programmazione";

	private final String[] F1_TITOLI_COLONNE = new String[] {
	  "Amministrazione","Codice Fiscale", "Codice IPA Amministrazione", "Dipartimento",
      "Ufficio", "Regione", "Provincia", "Indirizzo",
      "Telefono", "Indirizzo mail", "Indirizzo PEC", "Nome",
      "Cognome", "Codice fiscale", "Telefono", "Indirizzo mail" };
	
	private final int[] F1_LARGHEZZA_COLONNE = new int[] { 25, 25, 25, 25, 20, 20, 10, 25, 15, 25, 25, 15, 20, 20, 15, 25 };
	  
	  // Foglio Scheda B
	private final String F2_COL = "SCHEDA H - PROGRAMMA TRIENNALE - ELENCO DELLE ACQUISIZIONI DI FORNITURE E SERVIZI DI IMPORTO STIMATO SUPERIORE A 1 MILIONE DI EURO AAAA/AAAA+2";
	  
	private final String[] F2_TITOLI_COLONNE = new String[] {
			"Numero intervento CUI",
			"Codice Fiscale Amministrazione",
			"Prima annualità del primo programma nel quale l'intervento è stato inserito",
			"Annualità nella quale si prevede di dare avvio alla procedura di acquisto",
			"Codice CUP",
			"Acquisto ricompreso nell'importo complessivo di un lavoro o di altra acquisizione presente in programmazione di lavori,forniture e servizi",
			"CUI lavoro o altra acquisizione nel cui importo complessivo l'acquisto è ricompreso",
			"Lotto funzionale",
			"Ambito geografico di esecuzione dell'Acquisto (Regione/i)",
			"Settore",
			"CPV",
			"Descrizione Acquisto",
			"Livello di priorità",
			"Responsabile unico del progetto",
			"Durata del contratto",
			"L'acquisto è relativo a nuovo affidamento di contratto in essere",
			"Stima dei costi dell'acquisto Primo anno",
			"Stima dei costi dell'acquisto Secondo anno",
			"Stima dei costi dell'acquisto Terzo Anno",
			"Costi su annualità successive",
			"Totale",
			"Apporto di capitale privato Importo",
			"Apporto di capitale privato Tipologia",
			"Codice AUSA Centrale di Committenza o Soggetto Aggregatore al quale si farà ricorso per l'espletamento della procedura di affidamento",
			"Denominazione Centrale di Committenza o Soggetto Aggregatore al quale si farà ricorso per l'espletamento della procedura di affidamento",
			"Acquisto aggiunto o variato a seguito di modifica programma" };
	
	private final int[] F2_LARGHEZZA_COLONNE = new int[] {
	      25, 25, 20, 20, 20, 20,
		  20, 20, 20, 20, 20, 20,
		  20, 20, 20, 20, 25, 20,
		  20, 20, 20, 20, 20, 20,
		  20, 25 };
	  
	  
	  /** Logger di classe. */
	protected static Logger logger = LogManager.getLogger(ProgrammiManager.class);
	
		/**
		 * Dao MyBatis con le primitive di estrazione dei dati.
		 */
	@Autowired
	private ProgrammiMapper programmiMapper;
	
	@Autowired
	private SqlMapper sqlMapper;	
	
	@Autowired
	DataSource dataSource;
		
	@Autowired
	protected WGenChiaviManager wgcManager;
		
		
	public ResponseResult reportSoggAggregatori(String idProgramma)  {
		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		
		try {
			//InputStream inp = new FileInputStream(MODELLO_ADEMPIMENTI_LEGGE_190);
			Workbook wb = new XSSFWorkbook();
		  			   
		    // Creazione del dizionario degli stili delle celle dell'intero file Excel
		    DizionarioStiliExcel dizStiliExcel = new DizionarioStiliExcel(wb);
		    Row riga = null;
		    Cell cella = null;
		    CellStyle stile = dizStiliExcel.getStileExcel(DizionarioStiliExcel.CELLA_INTESTAZIONE);
		    
		    // Creazione del primo foglio e scrittura righe di intestazione 
		    Sheet foglioDatiEnte = wb.createSheet(FOGLIO_MODELLO_DATI_ENTE);

		    // Prima riga del foglio 'Dati Ente'
		    // Gruppi di celle con due titoli
		    riga = foglioDatiEnte.createRow(0);
		    cella = riga.createCell(0);
		    cella.setCellStyle(stile);
		    cella.setCellValue(new XSSFRichTextString(F1_COL_AMMINISTRAZIONE));
		    cella = riga.createCell(11);
		    cella.setCellStyle(stile);
		    cella.setCellValue(new XSSFRichTextString(F1_COL_REFERENTE));
		    CellRangeAddress cellRange1 = new CellRangeAddress(0, 0, 0, 10);
		    CellRangeAddress cellRange2 = new CellRangeAddress(0, 0, 11, 15);
		    foglioDatiEnte.addMergedRegion(cellRange1);
		    foglioDatiEnte.addMergedRegion(cellRange2);
		    
		    // Seconda riga del foglio 'Dati Ente'
		    // Scrittura dell'intestazione delle sedici colonne del foglio    
		    riga = foglioDatiEnte.createRow(1);
		    for (int i=0; i < F1_TITOLI_COLONNE.length; i++) {
		    	cella = riga.createCell(i);
		    	cella.setCellStyle(stile);
		    	cella.setCellValue(new XSSFRichTextString(F1_TITOLI_COLONNE[i]));		    	
		    	foglioDatiEnte.setColumnWidth(i, (short) 256 * F2_LARGHEZZA_COLONNE[i]);		    	
		    }
		    
		    // Creazione del secondo foglio e scrittura righe di intestazione		
		 	Sheet foglioSchedaB = wb.createSheet(FOGLIO_MODELLO_SCHEDA_B);
		 	// Prima riga del foglio 'Scheda B'
		    // Gruppi di celle con un titolo
		    riga = foglioSchedaB.createRow(0);
		    cella = riga.createCell(0);
		    cella.setCellStyle(stile);
		    cella.setCellValue(new XSSFRichTextString(F2_COL));
		    cellRange1 = new CellRangeAddress(0, 0, 0, 25);
		    foglioSchedaB.addMergedRegion(cellRange1);
		     
		    // Seconda riga del foglio 'Scheda B'
		    // Scrittura dell'intestazione delle sedici colonne del foglio    
		    riga = foglioSchedaB.createRow(1);
		    for (int i=0; i < F2_TITOLI_COLONNE.length; i++) {
		    	cella = riga.createCell(i);
		        cella.setCellStyle(stile);
		        cella.setCellValue(new XSSFRichTextString(F2_TITOLI_COLONNE[i]));
		        foglioSchedaB.setColumnWidth(i, F2_LARGHEZZA_COLONNE[i]);
		        foglioSchedaB.setColumnWidth(i, (short) 256 * F2_LARGHEZZA_COLONNE[i]);		        
		    }
		    

		    this.populateExcel(wb, idProgramma, dizStiliExcel);

			ByteArrayOutputStream os = new ByteArrayOutputStream();			
			wb.write(os);		
			os.close();
			
			String base64=Base64.getEncoder().encodeToString(os.toByteArray());
		    risultato.setData(base64);
			return risultato;
			
		} catch (Exception e) {
			logger.error("Errore inaspettato durante il metodo reportSoggAggregatori", e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
			return risultato;
		}
		
	}


	private void populateExcel(Workbook wb, String idProgramma, DizionarioStiliExcel dizStiliExcel) throws Exception {
		List<ExportSogg> dati;

		Sheet foglioDatiEnte = wb.getSheet(FOGLIO_MODELLO_DATI_ENTE);
		Sheet foglioSchedaB  = wb.getSheet(FOGLIO_MODELLO_SCHEDA_B);
		
		if (foglioDatiEnte == null) {
			throw new Exception("Non trovato il foglio " + FOGLIO_MODELLO_DATI_ENTE);
		}
		
		
		dati = this.programmiMapper.getSoggettiAggregatori(Long.valueOf(idProgramma));
				
		if (dati != null && dati.size() > 0) {
			int indiceRigaCorrente = START_ROW;

			for (int i=0; i < dati.size(); i++) {
				if (i == 0) {
					// Scrittura della prima riga di valori estratti nel foglio Dati ente	          
					fillDatiEnte(foglioDatiEnte, (indiceRigaCorrente), dati.get(i), i, dizStiliExcel);
		        }
				fillSchedaB(foglioSchedaB, (indiceRigaCorrente), dati.get(i), i, dizStiliExcel);
				indiceRigaCorrente++;
			}
		}
		
	}
	

	
	
	private void fillDatiEnte(Sheet sheet, int indiceRigaCorrente, ExportSogg soggetto, int indiceRecordLotti, DizionarioStiliExcel dizStiliExcel)  {
		    Row riga = sheet.createRow(indiceRigaCorrente);
		    Cell c;

			c = riga.createCell(0,CellType.STRING);
			c.setCellValue(StringUtils.trimToEmpty(soggetto.getAmministrazione()));
			c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

		    c = riga.createCell(1,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCodiceFiscaleEnte()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(2,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCodiceIPA()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(3,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getDipartimento()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(4,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getUfficio()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(5,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getRegione()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(6,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getProvincia()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(7,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getIndirizzoEnte()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(8,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getTelefonoEnte()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(9,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getMailEnte()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(10,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getPecEnte()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(11,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getNomeReferente()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(12,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCognomeReferente()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(13,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCodiceFiscaleReferente()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(14,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getTelefonoReferente()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(15,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getMailReferente()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    

		}

	
		private void fillSchedaB(Sheet sheet, int indiceRigaCorrente, ExportSogg soggetto,  int indiceRecordLotti, DizionarioStiliExcel dizStiliExcel) {
		    Row riga = sheet.createRow(indiceRigaCorrente);
		    Cell c;
		    c = riga.createCell(0,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getNumeroInterventoCUI()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(1,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCodiceFiscaleAmministrazione()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(2,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getPrimaAnnualita()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(3,CellType.NUMERIC);
		    c.setCellValue("");
		    if(soggetto.getAnno()!=null)
		      c.setCellValue(soggetto.getAnno());		    
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.INTERO_ALIGN_RIGHT));
		    
		    c = riga.createCell(4,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCodiceCUP()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(5,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getAcquistoRicompreso()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

			c = riga.createCell(6,CellType.STRING);
			c.setCellValue(StringUtils.trimToEmpty(soggetto.getCuiLavoro()));
			c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

			c = riga.createCell(7,CellType.STRING);
			c.setCellValue(StringUtils.trimToEmpty(soggetto.getLottoFunzionale()));
			c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(8,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getAmbitoGeografico()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

		    

		    c = riga.createCell(9,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getSettore()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(10,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCPV()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(11,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getDescrizioneAcquisto()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(12,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getPriorita()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(13,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCfResponsabileProcedimento()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

			c = riga.createCell(14,CellType.NUMERIC);
			c.setCellValue("");
			if(soggetto.getDurataContratto()!=null) {
				c.setCellValue(soggetto.getDurataContratto());
			}
			c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.INTERO_ALIGN_RIGHT));
		    
		    c = riga.createCell(15,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getContrattoInEssere()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

		    
		    c = riga.createCell(16,CellType.NUMERIC);
		    c.setCellValue("");
		    if(soggetto.getStimaCostiProgrammaPrimoAnno()!=null) {		    
		    	c.setCellValue(soggetto.getStimaCostiProgrammaPrimoAnno());	
		    }		    
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
		    
		    c = riga.createCell(17,CellType.NUMERIC);
		    c.setCellValue("");
		    if(soggetto.getStimaCostiProgrammaSecondoAnno()!=null) {		    
		    	c.setCellValue(soggetto.getStimaCostiProgrammaSecondoAnno());	
		    }		    
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));

			c = riga.createCell(18,CellType.NUMERIC);
			c.setCellValue("");
			if(soggetto.getStimaCostiProgrammaTerzoAnno()!=null) {
				c.setCellValue(soggetto.getStimaCostiProgrammaTerzoAnno());
			}
			c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
		    
		    c = riga.createCell(19,CellType.NUMERIC);
		    c.setCellValue("");
		    if(soggetto.getCostiAnnualitaSuccessive()!=null) {		    
		    	c.setCellValue(soggetto.getCostiAnnualitaSuccessive());	
		    }		    
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
		    
		    c = riga.createCell(20,CellType.NUMERIC);
		    c.setCellValue("");
		    if(soggetto.getStimaCostiProgrammaTotale()!=null) {		    
		    	c.setCellValue(soggetto.getStimaCostiProgrammaTotale());	
		    }		    
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
		    
		    c = riga.createCell(21,CellType.NUMERIC);
		    c.setCellValue("");
		    if(soggetto.getImportoApportoCapitalePrivato()!=null) {		    
		    	c.setCellValue(soggetto.getImportoApportoCapitalePrivato());	
		    }		    
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.DECIMALE2_ALIGN_RIGHT));
		    
		    c = riga.createCell(22,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getTipoApportoCapitalePrivato()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(23,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getCodiceAUSA()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		    
		    c = riga.createCell(24,CellType.STRING);
		    c.setCellValue(StringUtils.trimToEmpty(soggetto.getDenomAmministrazioneDelegata()));
		    c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));

			c = riga.createCell(25,CellType.STRING);
			c.setCellValue(StringUtils.trimToEmpty(soggetto.getAquistoAggVar()));
			c.setCellStyle(dizStiliExcel.getStileExcel(DizionarioStiliExcel.STRINGA_ALIGN_LEFT));
		   
		    
		}
		
}
