import { JobDTO } from "../domain/jobDTO";

export class JobRequest {

    public update: boolean;
    public jobDTO: JobDTO;

    public constructor() {
        this.update = false;
        this.jobDTO = new JobDTO();
    }

}