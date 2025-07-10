package it.appaltiecontratti.programmi.bl;

import it.appaltiecontratti.programmi.entity.*;
import it.appaltiecontratti.programmi.mapper.ProgrammiMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since giu 15, 2023
 */
@Component
public class ExportInterventiManager {

    private static final Logger LOGGER = LogManager.getLogger(ExportInterventiManager.class);

    @Autowired
    private ProgrammiMapper programmiMapper;

    public ResponseFile exportInterventiAquisti(final ExportInterventiAquistiForm form) {

        ResponseFile risultato = new ResponseFile();
        risultato.setEsito(true);

        if (form != null) {
            try {
                ProgrammaEntry programma = programmiMapper.getProgramma(form.getContri());

                if (programma == null) {
                    risultato.setErrorData(ResponseFile.ERROR_NOT_FOUND);
                    return risultato;
                }

                InterventiSearchForm searchForm = form.getSearchForm() != null ? form.getSearchForm() : new InterventiSearchForm();

                if (StringUtils.isNotBlank(searchForm.getNumeroCui())) {
                    searchForm.setNumeroCui("%" + searchForm.getNumeroCui().toUpperCase() + "%");
                }

                if (StringUtils.isNotBlank(searchForm.getCodInterno())) {
                    searchForm.setCodInterno("%" + searchForm.getCodInterno().toUpperCase() + "%");
                }

                if (StringUtils.isNotBlank(searchForm.getDescrizione())) {
                    searchForm.setDescrizione("%" + searchForm.getDescrizione().toUpperCase() + "%");
                }

                if (StringUtils.isNotBlank(searchForm.getCodiceCUP())) {
                    searchForm.setCodiceCUP("%" + searchForm.getCodiceCUP().toUpperCase() + "%");
                }

                List<String> testata = null;
                List<List<String>> body = null;

                if (programma.getTipoProg() == 1L) {
                    List<ExportInterventiQueryResult> results = programmiMapper.exportListaInterventiFiltrata(programma.getId(), searchForm);

                    testata = printTestataInterventi(form);
                    body = printBodyInterventi(form, results);

                } else {
                    List<ExportAcquistiQueryResult> results = programmiMapper.exportListaAcquistiFiltrata(programma.getId(), searchForm);

                    testata = printTestataAquisti(form, programma.getNorma());
                    body = printBodyAcquisti(form, results, programma.getNorma());
                }

                StringWriter writer = new StringWriter();
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setDelimiter(';').setQuoteMode(QuoteMode.ALL).setQuote('\"').build());
                csvPrinter.printRecord(testata);

                body.stream().forEach(r -> {
                    try {
                        csvPrinter.printRecord(r);
                    } catch (IOException e) {
                        LOGGER.error(e);
                    }
                });
                csvPrinter.flush();

                String output = writer.toString();
                risultato.setData(output);

            } catch (Exception e) {
                LOGGER.error(e);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseFile.ERROR_UNEXPECTED);
            }
        }
        return risultato;
    }

    public ResponseFile exportInterventiAcquistiNonRiproposti(final ExportInterventiAquistiNonRipropostiForm form) {

        ResponseFile risultato = new ResponseFile();
        risultato.setEsito(true);

        if (form != null) {
            try {
                ProgrammaEntry programma = programmiMapper.getProgramma(form.getContri());

                if (programma == null) {
                    risultato.setErrorData(ResponseFile.ERROR_NOT_FOUND);
                    return risultato;
                }

                List<ExportInterventiAcquistiNonRipropostiQueryResult> results = programmiMapper.exportListaInterventiAcquistiNonRiproposti(programma.getId());
                List<String> testata = printTestataInterventiNonRiproposti(form, programma.getTipoProg());
                List<List<String>> body = printBodyInterventiNonRiproposti(form, results);

                StringWriter writer = new StringWriter();
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setDelimiter(';').setQuoteMode(QuoteMode.ALL).setQuote('\"').build());
                csvPrinter.printRecord(testata);

                body.stream().forEach(r -> {
                    try {
                        csvPrinter.printRecord(r);
                    } catch (IOException e) {
                        LOGGER.error(e);
                    }
                });
                csvPrinter.flush();

                String output = writer.toString();
                risultato.setData(output);

            } catch (Exception e) {
                LOGGER.error(e);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseFile.ERROR_UNEXPECTED);
            }
        }
        return risultato;
    }

    private List<String> printTestataInterventi(final ExportInterventiAquistiForm form) {

        List<String> lista = new ArrayList<>();
        if (form.isShowCUI()) lista.add("Codice Unico Intervento - CUI");
        if (form.isShowCodInt()) lista.add("Cod. Int. Amm.ne");
        if (form.isShowCup()) lista.add("Codice CUP");
        if (form.isShowAnnualita())
            lista.add("Annualita' nella quale si prevede di dare avvio alla procedura di affidamento");
        if (form.isShowRup()) lista.add("Responsabile del procedimento");
        if (form.isShowLottoF()) lista.add("Lotto funzionale");
        if (form.isShowLavoroC()) lista.add("Lavoro complesso");
        if (form.isShowCodIstat()) {
            lista.add("Codice Istat Reg");
            lista.add("Codice Istat Prov");
            lista.add("Codice Istat Com");
        }
        if (form.isShowNuts()) lista.add("Localizzazione - codice NUTS");
        if (form.isShowTipologia()) lista.add("Tipologia");
        if (form.isShowCategoria()) lista.add("Settore e sottosettore intervento");
        if (form.isShowDescrizione()) lista.add("Descrizione dell'intervento");
        if (form.isShowPriorita()) lista.add("Livello di priorita'");
        if (form.isShowImportiAnnualita()) {
            lista.add("STIMA DEI COSTI DELL'INTERVENTO - Primo anno");
            lista.add("STIMA DEI COSTI DELL'INTERVENTO - Secondo anno");
            lista.add("STIMA DEI COSTI DELL'INTERVENTO - Terzo anno");
            lista.add("STIMA DEI COSTI DELL'INTERVENTO - Costi su annualita' successiva");
            lista.add("STIMA DEI COSTI DELL'INTERVENTO - Importo complessivo");
        }
        if (form.isShowImmobili())
            lista.add("Valore degli eventuali immobili di cui alla scheda C collegati all'intervento");
        if (form.isShowScadenza())
            lista.add("Scadenza temporale ultima per l'utilizzo dell'eventuale finanziamento derivante da contrazione di mutuo");
        if (form.isShowApportoCapitalePrivato()) {
            lista.add("Apporto di capitale privato - Importo");
            lista.add("Apporto di capitale privato- Tipologia");
        }
        if (form.isShowVariato())
            lista.add("Intervento aggiunto o variato a seguito di modifica programma");

        return lista;
    }

    private List<String> printTestataAquisti(final ExportInterventiAquistiForm form, final Long norma) {

        List<String> lista = new ArrayList<>();
        if (form.isShowCUI()) lista.add("Codice Unico Intervento - CUI");
        if (form.isShowAnnualita())
            lista.add("Annualita' nella quale si prevede di dare avvio alla procedura di affidamento");
        if (form.isShowCup()) lista.add("Codice CUP");
        if (form.isShowAcquistoRicompreso())
            lista.add("Acquisto ricompreso nell'importo complessivo di un lavoro o di altra acquisizione presente in programmazione di lavori, forniture e servizi");
        if (form.isShowCodCuiLavoroCollegato())
            lista.add("CUI lavoro o altra acquisizione nel cui importo complessivo l'acquisto e' eventualmente ricompreso");
        if (form.isShowLottoF()) lista.add("Lotto funzionale");
        if (form.isShowAmbitoGeografico()) lista.add("Ambito geografico di esecuzione dell'acquisto Codice NUTS");
        if (form.isShowSettore()) lista.add("Settore");
        if (form.isShowCpv()) lista.add("CPV");
        if (form.isShowDescrizione()) lista.add("Descrizione dell'acquisto");
        if (form.isShowPriorita()) lista.add("Livello di priorita'");
        if (form.isShowRup()) lista.add("Responsabile del procedimento");
        if (form.isShowDurataContratto()) lista.add("Durata del contratto");
        if (form.isShowContrattoEssere())
            lista.add("L'acquisto e' relativo a nuovo affidamento di contratto in essere");

        if (form.isShowImportiAnnualita()) {
            lista.add("STIMA DEI COSTI DELL'ACQUISTO - Primo anno");
            lista.add("STIMA DEI COSTI DELL'ACQUISTO - Secondo anno");
            if (norma == 3L) {
                lista.add("STIMA DEI COSTI DELL'ACQUISTO - Terzo anno");
            }
            lista.add("STIMA DEI COSTI DELL'ACQUISTO - Costi su annualita' successiva");
            lista.add("STIMA DEI COSTI DELL'ACQUISTO - Totale");
        }
        if (form.isShowApportoCapitalePrivato()) {
            lista.add("Apporto di capitale privato - Importo");
            lista.add("Apporto di capitale privato - Tipologia");
        }
        if (form.isShowAusa()) {
            lista.add("CENTRALE DI COMMITTENZA O SOGGETTO AGGREGATORE AL QUALE SI FARA' RICORSO PER L'ESPLETAMENTO DELLA PROCEDURA DI AFFIDAMENTO - codice AUSA");
            lista.add("CENTRALE DI COMMITTENZA O SOGGETTO AGGREGATORE AL QUALE SI FARA' RICORSO PER L'ESPLETAMENTO DELLA PROCEDURA DI AFFIDAMENTO - denominazione");
        }
        if (form.isShowVariato())
            lista.add("Acquisto aggiunto o variato a seguito di modifica programma");

        return lista;
    }

    private List<String> printTestataInterventiNonRiproposti(final ExportInterventiAquistiNonRipropostiForm form, final Long tipoProg) {

        List<String> lista = new ArrayList<>();
        if (form.isShowCui()) lista.add("Codice Unico Intervento - CUI");
        if (form.isShowCup()) lista.add("CUP");
        if (tipoProg == 1L) {
            if (form.isShowDescrizione()) lista.add("Descrizione dell'intervento");
            if (form.isShowImporto()) lista.add("Importo intervento");
        } else {
            if (form.isShowDescrizione()) lista.add("Descrizione dell'acquisto");
            if (form.isShowImporto()) lista.add("Importo acquisto");
        }
        if (form.isShowPriorita()) lista.add("Livello di priorita'");
        if (form.isShowMotivo()) lista.add("Motivo per il quale l'intervento non e' riproposto");

        return lista;
    }

    private List<List<String>> printBodyInterventi(final ExportInterventiAquistiForm form, final List<ExportInterventiQueryResult> results) {

        List<List<String>> righe = new ArrayList<>();

        DecimalFormat numberFormat = new DecimalFormat("#,##0.00");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        results.stream().forEach(r -> {

            List<String> colonne = new ArrayList<>();

            if (form.isShowCUI()) colonne.add(r.getCui());
            if (form.isShowCodInt()) colonne.add(r.getCodInt());
            if (form.isShowCup()) colonne.add(r.getCodCup());
            if (form.isShowAnnualita())
                colonne.add(r.getAnnRif().intValue() > 0 && r.getPrimaAnn().intValue() > 0 ? (r.getAnnRif().intValue() + r.getPrimaAnn().intValue() - 1) + "" : "");
            if (form.isShowRup()) colonne.add(r.getResponsabile());
            if (form.isShowLottoF())
                colonne.add(r.getLottoF().equals("2") ? "No" : (r.getLottoF().equals("1") ? "Si" : ""));
            if (form.isShowLavoroC())
                colonne.add(r.getLavoroC().equals("2") ? "No" : (r.getLavoroC().equals("1") ? "Si" : ""));
            if (form.isShowCodIstat()) {
                colonne.add(r.getCodIstat().length() == 9 ? r.getCodIstat().substring(0, 3) : "");
                colonne.add(r.getCodIstat().length() == 9 ? r.getCodIstat().substring(3, 6) : "");
                colonne.add(r.getCodIstat().length() == 9 ? r.getCodIstat().substring(6, 9) : "");
            }
            if (form.isShowNuts()) colonne.add(r.getCodNuts());
            if (form.isShowTipologia()) colonne.add(r.getTipologia());
            if (form.isShowCategoria()) colonne.add(r.getCategoria());
            if (form.isShowDescrizione()) colonne.add(r.getDescrizione().trim());
            if (form.isShowPriorita())
                colonne.add(r.getPriorita().intValue() > 0 ? r.getPriorita().intValue() + "" : "");
            if (form.isShowImportiAnnualita()) {
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCosti1Anno().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCosti2Anno().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCosti3Anno().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCostiAlAnno().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCostiTotale().setScale(2, RoundingMode.CEILING)) + "");
            }
            if (form.isShowImmobili())
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getImmobili().setScale(2, RoundingMode.CEILING)) + "");
            if (form.isShowScadenza())
                colonne.add(r.getScadenza() != null ? dateFormat.format(r.getScadenza()) + "" : "");
            if (form.isShowApportoCapitalePrivato()) {
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCapPrivImp().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getTipoImp());
            }
            if (form.isShowVariato())
                colonne.add(r.getVariato() != null ? r.getVariato().toString() : "");

            righe.add(colonne);
        });

        return righe;
    }

    private List<List<String>> printBodyAcquisti(final ExportInterventiAquistiForm form, final List<ExportAcquistiQueryResult> results, final Long norma) {

        List<List<String>> righe = new ArrayList<>();

        DecimalFormat numberFormat = new DecimalFormat("#,##0.00");


        results.stream().forEach(r -> {

            List<String> colonne = new ArrayList<>();

            if (form.isShowCUI()) colonne.add(r.getCui());
            if (form.isShowAnnualita())
                colonne.add(r.getAnnRif().intValue() > 0 && r.getPrimaAnn().intValue() > 0 ? (r.getAnnRif().intValue() + r.getPrimaAnn().intValue() - 1) + "" : "");
            if (form.isShowCup()) colonne.add(r.getCodCup());
            if (form.isShowAcquistoRicompreso())
                colonne.add(r.getAcquistoRicompreso().intValue() > 0 ? r.getAcquistoRicompreso().intValue() + "" : "");
            if (form.isShowCodCuiLavoroCollegato())
                colonne.add(r.getCodCuiLavoroCollegato());
            if (form.isShowLottoF())
                colonne.add(r.getLottoF().equals("2") ? "No" : (r.getLottoF().equals("1") ? "Si" : ""));
            if (form.isShowAmbitoGeografico()) colonne.add(r.getAmbitoGeografico());
            if (form.isShowSettore())
                colonne.add(r.getSettore().equals("L") ? "Lavori" : r.getSettore().equals("S") ? "Servizi" : r.getSettore().equals("F") ? "Forniture" : "");
            if (form.isShowCpv()) colonne.add(r.getCpv());
            if (form.isShowDescrizione()) colonne.add(r.getDescrizione().trim());
            if (form.isShowPriorita())
                colonne.add(r.getPriorita().intValue() > 0 ? r.getPriorita().intValue() + "" : "");
            if (form.isShowRup()) colonne.add(r.getResponsabileCognome() + " " + r.getResponsabileNome());
            if (form.isShowDurataContratto())
                colonne.add(r.getDurataContratto().intValue() > 0 ? r.getDurataContratto().intValue() + "" : "");
            if (form.isShowContrattoEssere())
                colonne.add(r.getContrattoEssere().equals("2") ? "No" : (r.getContrattoEssere().equals("1") ? "Si" : ""));

            if (form.isShowImportiAnnualita()) {
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCosti1Anno().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCosti2Anno().setScale(2, RoundingMode.CEILING)) + "");
                if (norma == 3L) {
                    colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCosti3Anno().setScale(2, RoundingMode.CEILING)) + "");
                }
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCostiAlAnno().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCostiTotale().setScale(2, RoundingMode.CEILING)) + "");
            }
            if (form.isShowApportoCapitalePrivato()) {
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getCapPrivImp().setScale(2, RoundingMode.CEILING)) + "");
                colonne.add(r.getTipoImp());
            }
            if (form.isShowAusa()) {
                colonne.add(r.getCodAusa().trim());
                colonne.add(r.getDenominazioneAusa().trim());
            }
            if (form.isShowVariato())
                colonne.add(r.getVariato() != null ? r.getVariato().toString() : "");

            righe.add(colonne);
        });

        return righe;
    }

    private List<List<String>> printBodyInterventiNonRiproposti(final ExportInterventiAquistiNonRipropostiForm form, final List<ExportInterventiAcquistiNonRipropostiQueryResult> results) {

        List<List<String>> righe = new ArrayList<>();

        DecimalFormat numberFormat = new DecimalFormat("#,##0.00");


        results.stream().forEach(r -> {

            List<String> colonne = new ArrayList<>();

            if (form.isShowCui()) colonne.add(r.getCodiceUnico());
            if (form.isShowCup()) colonne.add(r.getCup());
            if (form.isShowDescrizione()) colonne.add(r.getDescrizione().trim());
            if (form.isShowImporto())
                colonne.add(r.getContri().intValue() == 0 ? "" : numberFormat.format(r.getImporto().setScale(2, RoundingMode.CEILING)) + "");
            if (form.isShowPriorita())
                colonne.add(r.getPriorita().intValue() > 0 ? r.getPriorita().intValue() + "" : "");
            if (form.isShowMotivo()) colonne.add(r.getMotivo().trim());

            righe.add(colonne);
        });

        return righe;
    }
}
