export class LocationDTO {

    public locationId: number;
    public streetAddress: String;
    public postalCode: String;
    public city: String;
    public stateProvince: String;
    public countryId: String;
    public countryName: String;

    constructor() {
        this.locationId = 0;
    }
}