import { IDictionary } from '@maggioli/sdk-commons';

export interface TabellatoNuts {
	codice?: string;
	paese?: string;
	area?: string;
	regione?: string;
	provincia?: string;
	descrizione?: string;
	order?: number;
}

export interface DataNuts {
	paesi?: Array<TabellatoNuts>;
	aree?: Array<TabellatoNuts>;
	regioni?: Array<TabellatoNuts>;
	province?: Array<TabellatoNuts>;
}

export const AREA_ORDER_MAP: IDictionary<number> = {
	C: 0,
	H: 1,
	I: 2,
	F: 3,
	G: 4,
	Z: 5
}