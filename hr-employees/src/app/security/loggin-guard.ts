import { Injectable } from "@angular/core";
import { SessionService, WebModel } from "../services/session.service";
import { Router, CanActivate } from "@angular/router";
import { LoginModel } from "../ui-components/login/login.model";

@Injectable()
export class LogginGuard implements CanActivate{

    loginModel: LoginModel;

    constructor(private _router: Router, private _session: SessionService) { }

    canActivate() {
        this.loginModel = this._session.restoreStatus(WebModel.LOGIN, new LoginModel());

        if (this.loginModel._loggedIn) {
            console.log('user logged successfully');

            this._router.navigate(['/middle-ui']);
            return true;
        }
        console.log('user not logged');
        return true;
    }

}