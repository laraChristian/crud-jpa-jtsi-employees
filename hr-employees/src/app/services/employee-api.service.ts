import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { environment } from '../../environments/environment';
import { ServiceUtilities, Apis, Resources } from './serviceUtilities';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { EmployeeResponse } from '../mesagges/employeeResponse';
import { EmployeeRequest } from '../mesagges/employeeRequest';

const BASE_API = environment.apiUrl;
const API_URL = Apis.EMPLOYEES_API;

@Injectable()
export class EmployeeApiService extends ServiceUtilities {

  constructor(private _http: Http) {
    super();
  }

  public listManagersCmb(): Observable<EmployeeResponse> {
    console.log('start -- list-departments method');
    return this._http.get(BASE_API + API_URL + Resources.LIST_MANAGERS_CMB, this._options).pipe(map(resp => {
      return resp.json();
    }));
  }

  public listAllEmployees(): Observable<EmployeeResponse> {
    console.log('start -- list-departments method');
    return this._http.get(BASE_API + API_URL + Resources.LIST_EMPLOYEES, this._options).pipe(map(resp => {
      return resp.json();
    }));
  }

  public createOrEditEmployee(employeeRequest: EmployeeRequest): Observable<EmployeeResponse> {
    console.log('start -- create-edit-employee method');
    return this._http.post(BASE_API + API_URL + Resources.CREATE_EMPLOYEE, employeeRequest, this._options).pipe(map(resp => {
      return resp.json();
    }))
  }

  public deleteEmployee(employeeRequest: EmployeeRequest): Observable<EmployeeResponse> {
    console.log('start -- create-edit-employee method');
    return this._http.post(BASE_API + API_URL + Resources.DELETE_EMPLOYEE, employeeRequest, this._options).pipe(map(resp => {
      return resp.json();
    }))
  }

}
