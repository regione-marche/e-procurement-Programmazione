
import { Inject, Injectable, Injector } from "@angular/core";
import { IHttpParams, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkRestHelperService } from "@maggioli/sdk-commons";
import { toString } from "lodash-es";
import { BaseResponse, ComboMultiResponse, ComboResponse, FileDownloadResponse } from "@maggioli/sdk-appaltiecontratti-base";
import { Observable } from "rxjs";
import { C0oggassDetailsResponse, C0oggassForm, C0oggassListRequest, C0oggassListResponse } from "../model/sdk-docassociati.model";


@Injectable({ providedIn: "root" })
export class SdkDocAssociatiService extends SdkBaseService {

  constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
    super(inj);
  }

  public tabellatiGetComboData(tabcod: string): Observable<ComboResponse> {
    let params: IHttpParams = {
        tabcod: toString(tabcod),   
    };
    return this.restHelper.get<ComboResponse>(`${this.appConfig.environment.COMUNICAZIONI_MS_BASE_URL}/tabellatiGetComboData`, params);
  }
  
  public tabellatiGetMultiComboData(tabcods: Array<string>): Observable<ComboMultiResponse> {
    let params: IHttpParams = {
        tabcods: toString(tabcods),   
    };
    return this.restHelper.get<ComboMultiResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/tabellatiGetMultiComboData`, params);
  }
    
    public c0oggassGetList(req: C0oggassListRequest): Observable<C0oggassListResponse> {
      let params: IHttpParams = {
          take: toString(req.take),   
          skip: toString(req.skip),   
          sort: toString(req.sort),   
          sortDirection: toString(req.sortDirection),   
          codein: toString(req.codein),   
          idProfilo: toString(req.idProfilo),   
          c0aprg: toString(req.c0aprg),
          c0aent: toString(req.c0aent),
          c0akey1: toString(req.c0akey1),
          c0akey2: toString(req.c0akey2),
          c0akey3: toString(req.c0akey3),
          c0akey4: toString(req.c0akey4),
          c0akey5: toString(req.c0akey5)
      };
      return this.restHelper.get<C0oggassListResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassGetList`, params);
    }

    public c0oggassGetCountByKeys(req: C0oggassListRequest): Observable<C0oggassListResponse> {
      let params: IHttpParams = {
          take: toString(req.take),   
          skip: toString(req.skip),   
          sort: toString(req.sort),   
          sortDirection: toString(req.sortDirection),   
          codein: toString(req.codein),   
          idProfilo: toString(req.idProfilo),   
          c0aprg: toString(req.c0aprg),
          c0aent: toString(req.c0aent),
          c0akey1: toString(req.c0akey1),
          c0akey2: toString(req.c0akey2),
          c0akey3: toString(req.c0akey3),
          c0akey4: toString(req.c0akey4),
          c0akey5: toString(req.c0akey5)
      };
      return this.restHelper.get<C0oggassListResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassGetCountByKeys`, params);
    }

    public c0oggassDownloadFile(codein: string, c0acod: number, c0aprg: string): Observable<FileDownloadResponse> {
      let params: IHttpParams = {
          codein: toString(codein),   
          c0acod: toString(c0acod),   
          c0aprg: toString(c0aprg),
      };
      return this.restHelper.get<FileDownloadResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassDownloadFile`, params);
    }

    public checkSign(codein: string, c0acod: number, c0aprg: string): Observable<any> {
      let params: IHttpParams = {
          codein: toString(codein),   
          c0acod: toString(c0acod),   
          c0aprg: toString(c0aprg),
      };
      return this.restHelper.get<any>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/checkSign`, params);
    }
    
    public c0oggassGetDetails(codein: string, c0acod: number): Observable<C0oggassDetailsResponse> {
      let params: IHttpParams = {
          codein: toString(codein), 
          c0acod: toString(c0acod),   
      };
      return this.restHelper.get<C0oggassDetailsResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassGetDetails`, params);
    }
    
    public c0oggassCreateInit(codein: string): Observable<C0oggassDetailsResponse> {
      let params: IHttpParams = {
          codein: toString(codein),   
      };
      return this.restHelper.get<C0oggassDetailsResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassCreateInit`, params);
    }
    
    public c0oggassCreate(request: C0oggassForm): Observable<C0oggassDetailsResponse>{
      return this.restHelper.post<C0oggassDetailsResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassCreate`, request);
    }
    
    public c0oggassUpdate(request: C0oggassForm): Observable<C0oggassDetailsResponse>{
      return this.restHelper.post<C0oggassDetailsResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassUpdate`, request);
    }
    
    public c0oggassDelete(codein: string, c0acod: number): Observable<BaseResponse> {
      let params: IHttpParams = {
          codein: toString(codein), 
          c0acod: toString(c0acod),   
      };
      return this.restHelper.get<BaseResponse>(`${this.appConfig.environment.DOCASSOCIATI_MS_BASE_URL}/c0oggassDelete`, params);
    }
    
    // #region Getters
    
    private get restHelper(): SdkRestHelperService {
        return this.injectable(SdkRestHelperService);
    }

    // #endregion
}
