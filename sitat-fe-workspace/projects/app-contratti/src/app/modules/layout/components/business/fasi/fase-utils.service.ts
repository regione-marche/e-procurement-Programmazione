import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService } from '@maggioli/sdk-commons';
import { get, isEmpty } from 'lodash-es';

import { BaseFaseService } from '../../../../models/fasi/fasi.model';
import { ElencoImpreseInvitatePartecipantiService } from '../../../../services/fasi/elenco-impr-inv-parte.service';
import { FaseAccordoBonarioService } from '../../../../services/fasi/fase-accordo-bonario.service';
import { FaseAdesioneAccordoQuadroService } from '../../../../services/fasi/fase-adesione-accordo-quadro.service';
import { FaseAggiudicazioneSempService } from '../../../../services/fasi/fase-aggiudicazione-semp.service';
import { FaseAggiudicazioneService } from '../../../../services/fasi/fase-aggiudicazione.service';
import { FaseAvanzamentoSempService } from '../../../../services/fasi/fase-avanzamento-semp.service';
import { FaseAvanzamentoService } from '../../../../services/fasi/fase-avanzamento.service';
import { FaseCantieriService } from '../../../../services/fasi/fase-cantieri.service';
import { FaseCollaudoService } from '../../../../services/fasi/fase-collaudo.service';
import { FaseComunicazioneEsitoService } from '../../../../services/fasi/fase-comunicazione.service';
import { FaseConclusioneSempService } from '../../../../services/fasi/fase-conclusione-semp.service';
import { FaseConclusioneService } from '../../../../services/fasi/fase-conclusione.service';
import { FaseInadempienzeSicurezzaService } from '../../../../services/fasi/fase-inadempienze-sicurezza.service';
import { FaseInfortuniService } from '../../../../services/fasi/fase-infortuni.service';
import { FaseInidoneitaContributivaService } from '../../../../services/fasi/fase-inidoneita-contributiva.service';
import { FaseInidoneitaTecnicoProfService } from '../../../../services/fasi/fase-inidoneita-tecnico-prof.service';
import { FaseInizialeSempService } from '../../../../services/fasi/fase-iniziale-semp.service';
import { FaseInizialeService } from '../../../../services/fasi/fase-iniziale.service';
import { FaseModificaContrattualeService } from '../../../../services/fasi/fase-modifica-contrattuale.service';
import { FaseRecessoService } from '../../../../services/fasi/fase-recesso.service';
import { FaseSospensioneService } from '../../../../services/fasi/fase-sospensione.service';
import { FaseStipulaAccordoQuadroService } from '../../../../services/fasi/fase-stipula-accordo-quadro.service';
import { FaseSubappaltoService } from '../../../../services/fasi/fase-subappalto.service';
import { FaseSottoScrizioneContrattoService } from '../../../../services/fasi/fase-sottoscrizione-contratto.service';
import { FaseConclusioneAffidamentiDirettiService } from '../../../../services/fasi/fase-conclusione-affidamenti-diretti.service';
import { FaseRipresaPrestazioneService } from '../../../../services/fasi/fase-ripresa-prestazione.service';
import { FaseSuperamentoQuartoContrattualeService } from '../../../../services/fasi/fase-superamento-quarto-contrattuale.service';
import { FaseRichiestaSubappaltoService } from '../../../../services/fasi/fase-richiesta-subappalto.service';
import { FaseEsitoSubappaltoService } from '../../../../services/fasi/fase-esito-subappalto.service';
import { FaseConclusioneSubappaltoService } from '../../../../services/fasi/fase-conclusione-subappalto.service';
import { FaseIncarichiProfessionaliService } from '../../../../services/fasi/fase-incarichi-professionali.service';
import { FaseVariazioneAggiudicatariService } from '../../../../services/fasi/fase-variazione-aggiudicatari.service';

@Injectable({ providedIn: 'root' })
export class FaseUtilsService extends SdkBaseService {

    private fasiServiceMap: IDictionary<BaseFaseService>;

    constructor(inj: Injector) {
        super(inj);
        this.initMap();
    }

    // #region Public

    public getFaseService(codiceFase: string): BaseFaseService {
        if (!isEmpty(codiceFase)) {
            return get(this.fasiServiceMap, codiceFase);
        }
        return undefined;
    }

    // #endregion

    // #region Private

    private initMap(): void {
        this.fasiServiceMap = {
            '101': this.faseElencoImprInvParteService,
            '984': this.faseComunicazioneEsitoService,
            '1': this.faseAggiudicazioneService,
            '987': this.faseAggiudicazioneSempService,
            '12': this.faseAdesioneAccordoQuadroService,
            '2': this.faseInizialeService,
            '986': this.faseInizialeSempService,
            '11': this.faseStipulaAccordoQuadroService,
            '3': this.faseAvanzamentoService,
            '102': this.faseAvanzamentoSempService,
            '4': this.faseConclusioneService,
            '985': this.faseConclusioneSempService,
            '5': this.faseCollaudoService,
            '6': this.faseSospensioneService,
            '7': this.faseModificaContrattualeService,
            '8': this.faseAccordoBonarioService,
            '9': this.faseSubappaltoService,
            '10': this.faseRecessoService,
            '997': this.faseInidoneitaTecnicoProfService,
            '996': this.faseInidoneitaContributivaService,
            '995': this.faseInadempienzeSicurezzaService,
            '994': this.faseInfortuniService,
            '998': this.faseCantieriService,
            '13': this.faseSottoScrizioneContrattoService,
            '14': this.faseSuperamentoQuartoContrattualeService,
            '15': this.faseRipresaPrestazioniService,
            '16': this.faseRichiestaSubappaltoService,
            '17': this.faseEsitoSubappaltoService,
            '18': this.faseConclusioneSubappaltoService,
            '19': this.faseConclusioneAffidamentiDirettiService,
            '20': this.faseIncarichiProfessionaliService,
            '21': this.faseVariazioneAggiudicatariService
        };
    }

