import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { WelcomeService } from './welcome.service';
import { Router, ActivatedRoute } from '@angular/router';
import { first } from 'rxjs/operators';

import { LoginForm } from './loginForm'

//import { AuthenticationService } from '../_services/authentication.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  providers: [ WelcomeService ],
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  loginForm;
  returnUrl: string;
  loading = false;
  submitted = false;
  error = '';
  loginFailed = 0;
  constructor(private formBuilder: FormBuilder,
              private welcomeService: WelcomeService,
              private route: ActivatedRoute,
              private router: Router) {
                this.loginForm = formBuilder.group( {
                    email: "",
                    password: ""
                });
               }

  ngOnInit() {
    this.welcomeService.logout();

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  failedLogin () {
    return this.loginFailed == 1;
  }

  isLoading() {
    return this.loading;
  }

  onSubmit() {
    const newForm : LoginForm = {
      email: this.loginForm.controls['email'].value,
      password: this.loginForm.controls['password'].value
    }
    this.loading = true;
    this.welcomeService.login(newForm)
    .pipe(first())
    .subscribe(
      data=> {
        console.log("DEBUG: Id of successfully loggined user: " + this.welcomeService.getLoginedUser());
        this.loginFailed = 0;
      },
      error => {
        this.error = error;
        //console.log("error");  
        this.loginFailed = 1;
        this.loading = false;
      }
    );

  }

}
