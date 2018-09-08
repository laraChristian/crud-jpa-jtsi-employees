import { Injectable } from '@angular/core';
import { ServiceUtilities, Apis, Resources } from './serviceUtilities';
import { Http } from '@angular/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { LocationResponse } from '../mesagges/locationResponse';
import { CountryResponse } from '../mesagges/countryResponse';
import { LocationRequest } from '../mesagges/locationRequest';
import { RegionResponse } from '../mesagges/regionResponse';
import { CountryRequest } from '../mesagges/countryRequest';
import { RegionRequest } from '../mesagges/regionRequest';

const BASE_API = environment.apiUrl;
const API_URL = Apis.REGIONS_API;

@Injectable()
export class RegionsApiService extends ServiceUtilities {

  constructor(private _http: Http) {
    super();
  }

  public listLocationsCmb(): Observable<LocationResponse> {
    console.log('start list-locations-cmb');
    return this._http.get(BASE_API + API_URL + Resources.LIST_LOCATIONS_CMB, this._options).pipe(map(resp => resp.json()));
  }

  public listCountriesCmb(): Observable<CountryResponse> {
    console.log('start list-locations-cmb');
    return this._http.get(BASE_API + API_URL + Resources.LIST_COUNTRIES_CMB, this._options).pipe(map(resp => resp.json()));
  }

  public listRegionsCmb(): Observable<RegionResponse> {
    console.log('start list-regions-cmb');
    return this._http.get(BASE_API + API_URL + Resources.LIST_REGIONS_CMB, this._options).pipe(map(resp => resp.json()));
  }

  public createEditLocation(request: LocationRequest): Observable<CountryResponse> {
    console.log('start list-locations-cmb');
    return this._http.post(BASE_API + API_URL + Resources.CREATE_LOCATION, request, this._options).pipe(map(resp => {

      if (resp.status == 304) {
        console.log('not modified');
      }

      if (resp.status == 500) {
        console.log('server error');
      }

      return resp.json();
    }));
  }

  public listLocations(): Observable<LocationResponse> {
    return this._http.get(BASE_API + API_URL + Resources.LIST_LOCATIONS, this._options).pipe(map(resp => resp.json()));
  }

  public deleteLocation(request: LocationRequest): Observable<LocationResponse> {
    return this._http.post(BASE_API + API_URL + Resources.DELETE_LOCATION, request, this._options).pipe(map(resp => {
      return resp.json();
    }));
  }

  public listCountries(): Observable<CountryResponse> {
    console.log('start list-countries method');
    return this._http.get(BASE_API + API_URL + Resources.LIST_COUNTRIES, this._options).pipe(map(resp => resp.json()));
  }

  public createCountry(request: CountryRequest): Observable<CountryResponse> {
    return this._http.post(BASE_API + API_URL + Resources.CREATE_COUNTRY, request, this._options).pipe(map(resp => resp.json()));
  }

  public deleteCountry(request: CountryRequest): Observable<CountryResponse> {
    return this._http.post(BASE_API + API_URL + Resources.DELETE_COUNTRY, request, this._options).pipe(map(resp => resp.json()));
  }

  public listRegions(): Observable<RegionResponse> {
    console.log('start -- list-regions method');
    return this._http.get(BASE_API + API_URL + Resources.LIST_REGIONS, this._options).pipe(map(resp => resp.json()));
  }

  public createRegion(request: RegionRequest): Observable<RegionResponse> {
    return this._http.post(BASE_API + API_URL + Resources.CREATE_REGION, request, this._options).pipe(map(resp => resp.json()));
  }

  public deleteRegion(request: RegionRequest): Observable<RegionResponse> {
    return this._http.post(BASE_API + API_URL + Resources.DELETE_REGION, request, this._options).pipe(map(resp => resp.json()));
  }
}