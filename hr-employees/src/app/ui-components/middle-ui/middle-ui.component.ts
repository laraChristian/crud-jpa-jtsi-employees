import { Component, OnInit } from '@angular/core';
import { SessionService, WebModel } from '../../services/session.service';
import { Router } from '@angular/router';
import { LoginModel } from '../login/login.model';

@Component({
  selector: 'middle-ui',
  templateUrl: './middle-ui.component.html',
  styleUrls: ['./middle-ui.component.css']
})
export class MiddleUiComponent implements OnInit {

  private show: boolean;

  constructor(private session: SessionService, private _router: Router) { }

  ngOnInit() {
    this.show = true;
  }

  public showToLoggedUsers(): boolean {
    let lm: LoginModel = this.session.restoreStatus(WebModel.LOGIN, new LoginModel());
    console.log(lm);
    return lm._loggedIn;
  }

  public logout(): void {
    this.session.logout();
    this._router.navigate(['/loggin']);
  }

  public haveFocus(){
    if (this._router.url === '/middle-ui'){
      this.show = true;
    }else{
      this.show = false;
    }
    return this.show;
  }

}
