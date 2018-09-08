import { Component, OnInit } from '@angular/core';
import { CountriesModel } from './countries.model';
import { RegionsApiService } from '../../services/regions-api.service';
import { MessageService } from '../../../../node_modules/primeng/api';

@Component({
  selector: 'countries-ui',
  templateUrl: './countries-ui.component.html',
  styleUrls: ['./countries-ui.component.css'],
  providers: [RegionsApiService, MessageService]
})
export class CountriesUiComponent implements OnInit {

  _model: CountriesModel;

  constructor(private _regionService: RegionsApiService, private _messageService: MessageService) {
    this._model = new CountriesModel();
  }

  ngOnInit() {
    this.listRegionsCmb();
    this.listCountries();
  }


  //#region core
  private prepareCreate(): void {
    console.log('start -- prepare-create-country method');
    this._model.showDialog();
    this._model.enableDelete = true;
    console.log('end -- prepare-create-country method');
  }

  private createEditContry() {
    console.log('start --  create-edit country');
    this._regionService.createCountry(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this.listCountries();
        } else {
          this._model.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
      },
      error => console.error('[ERROR] -- ' + error),
      () => console.log('end create edit method')
    );
    this._model.clean();

  }

  private deleteCountry() {
    console.log('start  -- delete-country method')
    this._regionService.deleteCountry(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this.listCountries();
        } else {
          this._model.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
      },
      error => console.error('[ERROR] -- ' + error),
      () => console.log('end  -- delete-country method')
    );
    this._model.clean();
  }
  //#endregion

  //#region dropdown
  private listRegionsCmb(): void {
    this._regionService.listRegionsCmb().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.fillCombos(resp.regionsDTO, 'regionName', 'regionId', this._model.regionsItems);
        } else {
          console.error('[ERROR] ' + resp.message);
        }
      },
      error => console.log('[ERROR] ' + error)
    );
  }

  //#endregion

  //#region  data-table
  private listCountries() {
    console.log('start -- list-countries method')
    this._regionService.listCountries().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.countries = resp.countriesDTO;
        } else {
          console.error('[ERROR] ' + resp.message)
        }
      },
      error => console.error('[ERROR] ' + error),
      () => console.log('end -- list-countries method')
    );
  }

  onRowSelect(): void {
    this._model.request.update = true;
    this._model.enableId = true;
    this._model.showDialog();
  }


  //#endregion
}
