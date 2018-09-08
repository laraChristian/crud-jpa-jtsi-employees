import { RegionDTO } from "../domain/regionDTO";

export class RegionRequest {

    public update: boolean;
    public request: RegionDTO;

    constructor() {
        this.update = false;
    }
}