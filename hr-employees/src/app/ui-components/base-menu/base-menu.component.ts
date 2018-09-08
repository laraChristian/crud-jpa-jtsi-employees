import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/components/common/menuitem';
import { SessionService, WebModel } from '../../services/session.service';
import { Router, RouterLink } from '@angular/router';
import { LoginModel } from '../login/login.model';

@Component({
  selector: 'base-menu',
  templateUrl: './base-menu.component.html',
  styleUrls: ['./base-menu.component.css']
})
export class BaseMenuComponent implements OnInit {

  _items: MenuItem[];

  constructor(private session: SessionService, private _router: Router) { }

  ngOnInit() {
    this._items = [
      {
        label: 'Home',
        icon: 'fa fa-fw fa-dashcube',
        items: [
          { label: 'Home', icon: 'fa fa-fw fa-home', url: 'http://localhost:4200/#/middle-ui' },
          { label: 'Users', icon: 'fa fa-fw fa-group', routerLink: 'users-ui' },
          { label: 'Departments', icon: 'fa fa-fw fa-suitcase', routerLink: 'departments-ui' },
          { label: 'Jobs', icon: 'fa fa-cubes', routerLink: 'jobs-ui' },
          {
            label: 'Regions', icon: 'fa fa-shirtsinbulk', items: [
              { label: 'Regions', icon: 'fa fa-shield', routerLink:'regions-ui' },
              { label: 'Countries', icon: 'fa fa-bookmark', routerLink: 'countries-ui' },
              { label: 'Locations', icon: 'fa fa-flag-o', routerLink: 'locations-ui' }

            ]
          }
        ]
      },
      {
        label: 'Exit',
        icon: 'fa fa-fw fa-minus',
        command: (onclick) => this.logout()
      }
    ];
  }

  public showToLoggedUsers(): boolean {
    let lm: LoginModel = this.session.restoreStatus(WebModel.LOGIN, new LoginModel());
    return lm._loggedIn;
  }

  public logout(): void {
    this.session.logout();
    this._router.navigate(['/loggin']);
  }

}
