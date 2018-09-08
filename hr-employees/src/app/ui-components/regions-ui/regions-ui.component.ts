import { Component, OnInit } from '@angular/core';
import { RegionsApiService } from '../../services/regions-api.service';
import { RegionsModel } from './regions.model';
import { MessageService } from '../../../../node_modules/primeng/api';
import { FormBuilder } from '../../../../node_modules/@angular/forms';
import { RegionRequest } from '../../mesagges/regionRequest';

@Component({
  selector: 'regions-ui',
  templateUrl: './regions-ui.component.html',
  styleUrls: ['./regions-ui.component.css'],
  providers: [RegionsApiService]
})
export class RegionsUiComponent implements OnInit {

  private _model: RegionsModel;

  constructor(private _regionService: RegionsApiService, private _messageService: MessageService, private fb: FormBuilder) {
    this._model = new RegionsModel(fb);
  }

  ngOnInit() {
    this.listRegions();
  }


  //#region Form
  private onSubmit(value: string) {
    this._model.request.request = this._model.regionsForm.value;

    if (this._model.request.request.regionId == null) {
      this._model.request.request.regionId = 0;
    }
    this.createRegion(this._model.request);
  }
  //#endregion

  //#region Core
  private createRegion(request: RegionRequest) {
    console.log('start -- create-region method');
    this._regionService.createRegion(request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this.listRegions();
        } else {
          this._model.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
      },
      error => console.error('[ERROR] -- ' + error),
      () => console.log('end -- create-region method')
    );
    this._model.clean();
  }

  private deleteRegion(): void {
    console.log('start -- delete-region method');
    this._regionService.deleteRegion(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this._model.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
        } else {
          this._model.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
      },
      error => console.log('[ERROR] -- ' + error),
      () => console.log('end -- delete-region method')
    );
    this._model.clean();
    this.listRegions();
  }
  //#endregion

  //#region Data-Table

  onRowSelect(): void {
    this._model.request.update = true;
    this._model.disableDelete = false;
    this._model.regionsForm.setValue({ 'regionId': this._model.request.request.regionId, 'regionName': this._model.request.request.regionName });
  }

  listRegions(): void {
    console.log('start -- list-regions method')
    this._regionService.listRegions().subscribe(
      resp => {
        console.log(resp);
        if (resp.success == true) {
          this._model.regionsDTO = resp.regionsDTO;
        } else {
          console.error('[ERROR] ' + resp.message);
        }
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- list-regions method')
    );
  }

  //#endregion
}
