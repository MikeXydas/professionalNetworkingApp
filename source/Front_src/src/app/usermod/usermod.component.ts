import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { Observable} from 'rxjs';

@Component({
  selector: 'app-usermod',
  templateUrl: './usermod.component.html',
  providers: [ GetuserService, WelcomeService ],
  styleUrls: ['./usermod.component.css']
})
export class UsermodComponent implements OnInit {

  userId;
  user;
  skills;
  skillsString = "";
  userReceived = false;
  skillsReceived = false;
  validatedAccess = false;
  photoExists = false;

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService) { }

  ngOnInit() {
    this.userId = this.route.paramMap.subscribe(
      params=> {
        this.userId = params.get('id');
        this.getuserService.getUser(this.userId)
        .subscribe(
          data=> {
            this.user = data;
            this.userReceived = true;
            if(this.user.photoBean.userId != -1) {
              this.photoExists = true;
            }
            this.getuserService.getAccessLevelObs(2)
            .subscribe(
              data=> {
                this.validatedAccess = data;
              }
            );
          }
        );

        this.getuserService.getSkills(this.userId)
        .subscribe(
          data=> {
            this.skills = data;
            for(var whichSkill = 0; whichSkill < this.skills.length; whichSkill++) {
              if(whichSkill != 0) {
                this.skillsString += ", "
              }
              this.skillsString += this.skills[whichSkill].skillName;
            }
            this.skillsReceived = true;
            
          }
        );

      }

    );
  }

  isUserReceived() {
    return this.userReceived;
  }

  isAccessValidated() {
    return this.validatedAccess;
  }


  existsEducationText() {
    if((this.userReceived == true) 
    && (this.user.educationText != null) 
    && (this.user.educationText != "") 
    && (this.user.educationText != "NO") 
    && (this.user.educationText != "EMPTY"))
      return true;
    else
      return false;
  }

  existsJobText() {
    if((this.userReceived == true) 
    && (this.user.jobExperienceText != null) 
    && (this.user.jobExperienceText != "") 
    && (this.user.jobExperienceText != "NO")
    && (this.user.jobExperienceText != "EMPTY"))
      return true;
    else
      return false;
  }

  existsSkills() {
    if((this.skillsReceived == true) && (this.skills.length != 0))
      return true;
    else
      return false;
  }

  logOutCLick() {
    this.welcomeService.logout();
  }

}
