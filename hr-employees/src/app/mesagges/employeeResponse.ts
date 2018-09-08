import { EmployeeDTO } from "../domain/employeeDTO";

export class EmployeeResponse {

    public message: String;
    public cause: String;
    public employeesDTO: Array<EmployeeDTO>;
    public success: boolean;

}