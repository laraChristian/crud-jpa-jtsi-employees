import { JobResponse } from "../../mesagges/jobResponse";
import { JobRequest } from "../../mesagges/jobRequest";

export class JobsModel {

    response: JobResponse;
    request: JobRequest;
    displayDialog: boolean;
    jobCols: any[];
    ccRegexId: RegExp = /^[A-Z\W_\s]+$/;
    ccRegex: RegExp = /^[a-zA-Z\s]+$/;

    constructor() {
        this.init();
    }

    init(): void {
        this.response = new JobResponse();
        this.request = new JobRequest();
        this.displayDialog = false;
        this.jobCols = [{ field: 'jobId', header: 'Job Id' }, { field: 'jobTitle', header: 'Job Title' },
        { field: 'minSalary', header: 'Min Salary' }, { field: 'maxSalary', header: 'Min Salary' }]
    }

    hideDialog(): void {
        this.displayDialog = false;
    }

    showDialog(): void {
        this.displayDialog = true;
    }

    clean(): void {
        this.request = new JobRequest();
    }

    isLessThan(less: number, bigger: number): boolean {
        console.log(less < bigger)
        return less < bigger;
    }
}