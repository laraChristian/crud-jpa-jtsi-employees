import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, ResponseType } from '@angular/http';
import { Observable } from 'rxjs';
import { JobResponse } from '../mesagges/jobResponse';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Apis, Resources } from './serviceUtilities';
import { DepartmentResponse } from '../mesagges/departmentResponse';
import { DepartmentRequest } from '../mesagges/departmentRequest';
import { JobRequest } from '../mesagges/jobRequest';

const BASE_API = environment.apiUrl;
const API_URL = Apis.ADMINISTRATIVE_API;

@Injectable()
export class AdministrativeApiService {

  private _headers: Headers;
  private _options: RequestOptions;

  constructor(private _http: Http) {
    this._headers = new Headers({ 'Content-Type': 'application/json' })
    this._options = new RequestOptions({ headers: this._headers });
  }

  public listJobsCmb(): Observable<JobResponse> {
    console.log('start -- list-jobs method');
    return this._http.get(BASE_API + API_URL + Resources.LIST_JOBS_CMB, this._options).pipe(map(resp => {
      return resp.json();
    }));
  }

  public listJobs(): Observable<JobResponse> {
    console.log('start -- list-jobs method');
    return this._http.get(BASE_API + API_URL + Resources.LIST_JOBS, this._options).pipe(map(resp => {
      return resp.json();
    }));
  }


  public listAllDepartments(): Observable<DepartmentResponse> {
    console.log('start -- list-departments method');
    return this._http.post(BASE_API + API_URL + Resources.LIST_DEPARTMENTS, 'JSON', this._options).pipe(map(resp => {
      return resp.json();
    }));
  }

  public createOrEditDepartment(_departmentRequest: DepartmentRequest): Observable<DepartmentResponse> {
    console.log('start -- create-or-edit-department method');
    return this._http.post(BASE_API + API_URL + Resources.CREATE_DEPARTMENT, _departmentRequest, this._options).pipe(map(resp => {
      if (resp.status === 500) {
        console.log(resp.statusText);
      }
      return resp.json();
    }));
  }

  public deleteDepartment(_departmentRequest: DepartmentRequest): Observable<DepartmentResponse> {
    return this._http.post(BASE_API + API_URL + Resources.DELETE_DEPARTMENT, _departmentRequest, this._options).pipe(map(resp => {
      if (resp.status == 500) {
        console.log('[ATENTION] ' + resp.statusText);
      }
      return resp.json();
    }));
  }

  public createJob(_jobRequest: JobRequest): Observable<JobResponse> {
    console.log('start -- create-job method')
    return this._http.post(BASE_API + API_URL + Resources.CREATE_JOB, _jobRequest, this._options).pipe(map(resp => {
      if (resp.status == 500) {
        console.error('ATENTION: error in create job' + resp.status);
      }
      console.log('end -- create-job method')
      return resp.json();
    }));
  }

  public deleteJob(_jobRequest: JobRequest): Observable<JobResponse> {
    console.log('start -- delete-job method')
    return this._http.post(BASE_API + API_URL + Resources.DELETE_JOB, _jobRequest, this._options).pipe(map(resp => {
      if (resp.status == 500) {
        console.error('ATENTION: error in delete job' + resp.status);
      }
      console.log('end -- delete-job method')
      return resp.json();
    }));
  }
}
