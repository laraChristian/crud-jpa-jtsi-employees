import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { LoginResponse } from '../mesagges/loginResponse';
import { LoginRequest } from '../mesagges/loginRequest';
import { map, filter, catchError, mergeMap } from 'rxjs/operators';
import { Apis, Resources } from './serviceUtilities';


const API_URL = environment.apiUrl;

@Injectable()
export class LogginApiService {

  private _headers: Headers;
  private _options: RequestOptions;

  constructor(private _http: Http) {
    this._headers = new Headers({ 'Content-Type': 'application/json' });
    this._options = new RequestOptions({ headers: this._headers });
  }

  public loggin(_loginRequest: LoginRequest): Observable<LoginResponse> {
    console.log('start -- loggin method');
    return this._http.post(API_URL + Apis.EMPLOYEES_API + Resources.LOGGIN, _loginRequest, this._options).pipe(map(resp => resp.json(), catchError((error) => {
      console.log('error -- loggin: ' + error);
      return Observable.throw(error);
    })));
  }


}
