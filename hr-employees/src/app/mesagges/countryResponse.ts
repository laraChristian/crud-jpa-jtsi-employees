import { CountryDTO } from "../domain/countryDTO";

export class CountryResponse {

    public message: String;
    public success: boolean;
    public countriesDTO: Array<CountryDTO>;

}