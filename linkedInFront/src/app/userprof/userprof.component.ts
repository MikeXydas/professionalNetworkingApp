import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';

@Component({
  selector: 'app-userprof',
  templateUrl: './userprof.component.html',
  providers: [ GetuserService, WelcomeService ],
  styleUrls: ['./userprof.component.css']
})
export class UserprofComponent implements OnInit {

  isMine = false;
  userId;
  user;
  skills;
  skillsString = "";
  userReceived = false;
  skillsReceived = false;
  validatedAccess = false;

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
            this.getuserService.getAccessLevelObs(1)
            .subscribe(
              data=> {
                this.validatedAccess = data;
                if(this.welcomeService.getLoginedUser() == this.userId) {
                  this.isMine = true;
                }
                this.userReceived = true; 
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

  isPublicEducation() {
    return (this.user.isPublicEducation == 1) && (this.existsEducationText() == true);
  }

  isPublicJob() {
    return (this.user.isPublicJob == 1) && (this.existsJobText() == true);
  }

  isPublicSkill() {
    return (this.user.isPublicSkill == 1) && (this.existsSkills() == true);
  }

  isProfileMine() {
    return this.isMine;
  }

  isUserReceived() {
    return this.userReceived;
  }

  isAccessValidated() {
    return this.validatedAccess;
  }

  existsEducationText() {
    if((this.userReceived == true) && (this.user.educationText != null) && (this.user.educationText != "") && (this.user.educationText != "NO"))
      return true;
    else
      return false;
  }

  existsJobText() {
    if((this.userReceived == true) && (this.user.jobExperienceText != null) && (this.user.jobExperienceText != "") && (this.user.jobExperienceText != "NO"))
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
