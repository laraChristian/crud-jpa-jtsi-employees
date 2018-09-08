import { DeparmentDTO } from "../domain/departmentDTO";

export class DepartmentRequest {

    public departmentDTO: DeparmentDTO;
    public update: boolean;

    constructor() {
        this.update = false;
        this.departmentDTO = new DeparmentDTO();
    }
}