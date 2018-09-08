import { UsersUiComponent } from "../ui-components/users-ui/users-ui.component";
import { AuthGuard } from "../security/auth-guard";
import { LogginGuard } from "../security/loggin-guard";
import { LoginComponent } from "../ui-components/login/login.component";
import { RouterModule, Routes } from "@angular/router";
import { MiddleUiComponent } from "../ui-components/middle-ui/middle-ui.component";
import { DepartmentsUiComponent } from "../ui-components/departments-ui/departments-ui.component";
import { JobsUiComponent } from "../ui-components/jobs-ui/jobs-ui.component";
import { LocationsUiComponent } from "../ui-components/locations-ui/locations-ui.component";
import { CountriesUiComponent } from "../ui-components/countries-ui/countries-ui.component";
import { RegionsUiComponent } from "../ui-components/regions-ui/regions-ui.component";

const routes: Routes = [
    { path: '', redirectTo: 'loggin', pathMatch: 'full' },
    {
        path: 'middle-ui', component: MiddleUiComponent, canActivate: [AuthGuard], children: [
            { path: 'users-ui', component: UsersUiComponent },
            { path: 'departments-ui', component: DepartmentsUiComponent },
            { path: 'jobs-ui', component: JobsUiComponent },
            { path: 'locations-ui', component: LocationsUiComponent },
            { path: 'countries-ui', component: CountriesUiComponent },
            { path: 'regions-ui', component: RegionsUiComponent }
        ]
    },
    {
        path: 'loggin', component: LoginComponent, canActivate: [LogginGuard]
    }
];

export const routing = RouterModule.forRoot(routes, { useHash: true });