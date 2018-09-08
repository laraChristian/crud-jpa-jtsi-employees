import { Component, OnInit } from '@angular/core';
import { LoginModel } from './login.model';
import { LogginApiService } from '../../services/loggin-api.service';
import { LoginRequest } from '../../mesagges/loginRequest';
import { SEVERITY } from '../ui-utilities/commons-util';
import { WebModel, SessionService } from '../../services/session.service';
import { Router } from '@angular/router';
import { LoginResponse } from '../../mesagges/loginResponse';

@Component({
  selector: 'loggin',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LogginApiService]
})
export class LoginComponent implements OnInit {

  private _loginModel: LoginModel;

  constructor(private _logginService: LogginApiService, private _session: SessionService, private _router: Router) {
    this._loginModel = new LoginModel();
  }

  ngOnInit() {
  }

  public loggin(): void {
    this._logginService.loggin(this._loginModel._loginRequest).subscribe(
      data => this.processResponse(data),
      error => console.error(error)
    );
  }

  public processResponse(loginResponse: LoginResponse) {
    console.log('start -- process-response method');
    if (loginResponse.loggedIn) {
      this._loginModel._loginResponse = loginResponse;
      this._loginModel._loggedIn = true;
      this._session.save(WebModel.LOGIN, this._loginModel);
      this._router.navigate(['/middle-ui']);
    } else {
      this._loginModel.show(SEVERITY.error, 'Atention', loginResponse.message);
    }
    console.log('end -- process-response method');
  }

}


