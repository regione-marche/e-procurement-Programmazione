package it.appaltiecontratti.inbox.bl;

import it.appaltiecontratti.inbox.entity.responses.FullResponseLoaderAppalto;
import it.avlp.simog.massload.xmlbeans.LoaderAppalto;

public interface LoaderAppaltoService {

    FullResponseLoaderAppalto cancellaScheda(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                                             final Long numProgressivoFase, final Long idFlusso) throws Exception;

    boolean pubblicaScheda(final Long codGaraI, final Long codLottoI, final Long faseEsecuzioneI,
                           final Long numProgressivoFaseI, final Long idFlusso);

    String getCUI(final Long codGara, final Long codLotto, final Long faseEsecuzione, final Long numProgressivoFase);

    boolean cancellaDatiComuni(final Long codGara, final Long codLotto) throws Exception;
    
    public LoaderAppalto.TrasferimentoDati getTrasferimentoDati(final Long codGara, final Long codLotto,
            final Long faseEsecuzione, final Long numProgressivoFase, final boolean eliminaScheda);
}
