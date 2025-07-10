package it.maggioli.ssointegrms.services.general;

import it.maggioli.ssointegrms.dto.ListaDettaglioTabellatoFormDTO;
import it.maggioli.ssointegrms.dto.ResponseDTO;
import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaTabellatiFormDTO;
import it.maggioli.ssointegrms.dto.Tab0DTO;
import it.maggioli.ssointegrms.dto.Tab1DTO;
import it.maggioli.ssointegrms.dto.Tab2DTO;
import it.maggioli.ssointegrms.dto.Tab3DTO;
import it.maggioli.ssointegrms.dto.Tab5DTO;

/**
 * 
 * @author Cristiano Perin
 *
 */
public interface TabellatiService {

	ResponseListaDTO loadListaTabellati(final RicercaTabellatiFormDTO form);

	ResponseListaDTO loadListaDettaglioTabellato(final ListaDettaglioTabellatoFormDTO loadForm);

	Object getDettaglioTabellato(final String provenienza, final String codiceTabellato, final String identificativo);

	ResponseDTO insertTab0(final String codiceTabellato, final Tab0DTO form);

	ResponseDTO insertTab1(final String codiceTabellato, final Tab1DTO form);

	ResponseDTO insertTab2(final String codiceTabellato, final Tab2DTO form);

	ResponseDTO insertTab3(final String codiceTabellato, final Tab3DTO form);

	ResponseDTO insertTab5(final String codiceTabellato, final Tab5DTO form);

	ResponseDTO updateTab0(final String codiceTabellato, final String identificativo, final Tab0DTO form);

	ResponseDTO updateTab1(final String codiceTabellato, final Long identificativo, final Tab1DTO form);

	ResponseDTO updateTab2(final String codiceTabellato, final String identificativo, final Tab2DTO form);

	ResponseDTO updateTab3(final String codiceTabellato, final String identificativo, final Tab3DTO form);

	ResponseDTO updateTab5(final String codiceTabellato, final String identificativo, final Tab5DTO form);

	ResponseDTO deleteTabellato(final String provenienza, final String codiceTabellato, final String identificativo);
}
