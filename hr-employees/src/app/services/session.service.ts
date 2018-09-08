import { LocalStorageService } from 'angular-2-local-storage';
import { Injectable } from '@angular/core';
import { CookieService } from 'angular2-cookie/services/cookies.service';

@Injectable()
export class SessionService {

    constructor(private _lss: LocalStorageService, private _cookieService: CookieService) {
    }

    public restoreStatus<T>(key: WebModel, payload: T): T {
        let restoredPayload: T = this._lss.get<T>(key.toString());

        if (restoredPayload === null) {
            this._lss.set(key.toString(), payload);
        }

        return this._lss.get<T>(key.toString());
    }

    //---to save status
    public save<T>(key: WebModel, payload: T): void {
        this._lss.set(key.toString(), payload);
    }

    //--to retrieve data via key
    public retrieve<T>(key: WebModel): T {
        return this._lss.get<T>(key.toString())
    }

    //#region cookies

    public saveInCookie(key: WebModel, payload: string): void {
        this._cookieService.put(key.toString(), JSON.stringify(payload));
    }

    public retrieveFromCookie(key: WebModel): string {
        return this._cookieService.get(key.toString());
    }

    //#endregion

    //--to clean memory browser
    public logout(): void {
        this._cookieService.removeAll();
        this._lss.clearAll();
    }
}

export enum WebModel {
    LOGIN
}