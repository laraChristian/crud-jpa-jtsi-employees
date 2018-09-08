import { RegionDTO } from "../domain/regionDTO";

export class RegionResponse {

    public success: boolean;
    public message: String;
    public regionsDTO: Array<RegionDTO>;
}