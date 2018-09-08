export class EmployeeDTO {

    public id: number;
    public firstName: String;
    public lastName: String;
    public email: String;
    public identification: String;
    public hireDate: Date;
    public phoneNumber: String;
    public salary: number;
    public commissionPct: number;
    public departmentId: number;
    public departmentName: String;
    public managerId: number;
    public managerName: String;
    public jobId: String;
    public jobTitle: String;

    constructor() {
        this.managerId = 103;
        this.jobId = "IT_PROG";
        this.departmentId = 60;
    }
}