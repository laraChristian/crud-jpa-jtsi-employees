import { JobDTO } from "../domain/jobDTO";

export class JobResponse {

    public success: boolean;
    public jobsDTO: Array<JobDTO>;
    public message: String;

}