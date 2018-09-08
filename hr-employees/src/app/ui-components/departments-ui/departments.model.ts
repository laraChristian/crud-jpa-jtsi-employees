import { DepartmentResponse } from "../../mesagges/departmentResponse";
import { DepartmentRequest } from "../../mesagges/departmentRequest";
import { SelectItem } from "../../../../node_modules/primeng/api";

export class DepartmentsModel {

    response: DepartmentResponse;
    request: DepartmentRequest;
    departmentsCols: any[];
    displayDialog: boolean;
    disableDdManagers: boolean;
    locationItems: SelectItem[];
    employeeItems: SelectItem[] = [];

    constructor() {
        this.clean();
        this.response = new DepartmentResponse();
        this.departmentsCols = [{ field: 'departmentName', header: 'Department' }, { field: 'locationName', header: 'Location' },
        { field: 'firstName', header: 'Manager First Name' }, { field: 'lastName', header: 'Manager First Name' }];
        this.employeeItems.push({ value: 0, label: 'Rescind Manager' });
        this.locationItems = [];
    }

    clean() {
        this.request = new DepartmentRequest();
        this.displayDialog = false;
        this.disableDdManagers = false;
        this.request.update = false;
    }

    init() {
        this.request = new DepartmentRequest();
        this.request.departmentDTO.locationId = 1000;
        this.request.departmentDTO.departmentId = 0;
        this.request.departmentDTO.managerId = 0;
    }

}