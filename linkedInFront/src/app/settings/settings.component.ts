import { Component, OnInit } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  providers: [ GetuserService, WelcomeService ],
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  loginedUser;
  user;
  userReceived = false;
  validatedAccess = false;
  newEmail = "";
  newPass = "";
  confimNewPass = "";
  isLoadingPass = false;
  isLoadingEmail = false;
  emailExists = false;

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService) { }

  ngOnInit() {

    this.getuserService.getAccessLevelObs(1)
    .subscribe (
      data=> {
        this.validatedAccess = data;
        if(this.validatedAccess == true) {
          this.loginedUser = this.welcomeService.getLoginedUser();
          console.log(this.loginedUser);
          this.getuserService.getUser(this.loginedUser)
          .subscribe(
            data=> {
              this.user = data;
            }
          );
        }
      }
    );
    
  }

  isPasswordValid() {
    if(this.newPass == "") {
      return false;
    }

    if(this.newPass != this.confimNewPass) {
      return false;
    }

    return true;
  }

  isEmailValid() {
    return this.newEmail != "";
  }

  updateMail() {
    this.isLoadingEmail = true;
    this.user.email = this.newEmail;
    this.getuserService.updateUser(this.user)
    .subscribe(
      data=> {
        this.newEmail = "";
        this.isLoadingEmail = false;
        this.emailExists = false;
      },
      error => {
        this.isLoadingEmail = false;
        this.emailExists = true;
      }
    );
  }

  updatePassword() {
    this.isLoadingPass = true;
    this.user.password = this.newPass;
    this.getuserService.updateUser(this.user)
    .subscribe(
      data=> {
        this.newPass = "";
        this.confimNewPass = "";
        this.isLoadingPass = false;
      },
      error => {
        this.isLoadingPass = false;
      }
    );
  }

}
