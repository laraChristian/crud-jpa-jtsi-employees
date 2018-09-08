import { CommonsUtil } from "../ui-utilities/commons-util";
import { SelectItem } from "../../../../node_modules/primeng/api";
import { FormBuilder, Validators, FormControl, FormGroup } from "../../../../node_modules/@angular/forms";
import { LocationRequest } from "../../mesagges/locationRequest";
import { LocationResponse } from "../../mesagges/locationResponse";
import { LocationDTO } from "../../domain/locationDTO";

export class LocationsModel extends CommonsUtil {

    request: LocationRequest;
    response: LocationResponse;
    countryItems: SelectItem[];
    userform: FormGroup;
    locationCols: any[];

    constructor(private fb: FormBuilder) {
        super();
        this.countryItems = [];
        this.initForm();
        this.initCols();
        this.request = new LocationRequest();
        this.response = new LocationResponse();
    }


    initForm(): void {
        this.userform = this.fb.group({
            'streetAddress': new FormControl('', Validators.required),
            'postalCode': new FormControl('', Validators.required),
            'city': new FormControl('', Validators.required),
            'stateProvince': new FormControl('', Validators.required),
            'countryId': new FormControl('', Validators.required),
            'locationId': new FormControl(0),
        });
    }

    initCols(): void {
        this.locationCols = [{ field: 'streetAddress', header: 'Street Address' }, { field: 'postalCode', header: 'Postal Code' },
        { field: 'city', header: 'City' }, { field: 'stateProvince', header: 'State Province' },
        { field: 'countryName', header: 'Country Name' }]
    }

    clean(): void {
        this.userform.reset();
        this.request = new LocationRequest();
    }

    mapToRequest(dataRow: LocationDTO): void {
        this.request.request.countryId = dataRow.countryId;
        this.request.request.locationId = dataRow.locationId;
        this.request.request.stateProvince = dataRow.stateProvince;
        this.request.request.streetAddress = dataRow.streetAddress;
        this.request.request.city = dataRow.city;
        this.request.request.postalCode = dataRow.postalCode;
    }
}