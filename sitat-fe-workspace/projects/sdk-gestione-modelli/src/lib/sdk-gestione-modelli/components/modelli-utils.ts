import { IDictionary } from "@maggioli/sdk-commons";
import { ArgomentoDesc } from "../model/lib.model";
import { SdkComboBoxItem } from "@maggioli/sdk-controls";

export class ModelliUtils {
	static whichDescrizione(key: string): string {
		switch (key) {
			case "ESPR":
				return "Espropri";
			case "GENE":
				return "Archivi Generali";
			case "GENEWEB":
				return "Archivi Generali Web";
			default:
				return key;
		}
	}

	static whichDescrizioneArgomento(
		key: string,
		listaArgomenti: Array<ArgomentoDesc>
	): string {
		const arg: ArgomentoDesc = listaArgomenti.find(
			(argomento) => argomento.c0enom.split(".")[1] === key
		);
		return `${key} -  ${arg.c0earg} - ${arg.c0edes}`;
	}
	static extractMaxFileSize(maxSize: string | undefined): string {
		const maxSizeRegex = /(\d+).*?(KB|MB|GB)/i;
		if (!maxSize) {
			return "0 MB";
		}
		const match = maxSize.match(maxSizeRegex);
		if (!match) {
			return "0 MB";
		}

		const numericPart = parseInt(match[1], 10);
		if (isNaN(numericPart)) {
			return "0 MB";
		}

		return `${numericPart} ${match[2].toUpperCase()}`;
	}
	static createArgomentiArray(data: IDictionary<any>): string[] {
		return Object.entries(data).flatMap(([schema, items]) =>
			(items as string[]).map((item) => `${item}.${schema}`)
		);
	}

	static mapArgomentoDesc(
		argomentiDesc: Array<any>
	): Array<{ key: string; value: string }> {
		return argomentiDesc.map((argomento) => {
			const c0enom = argomento.c0enom.split(".")[0]
			return {key: c0enom,
			value: `${c0enom}  ${argomento.c0earg === c0enom ? "" : ' - ' + argomento.c0earg} - ${ argomento.c0edes}`,
		}
		});
	}
	static initializeData(results: {
		defaultSchema: any;
		defaultArgomento: any;
		inizNuovoModello: { data: IDictionary<any> };
		tabellati: any;
	}): {
		defaultSchema: any;
		defaultArgomento: any;
		schemaTabelleDictionary: IDictionary<any>;
		listSchema: Array<{ key: string; value: string }>;
	} {
		const {
			defaultSchema,
			defaultArgomento,
			inizNuovoModello: { data: schemaTabelleDictionary },
		} = results;
		const listSchema = Object.keys(schemaTabelleDictionary).map((key) => ({
			key,
			value: `${key} - ${ModelliUtils.whichDescrizione(key)}`,
		}));
		return {
			defaultSchema,
			defaultArgomento,
			schemaTabelleDictionary,
			listSchema,
		};
	}
	static createComboBoxListGenerator(
		schemaTabelleDictionary: IDictionary<any>,
		listArgomentoDesc: Array<{ key: string; value: string }>
	): (field: { data: { key: string } }) => Array<SdkComboBoxItem> {
		return (field: { data: { key: string } }): Array<SdkComboBoxItem> => {
			const listaTabelle = schemaTabelleDictionary[field.data.key];
			return listaTabelle.map((key) => ({
				key: String(key),
				value: listArgomentoDesc.find((item) => item.key === key)?.value,
			}));
		};
	}
}
