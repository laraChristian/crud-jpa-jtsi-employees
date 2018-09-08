import { Component, OnInit } from '@angular/core';
import { UsersModel } from './users.model';
import { AdministrativeApiService } from '../../services/administrative-api.service';
import { EmployeeApiService } from '../../services/employee-api.service';
import { SEVERITY } from '../ui-utilities/commons-util';
import { EmployeeDTO } from '../../domain/employeeDTO';
import { ConfirmationService } from 'primeng/api';
import { SessionService, WebModel } from '../../services/session.service';
import { LoginModel } from '../login/login.model';

@Component({
  selector: 'middle-ui/users-ui',
  templateUrl: './users-ui.component.html',
  styleUrls: ['./users-ui.component.css'],
  providers: [AdministrativeApiService, EmployeeApiService, ConfirmationService]
})
export class UsersUiComponent implements OnInit {

  private _model: UsersModel;

  constructor(private _administrativeApi: AdministrativeApiService, private _employeesApi: EmployeeApiService, private _confirmationService: ConfirmationService, private _session: SessionService) {
    this._model = new UsersModel();
  }

  ngOnInit() {
    this.listAllJobs();
    this.listAllManagers();
    this.listAllDepartments();
    this.listAllCreatedEmployees();
  }

  //#region load combos
  private listAllJobs(): void {
    console.log('start -- list-all-jobs method');
    this._administrativeApi.listJobsCmb().subscribe(
      jobResponse => {
        this._model.fillCombos(jobResponse.jobsDTO, 'jobTitle', 'jobId', this._model.jobItems);
      },
      error => console.log('[ERROR] -- list all jobs ' + error)
    );
    console.log('end -- list-all-jobs method');
  }

  private listAllDepartments(): void {
    console.log('start -- list-all-departments method');
    this._administrativeApi.listAllDepartments().subscribe(
      deparment => {
        this._model.fillCombos(deparment.departmentsDTO, 'departmentName', 'departmentId', this._model.departmentItems);
      },
      error => console.log('[ERROR] -- list all jobs ' + error)
    );
    console.log('end -- list-all-departments method');
  }

  private listAllManagers(): void {
    console.log('start -- list-all-managers method');
    this._employeesApi.listManagersCmb().subscribe(
      response => {
        this._model.managersResource = response.employeesDTO;
        response.employeesDTO.forEach(manager => {
          manager.firstName = manager.firstName + ' ' + manager.lastName;
        });
        response.employeesDTO = response.employeesDTO.filter(manager => {
          if (manager.jobId.includes('AD_PRES') == true || manager.jobId.includes('AD_VP') == true || manager.jobTitle.includes('Manager') == true) {
            return true;
          }
          return false;
        });

        this._model.fillCombos(response.employeesDTO, 'firstName', 'id', this._model.managerItems);
      },
      error => console.log('[ERROR] -- list all jobs ' + error)
    );
    console.log('end -- list-all-managers method');
  }

  private listAllCreatedEmployees(): void {
    console.log('start -- list-all-created-employees');
    this._model.loading = true;
    setTimeout(() => {
      this._employeesApi.listAllEmployees().subscribe(
        resp => {
          this._model.employeesCreated = resp.employeesDTO;
        },
        error => console.log('[ERROR] ' + error)
      );
      this._model.loading = false;
    }, 3000);
    console.log('start -- list-all-created-employees');
  }
  //#endregion

  //#region core
  private showRowData() {
    this._model.model.update = true;
  }

  private createEditEmploye() {
    console.log('start -- create-edit--employee method');
    if (this._model.validateFields(this._model.model.employee, ["firstName", "lastName", "email", "identification", "salary", "phoneNumber"]) === true) {
      this._employeesApi.createOrEditEmployee(this._model.model).subscribe(
        data => {
          if (data.success == true) {
            this._model.show(SEVERITY.success, 'Success', data.message.toString());
            this._model.clean();
            this.listAllCreatedEmployees();
          } else {
            this._model.show(SEVERITY.error, 'Atention', data.message.toString());
          }
        },
        error => console.log('[ERROR] ' + error)
      );
    } else {
      this._model.show(SEVERITY.error, 'Error', 'Empty fields arent allowed');
    }
    console.log('end -- create-edit--employee method');
  }

  private deleteEmployee(dto: EmployeeDTO) {
    console.log('start -- delete-employee')
    this._model.model.employee = dto;
    this._employeesApi.deleteEmployee(this._model.model).subscribe(
      resp => {
        console.log(resp);
        if (resp.success == true) {
          this._model.show(SEVERITY.success, 'Atention', 'Employee removed successfully')
        } else {
          this._model.show(SEVERITY.error, 'ERROR', resp.message.toString());
          console.log('[ERROR] -- ' + resp.message);
        }
      }
    );
    this._model.clean();
    this.listAllCreatedEmployees();
    console.log('end -- delete-employee')
  }

  private confirm(dto: EmployeeDTO) {
    this._confirmationService.confirm({
      message: 'Are you sure that you want to perform this action?',
      accept: () => {
        console.log('accepted');
        this.deleteEmployee(dto);
      },
      reject: () => {
        this._model.clean();
      }
    });
  }
  //#endregion

  //#region utilities
  private filterDepartments() {
    this._model.managersResource.forEach(manager => {
      console.log('selecting department');
      if (manager.id == this._model.model.employee.managerId) {
        this._model.model.employee.departmentId = manager.departmentId;
      }
    });
  }


  private disableDepartments() {
    if ((this._model.model.employee.jobId.includes('MAN') == true)
       || (this._model.model.employee.jobId.includes('MGR') == true) 
       || (this._model.model.employee.jobId.includes('AD') == true)) {
      this._model.status = false;
    } else {
      this._model.status = true;
    }
  }
  //#endregion
}
