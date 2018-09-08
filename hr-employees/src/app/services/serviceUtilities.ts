import { Http, Headers, RequestOptions } from '@angular/http';

export class ServiceUtilities {

    protected _headers: Headers;
    protected _options: RequestOptions;

    constructor() {
        this.init();
    }

    private init() {
        this._headers = new Headers({ 'Content-type': 'application/json' });
        this._options = new RequestOptions({ headers: this._headers });
    }

    protected instance(contentType: string, contentValue: string) {
        this._headers = new Headers({ 'Content-type': 'application/json' });
        this._options = new RequestOptions({ headers: this._headers });
    }

}

export enum Apis {
    EMPLOYEES_API = '/employees-api',
    ADMINISTRATIVE_API = '/administrative-api',
    REGIONS_API = '/regions-api'
}

export enum Resources {
    LOGGIN = '/loggin',
    CREATE_EMPLOYEE = '/create-edit-employee',
    CREATE_DEPARTMENT = '/create-edit-department',
    CREATE_JOB = '/create-edit-job',
    CREATE_LOCATION = '/create-edit-location',
    CREATE_COUNTRY = '/create-edit-country',
    CREATE_REGION = '/create-edit-region',
    LIST_MANAGERS_CMB = '/list-managers-cmb',
    LIST_JOBS_CMB = '/list-jobs-cmb',
    LIST_LOCATIONS_CMB = '/list-locations-cmb',
    LIST_COUNTRIES_CMB = '/list-countries-cmb',
    LIST_REGIONS_CMB = '/list-regions.cmb',
    LIST_EMPLOYEES = '/list-employees',
    LIST_JOBS = '/list-jobs',
    LIST_DEPARTMENTS = '/list-departments',
    LIST_LOCATIONS = '/list-locations',
    LIST_COUNTRIES = '/list-countries',
    LIST_REGIONS = '/list-regions',
    DELETE_DEPARTMENT = '/delete-department',
    DELETE_JOB = '/delete-job',
    DELETE_LOCATION = '/delete-location',
    DELETE_EMPLOYEE = '/delete-employee',
    DELETE_COUNTRY = '/delete-country',
    DELETE_REGION = '/delete-region'
}