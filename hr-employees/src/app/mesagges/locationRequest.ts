import { LocationDTO } from "../domain/locationDTO";

export class LocationRequest {

    public update: boolean;
    public request: LocationDTO;

    constructor() {
        this.request = new LocationDTO();
        this.update = false;
    }
}