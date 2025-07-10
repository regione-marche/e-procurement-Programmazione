import { cloneDeep } from 'lodash-es';

export class ArrayUtils {

    public static fill(array: Array<any>, value: any): Array<any> {
        let length = array == null ? 0 : array.length;
        if (!length) {
            return [];
        }

        let start = 0;
        let end = length;

        while (start < end) {
            array[start++] = cloneDeep(value);
        }
        return array;
    }

    public static download(filename: string, text: any, type: string) {
        let blob = this.base64ToBlob(text, type);
        let data = window.URL.createObjectURL(blob);
        let link = document.createElement('a');
        document.body.appendChild(link);
        link.href = data;
        link.download = filename;
        link.click();
        window.URL.revokeObjectURL(data);
        link.remove();
    }

    public static base64ToBlob(b64Data: any, type: string) {
        let byteCharacters = atob(b64Data); //data.file there
        let byteArrays = [];
        for (let offset = 0; offset < byteCharacters.length; offset += 512) {
            let slice = byteCharacters.slice(offset, offset + 512);

            let byteNumbers = new Array(slice.length);
            for (var i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }
            let byteArray = new Uint8Array(byteNumbers);
            byteArrays.push(byteArray);
        }
        return new Blob(byteArrays, { type: `application/${type}` });
    }
    
    // Method to get the original query without formatting
    getOriginalQuery(query: string): string {
        return query.replace(/\s+/g, ' ').trim();
    }
}