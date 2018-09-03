import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { SkillsListBean } from './skillsListBean'
import { ConnectionService } from '../connection.service';
import { ConnectionRequest } from '../connectionRequest';
import { ConversationService } from '../conversation/conversation.service';
import { BeginConv } from '../conversation/beginConv';
import { Message } from '../conversation/message';

@Component({
  selector: 'app-userprof',
  templateUrl: './userprof.component.html',
  providers: [ GetuserService, WelcomeService, ConnectionService, ConversationService ],
  styleUrls: ['./userprof.component.css']
})
export class UserprofComponent implements OnInit {

  isMine = false;
  userId;
  user;
  loginedUser;
  skills;
  skillsString = "";
  userReceived = false;
  skillsReceived = false;
  validatedAccess = false;
  updatingUser = false;
  updatingSkills = false;
  editingEducation = false;
  editingSkills = false;
  editingJob = false;
  changedSkills = false;
  isConnection = false;
  connectionList;
  toggledSendMessage = false;
  messageText = "";

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private conversationService : ConversationService,
              private conectionService : ConnectionService) { }

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
                this.loginedUser = this.welcomeService.getLoginedUser();
                this.validatedAccess = data;
                if(this.loginedUser == this.user.idUser) {
                  this.isMine = true;
                }
                this.userReceived = true;

                this.conectionService.getConnectedUsers(this.welcomeService.getLoginedUser())
                .subscribe(
                  data=> {
                    this.connectionList = data;
                    for(var whichConnection = 0; whichConnection < this.connectionList.length; whichConnection++) {
                      if(this.connectionList[whichConnection].idUser == this.user.idUser) {
                        this.isConnection = true;
                        break;
                      }
                    }
                  }
                );
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

  sendMessageButton() {
    this.toggledSendMessage = true;
  }

  sendMessage() {
    const newConv : BeginConv = {
      userId1: this.loginedUser,
      userId2: this.user.idUser
    }

    this.conversationService.beginConversation(newConv)
    .subscribe(
      data => {
        var conv : any = data;
        const newMsg : Message = {
           convId: conv.convId,
           contentText: this.messageText,
           senderId: this.loginedUser
        }

        this.conversationService.sendMessage(newMsg)
        .subscribe(
          data => {
            this.messageText = "";
            console.log("Successfully sent message");
          },
          error => {
            console.log("Failed to send message");
          }
        );
      },
      error => {
        console.log("Failed to begin conv");
      }
    );
  }

  isMessageEmpty() {
    return this.messageText == "";
  }

  sendRequest() {
    const newReq : ConnectionRequest = {
      id : 0,
      receiverId : this.user.idUser,
      sendTime : 0,
      userId : this.loginedUser
    }

    this.conectionService.sendRequest(newReq)
    .subscribe();
  }

  isPublicEducation() {
    return (this.user.isPublicEducation == 1);
  }

  isPublicJob() {
    return (this.user.isPublicJob == 1);
  }

  isPublicSkill() {
    return (this.user.isPublicSkill == 1);
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

  skillCheckEvent(event) {
    if ( event.target.checked ) {
        this.user.isPublicSkill = 1;
    }
    else if( !event.target.checked ) {
      this.user.isPublicSkill = 0;
    } 
  }

  educationCheckEvent(event) {
    if ( event.target.checked ) {
        this.user.isPublicEducation = 1;
    }
    else if( !event.target.checked ) {
      this.user.isPublicEducation = 0;
    } 
  }


  jobCheckEvent(event) {
    if ( event.target.checked ) {
        this.user.isPublicJob = 1;
        console.log("Checked job");
    }
    else if( !event.target.checked ) {
      this.user.isPublicJob = 0;
      console.log("UnChecked job");

    } 
  }

  isChanging() {
    return this.updatingUser || this.updatingSkills;
  }

  confirmChanges() {
    this.updatingUser = true;
    this.updatingSkills = true;
    this.getuserService.updateUser(this.user)
    .subscribe(
      data=> {
        this.updatingUser = false;
        this.editingEducation = false;
        this.editingJob = false;
        this.editingSkills = false;
      }
    );

    if(this.changedSkills == true) {
      const newSkills : SkillsListBean = {
        userId: this.user.idUser,
        skills: this.createSkillsList()
      }
      this.getuserService.insertSkills(newSkills)
      .subscribe(
        data=>{
          this.changedSkills = false;
          this.updatingSkills = false;
        }
      );
    }
    else {
      this.updatingSkills = false;
    }
  }

  isEditingEducation() {
    return this.editingEducation;
  }

  isEditingJob() {
    return this.editingJob;
  }

  isEditingSkills() {
    this.changedSkills = true;
    return this.editingSkills;
  }

  editEducation() {
    this.editingEducation = !this.editingEducation;
  }

  editSkills() {
    this.editingSkills = !this.editingSkills;
  }

  editJob() {
    this.editingJob = !this.editingJob;
  }

  createSkillsList() {
    var start = 0;
    var end = 0;
    var stringList : string[] = [];

    if(this.skillsString.length == 0) {
      return stringList;
    }

    while(end != -1) {
      if((end = this.skillsString.indexOf(", ", start)) == -1) {
        stringList.push(this.skillsString.slice(start));
      }
      else if(start == 0) {
          stringList.push(this.skillsString.slice(start, end));
          start = end + 2
      }
      else {
        if(this.skillsString.length  < start) {
          end = -1;
        }
        else {
          stringList.push(this.skillsString.slice(start, end));
          start = end + 2
        }
      }
    }
    return stringList;
  }
}
