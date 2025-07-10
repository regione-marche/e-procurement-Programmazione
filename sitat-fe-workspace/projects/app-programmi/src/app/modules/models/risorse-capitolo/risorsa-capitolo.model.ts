import { ResponseResult } from '@maggioli/app-commons';

export interface ResponseListaRisorsaCapitolo extends ResponseResult<Array<RisorsaCapitoloBaseEntry>> {
    totalCount?: number;
}

export interface RisorsaCapitoloBaseEntry {
    id?: number;
    idIntervento?: number;
    idProgramma?: number;
    importoComplessivo1?: number;
    importoComplessivo2?: number;
    importoComplessivo3?: number;
    importoComplessivoSucc?: number;
}

export interface RisorsaCapitoloEntry {
    altreRisDisp1?: number;
    altreRisDisp2?: number;
    altreRisDisp3?: number;
    altreRisDispSucc?: number;
    entrateContrMutuo1?: number;
    entrateContrMutuo2?: number;
    entrateContrMutuo3?: number;
    entrateContrMutuoSucc?: number;
    entrateDestVincLegge1?: number;
    entrateDestVincLegge2?: number;
    entrateDestVincLegge3?: number;
    entrateDestVincLeggeSucc?: number;
    id?: number;
    idIntervento?: number;
    idProgramma?: number;
    impFinanz1?: number;
    impFinanz2?: number;
    impFinanz3?: number;
    impFinanzSucc?: number;
    impegniSpesa?: string;
    importoComplessivo1?: number;
    importoComplessivo2?: number;
    importoComplessivo3?: number;
    importoComplessivoSucc?: number;
    importoIva1?: number;
    importoIva2?: number;
    importoIva3?: number;
    importoIvaSucc?: number;
    importoRisFinanz?: number;
    importoRisFinanzAltro?: number;
    importoRisFinanzStato?: number;
    note?: string;
    numCapBilancio?: string;
    speseSostenute?: number;
    stanziamentiBilancio1?: number;
    stanziamentiBilancio2?: number;
    stanziamentiBilancio3?: number;
    stanziamentiBilancioSucc?: number;
    verificaCoerenza?: string;
}

export interface RisorsaDiCapitoloInsertForm {
    altreRisDisp1?: number;
    altreRisDisp2?: number;
    altreRisDisp3?: number;
    altreRisDispSucc?: number;
    entrateContrMutuo1?: number;
    entrateContrMutuo2?: number;
    entrateContrMutuo3?: number;
    entrateContrMutuoSucc?: number;
    entrateDestVincLegge1?: number;
    entrateDestVincLegge2?: number;
    entrateDestVincLegge3?: number;
    entrateDestVincLeggeSucc?: number;
    id?: number;
    idIntervento?: number;
    idProgramma?: number;
    impFinanz1?: number;
    impFinanz2?: number;
    impFinanz3?: number;
    impFinanzSucc?: number;
    impegniSpesa?: string;
    importoComplessivo1?: number;
    importoComplessivo2?: number;
    importoComplessivo3?: number;
    importoComplessivoSucc?: number;
    importoIva1?: number;
    importoIva2?: number;
    importoIva3?: number;
    importoIvaSucc?: number;
    importoRisFinanz?: number;
    importoRisFinanzAltro?: number;
    importoRisFinanzStato?: number;
    note?: string;
    numCapBilancio?: string;
    speseSostenute?: number;
    stanziamentiBilancio1?: number;
    stanziamentiBilancio2?: number;
    stanziamentiBilancio3?: number;
    stanziamentiBilancioSucc?: number;
    verificaCoerenza?: string;
}