import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpErrorHandler }     from './http-error-handler.service';

import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';

import { JwtInterceptor } from './_helpers/jwt.interceptor';
import { ErrorInterceptor } from './_helpers/error.interceptor';
import { ModeratorComponent } from './moderator/moderator.component';
import { UsermodComponent } from './usermod/usermod.component';
import { WelcomeService} from './welcome/welcome.service'
import { GetuserService } from './getuser.service';
import { ConnectionService } from './connection.service'

import { UserprofComponent } from './userprof/userprof.component';
import { SettingsComponent } from './settings/settings.component';
import { NetworkComponent } from './network/network.component';
import { HomepageComponent } from './homepage/homepage.component'

var routes  =[
  {
    path: '',
    component: WelcomeComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'moderator',
    component: ModeratorComponent
  },
  {
    path: 'usermod/:id',
    component: UsermodComponent
  },
  {
    path: 'userprof/:id',
    component: UserprofComponent
  },
  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'network',
    component: NetworkComponent
  },
  {
    path: 'homepage',
    component: HomepageComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    WelcomeComponent,
    ModeratorComponent,
    UsermodComponent,
    UserprofComponent,
    SettingsComponent,
    NetworkComponent,
    HomepageComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [HttpErrorHandler,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    WelcomeService,
    GetuserService,
    ConnectionService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
