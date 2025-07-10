export interface ResponseResult<T> {
    data?: T;
    error?: string;
    errorData?: string;
    esito?: boolean;
}

export interface DynamicValue {
    codice?: number;
    descrizione?: string;
    value?: number;
}