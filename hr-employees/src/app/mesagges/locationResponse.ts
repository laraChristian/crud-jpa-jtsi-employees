import { LocationDTO } from "../domain/locationDTO";

export class LocationResponse {

    public locationsDTO: Array<LocationDTO>;
    public message: String;
    public success: boolean;

}