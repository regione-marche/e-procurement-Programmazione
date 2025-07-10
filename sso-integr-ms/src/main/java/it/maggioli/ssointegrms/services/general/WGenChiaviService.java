package it.maggioli.ssointegrms.services.general;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface WGenChiaviService {

	Long getNextId(final String tabella);

	Long getNextId(final String tabella, Long increment);
}
