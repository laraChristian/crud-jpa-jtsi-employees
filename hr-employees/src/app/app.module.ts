import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

//app components
import { LoginComponent } from './ui-components/login/login.component';
import { MiddleUiComponent } from './ui-components/middle-ui/middle-ui.component';
import { UsersUiComponent } from './ui-components/users-ui/users-ui.component';
import { BaseMenuComponent } from './ui-components/base-menu/base-menu.component';
import { DepartmentsUiComponent } from './ui-components/departments-ui/departments-ui.component';
import { JobsUiComponent } from './ui-components/jobs-ui/jobs-ui.component';
import { LocationsUiComponent } from './ui-components/locations-ui/locations-ui.component';

//primeng modules 
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { KeyFilterModule } from 'primeng/keyfilter';
import { PanelModule } from 'primeng/panel';
import { ButtonModule } from 'primeng/button';
import { GrowlModule } from 'primeng/growl';
import { PanelMenuModule } from 'primeng/panelmenu';
import { DropdownModule } from 'primeng/dropdown';
import { InputMaskModule } from 'primeng/inputmask';
import { TableModule } from 'primeng/table';
import { PasswordModule } from 'primeng/password';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { DialogModule } from 'primeng/dialog';
import { SplitButtonModule } from 'primeng/splitbutton';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { TooltipModule } from 'primeng/tooltip';
import { ToastModule } from 'primeng/toast';
import { GMapModule } from 'primeng/gmap';


//primeng message services
import { MessageService } from '../../node_modules/primeng/components/common/messageservice';

//sevices components
import { HttpModule } from '@angular/http';
import { CookieService } from 'angular2-cookie/services/cookies.service';


//session components
import { LocalStorageModule } from 'angular-2-local-storage';
import { SessionService } from './services/session.service';
import { AuthGuard } from './security/auth-guard';
import { LogginGuard } from './security/loggin-guard';
import { routing } from './routing-conf/app-routes';
import { CommonModule } from '../../node_modules/@angular/common';
import { CountriesUiComponent } from './ui-components/countries-ui/countries-ui.component';
import { RegionsUiComponent } from './ui-components/regions-ui/regions-ui.component';



@NgModule({
  declarations: [
    AppComponent,
    UsersUiComponent,
    BaseMenuComponent,
    LoginComponent,
    MiddleUiComponent,
    DepartmentsUiComponent,
    JobsUiComponent,
    LocationsUiComponent,
    CountriesUiComponent,
    RegionsUiComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    PanelMenuModule,
    KeyFilterModule,
    PanelModule,
    ButtonModule,
    GrowlModule,
    routing,
    HttpModule,
    DropdownModule,
    InputMaskModule,
    TableModule,
    PasswordModule,
    ConfirmDialogModule,
    MessagesModule,
    DialogModule,
    SplitButtonModule,
    ScrollPanelModule,
    TooltipModule,
    ToastModule,
    GMapModule,
    LocalStorageModule.withConfig({
      prefix: 'my-app',
      // storageType: 'localStorage'
      storageType: 'sessionStorage'
    })
  ],
  providers: [SessionService, AuthGuard, LogginGuard, MessageService, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
