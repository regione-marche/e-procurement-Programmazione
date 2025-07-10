import { BaseForm, BaseListRequest, BaseResponse, WDocdigForm } from "@maggioli/sdk-appaltiecontratti-base";

export class C0oggassParams {
    c0aprg?: string;
    c0aent?: string;
    c0akey1?: string;
    c0akey2?: string;
    c0akey3?: string;
    c0akey4?: string;
    c0akey5?: string;
}

export class C0oggass {
    c0acod?: number;
    c0adat?: Date;
    c0adatto?: Date;
    c0adirogg?: string;
    c0adprot?: Date;
    c0aent?: string;
    c0akey1?: string;
    c0akey2?: string;
    c0akey3?: string;
    c0akey4?: string;
    c0akey5?: string;
    c0anatto?: string;
    c0annote?: string;
    c0anomogg?: string;
    c0anprot?: string;
    c0aprg?: string;
    c0apubli?: string;
    c0ascad?: Date;
    c0asubdir?: string;
    c0atipace?: number;
    c0atipdoc?: string;
    c0atipo?: string;
    c0atit?: string;
    c0atpub?: number;
    c0avisib?: number;
    c0pdaid?: string;
    c0pdamd5?: string;
}

export class C0oggassDetailsResponse extends BaseResponse {
    item?: C0oggassDto;
    maxAllowedFileSize?: number;
    allowedFileExtensions?: string;
}

export class C0oggassDto extends C0oggass {
}

export class C0oggassForm extends BaseForm {
    item?: C0oggass;
    wDocdigItem?: WDocdigForm;
}

export class C0oggassListDto extends C0oggass {
}

export class C0oggassListRequest extends BaseListRequest {
    c0aprg?: string;
    c0aent?: string;
    c0akey1?: string;
    c0akey2?: string;
    c0akey3?: string;
    c0akey4?: string;
    c0akey5?: string;
}

export class C0oggassListResponse extends BaseResponse {
    data?: Array<C0oggassListDto>;
}


export class CheckSignResponse extends BaseResponse {
    signatory?: Array<SignatoryDto>;
}

export class SignatoryDto{
    date?: string;
    signatory?: string;
    status?: string;
}