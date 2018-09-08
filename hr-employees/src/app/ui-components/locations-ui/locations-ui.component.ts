import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Validators, FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { SelectItem, Message } from '../../../../node_modules/primeng/api';
import { RegionsApiService } from '../../services/regions-api.service';
import { LocationsModel } from './locations.model';
import { LocationRequest } from '../../mesagges/locationRequest';
import { MessageService } from '../../../../node_modules/primeng/components/common/messageservice';
import { LocationDTO } from '../../domain/locationDTO';

@Component({
  selector: 'locations-ui',
  templateUrl: './locations-ui.component.html',
  styleUrls: ['./locations-ui.component.css'],
  providers: [FormBuilder, RegionsApiService],
  encapsulation: ViewEncapsulation.Emulated
})
export class LocationsUiComponent implements OnInit {

  private _model: LocationsModel;
  options: any;

  constructor(private fb: FormBuilder, private _regionService: RegionsApiService, private _messageService: MessageService) {
    this._model = new LocationsModel(fb);
  }

  ngOnInit() {
    this.listCountriesCmb();
    this.listLocations();
  }

  //#region  form
  private onSubmit(value: string) {
    this._model.request.request = this._model.userform.value;

    if (this._model.request.request.locationId === null) {
      this._model.request.request.locationId = 0;
    }

    this.createEditLocation(this._model.request);
  }
  //#endregion

  //#region core
  private createEditLocation(request: LocationRequest): void {
    console.log('start -- create-edit-method');
    this._regionService.createEditLocation(request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this._model.clean();
          this.listLocations();
        } else {
          this._model.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
      },
      error => console.log('[ERROR] -- ' + error),
      () => console.log('end -- create-edit-method')
    );
  }

  private deleteLocation(request: LocationRequest): void {
    console.log('start -- delete method')
    console.log(request)
    this._regionService.deleteLocation(request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this.listLocations();
        } else {
          this._model.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
        this._model.clean();
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- on delete method')
    );

  }
  //#endregion

  //#region combos
  private listCountriesCmb(): void {
    console.log('start -- list-countries-cmb method')
    this._regionService.listCountriesCmb().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.fillCombos(resp.countriesDTO, 'countryName', 'countryId', this._model.countryItems);
        } else {
          console.log('[ERROR] ' + resp.message);
        }
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- list-countries-cmb method')
    );
  }
  //#endregion

  //#region data-table

  private listLocations(): void {
    console.log('start -- list-locations method');
    this._regionService.listLocations().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.response.locationsDTO = resp.locationsDTO;
          console.log(this._model.response.locationsDTO);
        } else {
          console.log('[ERROR] -- ' + resp.message);
        }
      },
      error => console.log('[ERROR] -- ' + error),
      () => console.log('end -- list-locations method')
    );
  }

  private onRowSelect(): void {
    this._model.request.update = true;
    this._model.userform.setValue({
      'streetAddress': this._model.request.request.streetAddress, 'postalCode': this._model.request.request.postalCode,
      'city': this._model.request.request.city, 'stateProvince': this._model.request.request.stateProvince,
      'countryId': this._model.request.request.countryId, 'locationId': this._model.request.request.locationId
    });
  }


  private onConfirm() {
    console.log('start -- on-delete method')
    this._messageService.clear('c');
    this.deleteLocation(this._model.request);
    console.log('end -- on-delete method')
  }

  private onReject() {
    console.log('start -- on-reject method')
    this._messageService.clear('c');
    this._model.clean();
    console.log('end -- on-reject method')
  }

  private showConfirm(rowData: LocationDTO) {
    console.log('start -- show-confirm method')
    this._model.clean();
    this._messageService.clear();
    this._messageService.add({ key: 'c', sticky: true, summary: 'Are you sure?', detail: 'Confirm to proceed' });
    this._model.mapToRequest(rowData);
    console.log('end -- show-confirm method');
  }
  //#endregion

}
