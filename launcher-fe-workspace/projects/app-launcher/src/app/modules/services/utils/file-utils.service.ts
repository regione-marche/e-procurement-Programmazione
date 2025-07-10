import { Injectable, Injector } from '@angular/core';
import { SdkBase64Helper, SdkBaseService } from '@maggioli/sdk-commons';
import { isEmpty } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class FileUtilsService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    public downloadFileUrl(url: string): void {
        let link = document.createElement('a');
        document.body.appendChild(link);
        link.target = '_blank';
        link.href = url;
        link.click();
        link.remove();
    }

    public downloadFileBinary(binary: string, fileType: string = 'application/pdf', decode: boolean = true, titolo: string = 'download'): void {
        let arrBuffer: Uint8Array;
        if (decode === true) {
            arrBuffer = this.sdkBase64Helper.base64ToArrayBuffer(binary);
        } else {
            arrBuffer = this.sdkBase64Helper.stringToArrayBuffer(binary);
        }
        let newBlob = new Blob([arrBuffer], { type: fileType });
        let data = window.URL.createObjectURL(newBlob);
        let link = document.createElement('a');
        document.body.appendChild(link);
        link.href = data;
        link.download = titolo;
        link.click();
        window.URL.revokeObjectURL(data);
        link.remove();
    }

    public getExtFromFileName(fileName: string): string {
        if (fileName != null && !isEmpty(fileName) && !fileName.endsWith('.')) {
            const lastIndex: number = fileName.lastIndexOf('.');
            if (lastIndex != -1) {
                const ext: string = fileName.substring(lastIndex + 1);
                return ext;
            }
        }
        return null;
    }

    // #region Getters

    private get sdkBase64Helper(): SdkBase64Helper { return this.injectable(SdkBase64Helper) }

    // #endregion

}