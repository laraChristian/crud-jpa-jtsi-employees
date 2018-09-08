import { CommonsUtil } from "../ui-utilities/commons-util";
import { RegionResponse } from "../../mesagges/regionResponse";
import { RegionDTO } from "../../domain/regionDTO";
import { SelectItem } from "../../../../node_modules/primeng/api";
import { CountryRequest } from "../../mesagges/countryRequest";
import { CountryDTO } from "../../domain/countryDTO";

export class CountriesModel extends CommonsUtil {

    request: CountryRequest;
    countries: Array<CountryDTO>;
    displayDialog: boolean;
    enableDelete: boolean;
    enableId: boolean;
    regionsItems: SelectItem[];
    countryCols: any[];

    constructor() {
        super();
        this.displayDialog = false;
        this.enableId = false;
        this.regionsItems = [];
        this.clean();
        this.initColums();
    }

    showDialog(): void {
        this.displayDialog = true;
    }

    hideDialog(): void {
        this.displayDialog = false;
        this.enableDelete = false;
    }

    clean() {
        this.hideDialog();
        this.request = new CountryRequest();
        this.enableId = false;
    }

    initColums(): void {
        this.countryCols = [{ field: 'countryName', header: 'Country Name' }, { field: 'regionName', header: 'Region Name' }]
    }
}