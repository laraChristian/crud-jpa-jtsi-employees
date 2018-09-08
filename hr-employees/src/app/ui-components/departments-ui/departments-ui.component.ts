import { Component, OnInit } from '@angular/core';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { CommonsUtil } from '../ui-utilities/commons-util';
import { AdministrativeApiService } from '../../services/administrative-api.service';
import { DepartmentsModel } from './departments.model';
import { RegionsApiService } from '../../services/regions-api.service';
import { EmployeeApiService } from '../../services/employee-api.service';
import { DepartmentRequest } from '../../mesagges/departmentRequest';


@Component({
  selector: 'departments-ui',
  templateUrl: './departments-ui.component.html',
  styleUrls: ['./departments-ui.component.css'],
  providers: [AdministrativeApiService, RegionsApiService, EmployeeApiService]
})
export class DepartmentsUiComponent extends CommonsUtil implements OnInit {

  private _model: DepartmentsModel;

  constructor(private _messageService: MessageService, private _departmentService: AdministrativeApiService,
    private _regionService: RegionsApiService, private _employeeService: EmployeeApiService) {
    super();
    this._model = new DepartmentsModel();
  }

  ngOnInit() {
    this.listAllDepartments();
    this.listLocations();
    this.listManagers();
  }

  //#region core
  public createDepartment() {
    this._model.init();
    this._model.displayDialog = true;
    this._model.disableDdManagers = true;
  }

  public createOrEditDepartment() {
    console.log('start create-or-edit-department method')
    if (this.validateFields(this._model.request.departmentDTO, ["departmentName", "locationId"]) == true) {
      this._model.displayDialog = false;
      this._departmentService.createOrEditDepartment(this._model.request).subscribe(
        resp => {
          if (resp.success == true) {
            this.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
            this.listAllDepartments();
            this._model.clean();
          } else {
            this.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
          }
        },
        error => console.log('[ERROR]' + error),
        () => console.log('end create-or-edit-department method')
      );
    } else {
      this.showViaService(this._messageService, 'error', 'Atention', 'Empty fields arent allowed');
    }
  }

  public deleteDepartment(): void {
    console.log('start -- delete employee');
    this._departmentService.deleteDepartment(this._model.request).subscribe(
      resp => {
        if (resp.success == true) {
          this.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this.listAllDepartments();
        } else {
          this.showViaService(this._messageService, 'error', 'Atention', resp.message.toString());
        }
        this._model.clean();
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end delete-department method')
    );
  }
  //#endregion

  //#region Data-table
  private listAllDepartments(): void {
    this._departmentService.listAllDepartments().subscribe(
      response => {
        if (response.success == true) {
          this._model.response.departmentsDTO = response.departmentsDTO;
          console.log(this._model.response.departmentsDTO)
        } else {
          console.log("[ERROR] " + response.message);
        }
      },
      error => console.log('[ERROR] ' + error)
    );
  }

  private onRowSelect() {
    this._model.request.update = true;
    this._model.displayDialog = true;
    this._model.disableDdManagers = false;
  }
  //#endregion

  //#region DropDowns

  public listLocations(): void {
    console.log('start -- list-locations method');
    this._regionService.listLocationsCmb().subscribe(
      resp => {
        if (resp.success == true) {
          this.fillCombos(resp.locationsDTO, 'city', 'locationId', this._model.locationItems);
        } else {
          console.log('[ERROR] ' + resp.message);
        }
      },
      error => console.log('[ERROR] ' + error)
    );
    console.log('end -- list-locations method');
  }

  public listManagers(): void {
    console.log('start -- list-managers method');
    this._employeeService.listManagersCmb().subscribe(
      resp => {
        if (resp.success == true) {
          resp.employeesDTO = resp.employeesDTO.filter(manager => {
            if (manager.jobId.includes('AD') == true || manager.jobTitle.includes('Manager') == true) {
              manager.firstName = manager.firstName + ' ' + manager.lastName;
              return true;
            }
            return false;
          })
          this.fillCombos(resp.employeesDTO, 'firstName', 'id', this._model.employeeItems);
        } else {
          console.log('[ERROR] ' + resp.message);
        }
      },
      error => console.log('[ERROR] ' + error)
    );
    console.log('end -- list-managers method');

  }
  //#endregion

  private cancel() {
    this._model.clean();
  }
}
