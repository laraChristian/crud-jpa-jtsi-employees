import { DeparmentDTO } from "../domain/departmentDTO";

export class DepartmentResponse {

    public departmentsDTO: Array<DeparmentDTO>;
    public message: String;
    public success: boolean;

    constructor() {
        this.success = false;
        this.departmentsDTO = [];
    }
}