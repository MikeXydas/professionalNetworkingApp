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

  userId;
  user;
  userReceived = false;
  validatedAccess = false;
  newEmail = "";
  newPass = "";
  confimNewPass = "";

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService) { }

  ngOnInit() {

    this.getuserService.getAccessLevelObs(1)
    .subscribe (
      data=> {
        this.validatedAccess = data;
        if(this.validatedAccess == true) {
          this.userId = this.welcomeService.getLoginedUser();
          console.log(this.userId);
          this.getuserService.getUser(this.userId)
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
    this.user.email = this.newEmail;
    this.getuserService.updateUser(this.user)
    .subscribe();
  }

  updatePassword() {
    this.user.password = this.newPass;
    this.getuserService.updateUser(this.user)
    .subscribe();
  }

}
