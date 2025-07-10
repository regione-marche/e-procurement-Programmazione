import { IDictionary } from "@maggioli/sdk-commons";

export class WizardPages {
  page1?: string;
  page2?: string;
  page3?: string;
  resultPage?: string;
}

export class CrudPages {
  parentListPage?: string;
  listPage?: string;
  detailsPage?: string;
  createPage?: string;
  editPage?: string;codein?: string;
}


export class BaseForm {
  stazioneAppaltante?: string;
  syscon?: number;
  idProfilo?: string;
}


export class BaseResponse {
  result?: string;
  errorData?: string;
  esito?: boolean;
  infoMessaggi?: string[];
  totalCount?: number;
}

export class ResponseResult<T> extends BaseResponse {
  data?: T;
}

export class ResponseResult1 extends BaseResponse {
  data?: string;
  esito?: boolean;
}

export class BaseRequest {
  codein?: string;
  idProfilo?: string;
}

export class ComboDto {
  key?: string;
  value?: string;
  value1?: string;
  codistat?: string;
}

export class ComboDtoGestioneUtenti {
  codice?: string;
  descrizione?: string;
}

export interface ComboDtoCacheInfo {
  data: Array<ComboDto>;
  expiration?: Date;
}


export class BaseListRequest extends BaseRequest {
  take?: number;
  skip?: number;
  sort?: string;
  sortDirection?: string;
}

export class DynamicValue {
  codice?: number;
  descrizione?: string;
  value?: number;
}


export class ComboResponseParametric<T> extends BaseResponse {
  data?: Array<T>;
}

export class ComboMultiResponseParametric<T> extends BaseResponse {
  data?: Map<string, Array<T>>;
}

export class ComboMultiResponse extends BaseResponse {
  data?: IDictionary<Array<ComboDto>>;
}

export class ComboResponse extends BaseResponse {
  data?: Array<ComboDto>;
}

export interface HomePageCard {
  code?: string;
  icon?: string;
  label: string;
  slug?: string;
  url?: string;
  params?: IDictionary<string>;
  oggettoProtezione?: string;
  // chiave per il parametro addizionale della label
  additionalParamsKey?: string;
}

export interface HomePageBox {
  title: string;
  description?: string;
  cards: Array<HomePageCard>;
}

export class FileDownloadResponse extends BaseResponse {
  fileContent?: string;
  fileName?: string;
}

export class StringResponse extends BaseResponse {
  data?: string;
}

export interface InfoCampo {
  campo?: string;
  chiaveEntita?: Array<string>;
  descrizione?: string;
  descrizioneEntita?: string;
  descrizioneSchema?: string;
  entita?: string;
  formato?: string;
  mnemonico?: string;
  schema?: string;
  valoriTabellati?: Array<string>;
}