    // #endregion

    // #region Getters

    private get faseIncarichiProfessionaliService(): FaseIncarichiProfessionaliService { return this.injectable(FaseIncarichiProfessionaliService) }

    private get faseConclusioneSubappaltoService(): FaseConclusioneSubappaltoService { return this.injectable(FaseConclusioneSubappaltoService) }

    private get faseEsitoSubappaltoService(): FaseEsitoSubappaltoService { return this.injectable(FaseEsitoSubappaltoService) }

    private get faseRichiestaSubappaltoService(): FaseRichiestaSubappaltoService { return this.injectable(FaseRichiestaSubappaltoService) }

    private get faseSuperamentoQuartoContrattualeService(): FaseSuperamentoQuartoContrattualeService { return this.injectable(FaseSuperamentoQuartoContrattualeService) }

    private get faseRipresaPrestazioniService(): FaseRipresaPrestazioneService { return this.injectable(FaseRipresaPrestazioneService) }

    private get faseConclusioneAffidamentiDirettiService(): FaseConclusioneAffidamentiDirettiService { return this.injectable(FaseConclusioneAffidamentiDirettiService) }

    private get faseSottoScrizioneContrattoService(): FaseSottoScrizioneContrattoService { return this.injectable(FaseSottoScrizioneContrattoService) }

    private get faseInizialeService(): FaseInizialeService { return this.injectable(FaseInizialeService) }

    private get faseAggiudicazioneService(): FaseAggiudicazioneService { return this.injectable(FaseAggiudicazioneService) }

    private get faseComunicazioneEsitoService(): FaseComunicazioneEsitoService { return this.injectable(FaseComunicazioneEsitoService) }

    private get faseAggiudicazioneSempService(): FaseAggiudicazioneSempService { return this.injectable(FaseAggiudicazioneSempService) }

    private get faseElencoImprInvParteService(): ElencoImpreseInvitatePartecipantiService { return this.injectable(ElencoImpreseInvitatePartecipantiService) }

    private get faseAvanzamentoService(): FaseAvanzamentoService { return this.injectable(FaseAvanzamentoService) }

    private get faseSospensioneService(): FaseSospensioneService { return this.injectable(FaseSospensioneService) }

    private get faseAvanzamentoSempService(): FaseAvanzamentoSempService { return this.injectable(FaseAvanzamentoSempService) }

    private get faseModificaContrattualeService(): FaseModificaContrattualeService { return this.injectable(FaseModificaContrattualeService) }

    private get faseAdesioneAccordoQuadroService(): FaseAdesioneAccordoQuadroService { return this.injectable(FaseAdesioneAccordoQuadroService); }

    private get faseStipulaAccordoQuadroService(): FaseStipulaAccordoQuadroService {
        return this.injectable(FaseStipulaAccordoQuadroService);
    }

    private get faseConclusioneService(): FaseConclusioneService {
        return this.injectable(FaseConclusioneService);
    }

    private get faseInizialeSempService(): FaseInizialeSempService { return this.injectable(FaseInizialeSempService); }

    private get faseConclusioneSempService(): FaseConclusioneSempService {
        return this.injectable(FaseConclusioneSempService);
    }

    private get faseAccordoBonarioService(): FaseAccordoBonarioService { return this.injectable(FaseAccordoBonarioService); }

    private get faseCollaudoService(): FaseCollaudoService {
        return this.injectable(FaseCollaudoService);
    }

    private get faseSubappaltoService(): FaseSubappaltoService { return this.injectable(FaseSubappaltoService); }

    private get faseRecessoService(): FaseRecessoService { return this.injectable(FaseRecessoService); }

    private get faseInidoneitaTecnicoProfService(): FaseInidoneitaTecnicoProfService { return this.injectable(FaseInidoneitaTecnicoProfService) }

    private get faseInidoneitaContributivaService(): FaseInidoneitaContributivaService {
        return this.injectable(FaseInidoneitaContributivaService);
    }

    private get faseInadempienzeSicurezzaService(): FaseInadempienzeSicurezzaService { return this.injectable(FaseInadempienzeSicurezzaService) }

    private get faseInfortuniService(): FaseInfortuniService { return this.injectable(FaseInfortuniService) }

    private get faseCantieriService(): FaseCantieriService { return this.injectable(FaseCantieriService) }

    private get faseVariazioneAggiudicatariService(): FaseVariazioneAggiudicatariService { return this.injectable(FaseVariazioneAggiudicatariService) }

    // #endregion
}
