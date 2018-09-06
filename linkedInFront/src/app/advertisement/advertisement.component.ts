import { Component, OnInit } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute } from '@angular/router';
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

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private advertisementService : AdvertisementService) { }

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
                  this.adsReceived = true;
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

  isAdValid() {
    return (this.adContext != "") && (this.adSkills != "") && (this.adTitle != "");
  }

  sendAd() {
    const newAd : PostAd = {
      userId: this.loginedUser,
      descriptionText: this.adContext,
      title: this.adTitle,
      skills: this.createSkillsList()
    }

    this.advertisementService.postAd(newAd)
    .subscribe(
      data=> {
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
        console.log("Successfully applied to ad");
      },
      error => {
        console.log("Failed to apply to ad");
      }
    );
  }

  createSkillsList() {
    var start = 0;
    var end = 0;
    var stringList : string[] = [];

    if(this.adSkills.length == 0) {
      return stringList;
    }

    while(end != -1) {
      if((end = this.adSkills.indexOf(", ", start)) == -1) {
        stringList.push(this.adSkills.slice(start));
      }
      else if(start == 0) {
          stringList.push(this.adSkills.slice(start, end));
          start = end + 2
      }
      else {
        if(this.adSkills.length  < start) {
          end = -1;
        }
        else {
          stringList.push(this.adSkills.slice(start, end));
          start = end + 2
        }
      }
    }
    return stringList;
  }
}
