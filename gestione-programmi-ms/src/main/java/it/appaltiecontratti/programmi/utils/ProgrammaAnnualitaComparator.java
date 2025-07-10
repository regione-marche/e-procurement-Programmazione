package it.appaltiecontratti.programmi.utils;

import it.appaltiecontratti.programmi.entity.ProgrammaBaseEntry;

import java.util.Comparator;

/**
 * @author Cristiano Perin <cristiano.perin@akera.it>
 * @version 1.0.0
 * @since lug 06, 2023
 */
public final class ProgrammaAnnualitaComparator {

    public ProgrammaAnnualitaComparator() {
        throw new AssertionError("La classe deve rimanere statica");
    }

    public static Comparator<ProgrammaBaseEntry> createProgrammaAnnualitaOrderComparator() {
        return Comparator.comparingLong(ProgrammaAnnualitaComparator::parseProgrammaToNumber);
    }

    private static Long parseProgrammaToNumber(ProgrammaBaseEntry input) {

        final String idProgramma = input.getIdProgramma();
        final String idSubstr = idProgramma.substring(idProgramma.length() - 3);
        final Long idCompare = Long.valueOf(idSubstr);

        return idCompare;
    }
}
