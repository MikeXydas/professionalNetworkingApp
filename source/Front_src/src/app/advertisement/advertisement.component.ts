import { Component, OnInit } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { AdvertisementService } from './advertisement.service';
import { PostAd } from './postAd';
import { Application } from './application';


@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  providers: [ GetuserService, WelcomeService, AdvertisementService ],
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit {

  loginedUser;
  user;
  userReceived = false;
  validatedAccess = false;
  adsReceived = false;
  advertisements;
  adTitle = "";
  adContext = "";
  adSkills = "";
  adForm;
  applications;
  applicationsReceived = false;
  successfulAdPost = false;
  showingApplications = false;
  successfulApply: boolean[] = [];

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private formBuilder: FormBuilder,
              private advertisementService : AdvertisementService) {
                this.adForm = formBuilder.group( {
                  title: ['', Validators.required],
                  skills: ['', Validators.required],
                  content: ['', Validators.required]
                })
               }

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
            data => {
              this.user = data;
              this.userReceived = true;
              this.advertisementService.getAdSkill(this.loginedUser)
              .subscribe(
                data => {
                  this.advertisements = data;
                  this.sortAdsOnScore();
                  this.adsReceived = true;
                  for(var i = 0; i < this.advertisements.length; i++) {
                    this.successfulApply.push(false);
                  }
                  console.log("Successfully received ads");
                },
                error => {
                  console.log(error);
                }
              );
            }
          );
        }
      }
    );  
  }

  isLoadingComplete() {
    return this.userReceived && this.adsReceived && this.validatedAccess;
  }

  isAdsEmpty() {
    return this.advertisements.length == 0;
  }

  transformDate(mseconds) {
    var date = new Date(mseconds);
    return date.toLocaleString();;
  }

  getApplications() {
    this.applicationsReceived = false;
    if(this.showingApplications == false) {
      this.advertisementService.getApplications(this.loginedUser)
      .subscribe(
        data=> {
          this.applications = data;
          this.applicationsReceived = true;
          this.showingApplications = true;
        },
        error => {
          console.log("Failed to receive applications");
        }
      );
    }
    else {
      this.showingApplications = false;
    }
  }

  isAppsEmpty() {
    if(this.applicationsReceived == false) {
      return true;
    }
    return this.applications.length == 0;
  }

  sortAdsOnScore() {
    for(var i = 0; i < this.advertisements.length; i++) {
      for(var j = 0; j < this.advertisements.length - i - 1; j++) {
        if(this.advertisements[j].score < this.advertisements[j + 1].score) {
          var temp = this.advertisements[j];
          this.advertisements[j] = this.advertisements[j + 1];
          this.advertisements[j + 1] = temp;
        }
      }
    }
  }

  isInvalid(control) {
    return this.adForm.controls[control].invalid
  }

  isAdInvalid() {
    if (!this.adForm.controls['content'].value.replace(/\s/g, '').length) {
      return true;
    }
    if (!this.adForm.controls['title'].value.replace(/\s/g, '').length) {
      return true;
    }
    if (!this.adForm.controls['skills'].value.replace(/\s/g, '').length) {
      return true;
    }
    return this.isInvalid('title') || this.isInvalid('skills') || this.isInvalid('content');
  }

  logOutCLick() {
    this.welcomeService.logout();
  }
  
  sendAd() {
    const newAd : PostAd = {
      userId: this.loginedUser,
      descriptionText: this.adForm.controls['content'].value,
      title: this.adForm.controls['title'].value,
      skills: this.createSkillsList()
    }

    this.advertisementService.postAd(newAd)
    .subscribe(
      data=> {
        this.adForm.setValue({
          title: '',
          content: '',
          skills: '',
        });
        this.successfulAdPost = true;
        console.log("Succesfully posted ad");
      },
      error => {
        console.log("Failed to upload ad");
      }
    );
  }

  applyToAd(whichAd) {
    const newApp : Application = {
      adId: this.advertisements[whichAd].adId,
      userId: this.loginedUser
    }

    this.advertisementService.applyToAd(newApp)
    .subscribe(
      data => {
        this.successfulApply[whichAd] = true;
        console.log("Successfully applied to ad");
      },
      error => {
        console.log("Failed to apply to ad");
      }
    );
  }

  printSkills(whichAd) {
    var retString = "";
    for(var i = 0; i < this.advertisements[whichAd].skills.length; i++) {
      if(i != 0) {
        retString += ", ";
      }
      retString += this.advertisements[whichAd].skills[i];
    }

    return retString;
  }
  
  createSkillsList() {
    var start = 0;
    var end = 0;
    var stringList : string[] = [];

    if(this.adForm.controls['skills'].value.length == 0) {
      return stringList;
    }

    while(end != -1) {
      if((end = this.adForm.controls['skills'].value.indexOf(", ", start)) == -1) {
        stringList.push(this.adForm.controls['skills'].value.slice(start));
      }
      else if(start == 0) {
          stringList.push(this.adForm.controls['skills'].value.slice(start, end));
          start = end + 2
      }
      else {
        if(this.adForm.controls['skills'].value.length  < start) {
          end = -1;
        }
        else {
          stringList.push(this.adForm.controls['skills'].value.slice(start, end));
          start = end + 2
        }
      }
    }
    return stringList;
  }
}
