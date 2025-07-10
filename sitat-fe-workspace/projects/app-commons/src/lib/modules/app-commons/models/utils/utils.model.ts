import { IDictionary } from '@maggioli/sdk-commons';

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

export interface ResponseDTO<T> {
	done: string,
	response?: T,
	messages?: Array<string>;
}