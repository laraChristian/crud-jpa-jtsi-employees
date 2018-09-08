import { EmployeeDTO } from "../domain/employeeDTO";

export class EmployeeRequest {

    public update: boolean;
    public employee: EmployeeDTO;

    public constructor() {
        this.init();
    }

    public init(): void {
        this.update = false;
        this.employee = new EmployeeDTO();
    }
}