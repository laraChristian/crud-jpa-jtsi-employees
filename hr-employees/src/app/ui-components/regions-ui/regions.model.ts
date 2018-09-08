import { CommonsUtil } from "../ui-utilities/commons-util";
import { RegionDTO } from "../../domain/regionDTO";
import { RegionRequest } from "../../mesagges/regionRequest";
import { FormGroup, FormControl, FormBuilder, Validators } from "../../../../node_modules/@angular/forms";

export class RegionsModel extends CommonsUtil {

    request: RegionRequest;
    tblCols: any[];
    disableDelete: boolean;
    regionsDTO: Array<RegionDTO>;
    regionsForm: FormGroup;

    constructor(private fb: FormBuilder) {
        super();
        this.disableDelete = true;
        this.initTblResources();
        this.initForm();
        this.clean();
    }

    initTblResources(): void {
        this.regionsDTO = [];
        this.tblCols = [{ field: 'regionId', header: 'Region Id' }, { field: 'regionName', header: 'Region Name' }];
    }

    initForm(): void {
        this.regionsForm = this.fb.group({
            'regionId': new FormControl(0),
            'regionName': new FormControl('', Validators.required),
        });
    }

    clean() {
        this.request = new RegionRequest();
        this.regionsForm.reset();
        this.disableDelete = true;
    }
}