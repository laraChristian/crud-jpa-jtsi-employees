import { CountryDTO } from "../domain/countryDTO";

export class CountryRequest {

    public update: boolean;
    public countryDTO: CountryDTO;

    constructor() {
        this.update = false;
        this.countryDTO = new CountryDTO();
    }
}