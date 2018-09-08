import { Component } from '@angular/core';
import { SessionService, WebModel } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { LoginModel } from './ui-components/login/login.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor() { }

}
