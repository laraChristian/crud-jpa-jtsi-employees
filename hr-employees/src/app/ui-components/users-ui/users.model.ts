import { CommonsUtil } from "../ui-utilities/commons-util";
import { EmployeeRequest } from "../../mesagges/employeeRequest";
import { JobResponse } from "../../mesagges/jobResponse";
import { SelectItem } from "primeng/components/common/selectitem";
import { EmployeeResponse } from "../../mesagges/employeeResponse";
import { EmployeeDTO } from "../../domain/employeeDTO";

export class UsersModel extends CommonsUtil {

    public model: EmployeeRequest;
    public jobResponse: JobResponse;
    public employeeResponse: EmployeeResponse;
    public managersResource: Array<EmployeeDTO>;
    public employeesCreated: Array<EmployeeDTO>;
    scrollableCols: any[];
    frozenCols: any[];
    public jobItems: SelectItem[];
    public departmentItems: SelectItem[];
    public managerItems: SelectItem[];
    public status: boolean;
    public loading: boolean;
    public ccRegex: RegExp = /^[a-zA-Z\s]+$/; 

    constructor() {
        super();
        this.init();
    }

    private init(): void {
        this.clean();
        this.jobResponse = new JobResponse();
        this.jobItems = [];
        this.departmentItems = [];
        this.managerItems = [];
        this.managersResource = [];
        this.model = new EmployeeRequest();
        this.status = false;
        this.frozenCols = [{ field: 'firstName', header: 'First Name' }];
        this.scrollableCols = [{ field: 'lastName', header: 'Last Name' }, { field: 'email', header: 'Email' }, { field: 'identification', header: 'Identification' },
        { field: 'hireDate', header: 'Hire Date' }, { field: 'phoneNumber', header: 'Phone Number' },
        { field: 'salary', header: 'Salary' }, { field: 'commissionPct', header: 'Comission' },
        { field: 'departmentName', header: 'Department Name' }, { field: 'managerName', header: 'Manager Name' },
        { field: 'jobTitle', header: 'Job Title' }];
    }


    public clean(): void {
        console.log('start -- clean method')
        this.model = new EmployeeRequest();
        this.status = false;
    }

}