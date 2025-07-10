import { BaseForm, BaseListRequest, BaseResponse } from "./sdk-base.model";

export class WDocdig {
  id?: WDocdigPK;
  digdesdoc?: string;
  digent?: string;
  digfirma?: string;
  digkey1?: string;
  digkey2?: string;
  digkey3?: string;
  digkey4?: string;
  digkey5?: string;
  dignomdoc?: string;
  digogg?: string;
  digtipdoc?: string;
  firmacheck?: string;
  firmacheckts?: Date;
}

export class WDocdigDetailsResponse extends BaseResponse {
  item?: WDocdigDto;
  maxAllowedFileSize?: number;
  allowedFileExtensions?: string;
}

export class WDocdigDto extends WDocdig {
}

export class WDocdigForm extends BaseForm {
  id?: WDocdigPK;
  digent?: string;
  digkey1?: string;
  digkey2?: string;
  digkey3?: string;
  digkey4?: string;
  digkey5?: string;
  digdesdoc?: string;
  dignomdoc?: string;
  digoggBase64?: string;
}

export class WDocdigListDto {
  idprg?: string;
  iddocdig?: number;
  digdesdoc?: string;
  dignomdoc?: string;
}

export class WDocdigListRequest extends BaseListRequest {
  idprg?: string;
  digent?: string;
  digkey1?: string;
  digkey2?: string;
  digkey3?: string;
  digkey4?: string;
  digkey5?: string;
}

export class WDocdigListResponse extends BaseResponse {
  data?: Array<WDocdigListDto>;
}

export class WDocdigPK {
  idprg?: string;
  iddocdig?: number;
}