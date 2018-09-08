import { Router, CanActivate } from "@angular/router";
import { WebModel, SessionService } from "../services/session.service";
import { LoginModel } from "../ui-components/login/login.model";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthGuard implements CanActivate {

    loginModel: LoginModel;

    constructor(private _router: Router, private _session: SessionService) { }

    canActivate() {
        this.loginModel = this._session.restoreStatus(WebModel.LOGIN, new LoginModel());

        if (this.loginModel._loggedIn) {
            console.log('user logged successfully');
            // logged in so return true
            return true;
        }
        console.log('user not logged');
        // not logged in so redirect to login page
        this._router.navigate(['/loggin']);
        return false;
    }
}