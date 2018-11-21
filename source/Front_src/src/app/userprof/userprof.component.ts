import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { SkillsListBean } from './skillsListBean'
import { ConnectionService } from '../connection.service';
import { ConnectionRequest } from '../connectionRequest';
import { ConversationService } from '../conversation/conversation.service';
import { BeginConv } from '../conversation/beginConv';
import { Message } from '../conversation/message';
import { SendPhoto } from './sendPhoto';

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
  editingPhoto = false;
  changedSkills = false;
  isConnection = false;
  connectionList;
  toggledSendMessage = false;
  messageText = "";
  photo = null;
  profilePhoto;
  receivedPhoto = false;
  photoExists = false;
  form: FormGroup;

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private conversationService : ConversationService,
              private conectionService : ConnectionService,
              private fb: FormBuilder) { 
                this.createForm();
              }

  ngOnInit() {
    this.route.paramMap.subscribe(
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
                this.getProfilePhoto();
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

  /*photoSelected(event){
    console.log(event);
    this.photo = event.target.files[0]
  }

  encodeImageFileAsURL(element) {
    var file = element.files[0];
    var reader = new FileReader();
    reader.onloadend = function() {
      console.log('RESULT', reader.result)
      const newPhoto : SendPhoto = {
        userId: 142,
        photo: reader.result
      }
      this.getuserService.updatePhoto(newPhoto)
      .subscribe(
        data=> {
          console.log("Succesfully send photo");
        },
        error=> {
          console.log("Failed to send photo");
        }
      );
    }
    reader.readAsDataURL(file);

    
  }*/

  loading: boolean = false;

  @ViewChild('fileInput') fileInput: ElementRef;


  /*onFileChange(event) {
    let reader = new FileReader();
    if(event.target.files && event.target.files.length > 0) {
      let file = event.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.form.get('userId').setValue(this.loginedUser);
        this.form.get('avatar').setValue({
          filename: file.name,
          filetype: file.type,
          value: (reader.result as string).split(',')[1]
        })
      };
    }
  }*/

  onFileChange(event) {
    let reader = new FileReader();
    if(event.target.files && event.target.files.length > 0) {
      let file = event.target.files[0];
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.form.get('userId').setValue(this.loginedUser);
        this.form.get('avatar').setValue((reader.result as string).split(',')[1]);
        this.form.get('fileType').setValue(this.getImageType(file.type));
      };
    }
  }

  onSubmit() {
    this.receivedPhoto = false;
    const formModel = this.form.value;
    this.loading = true;
    const newPhoto : SendPhoto = {
      userId: this.loginedUser,
      photo: this.form.controls['avatar'].value,
      fileType: this.form.controls['fileType'].value
    }
    console.log(newPhoto);
    this.getuserService.updatePhoto(newPhoto)
    .subscribe(
      data=> {
        this.getProfilePhoto();
        console.log("Successfully uploaded Image");
      },
      error=> {
        console.log("Failed to upload Image");
      }
    );
    
    
  }

  getImageType(type) {
    var temp = type.split("/");
    return temp[1];
  }

  createForm() {
    this.form = this.fb.group({
      userId: -1,
      avatar: null,
      fileType: ""
    });
  }

  getProfilePhoto() {
    this.getuserService.getProfPhoto(this.userId)
    .subscribe(
      data=> {
        this.profilePhoto = data;
        console.log(this.profilePhoto);
        if(this.profilePhoto.userId != -1) {
          this.photoExists = true;
          //this.profilePhoto.photo = btoa(this.profilePhoto.photo);
        }
        this.receivedPhoto = true;
      }
    )
  }
  /*editPhoto(){
    console.log(this.photo);
    const newPhoto : SendPhoto = {
      userId: 142,
      photo: this.getBase64Image(this.photo)
    }*/

    
    /*var promise = this.getBase64(this.photo);
    promise.then(function(result) {
      console.log("RESULT: " + result);
      const newPhoto : SendPhoto = {
        userId: 142,
        photo: result
      }
  
      //this.getBase64(this.photo);
      this.getuserService.updatePhoto(newPhoto)
      .subscribe(
        data=> {
          console.log("Succesfully send photo");
        },
        error=> {
          console.log("Failed to send photo");
        }
      );
    }); */
  //}

  getBase64(file) {
    return new Promise(function(resolve, reject) {
        var reader = new FileReader();
        reader.onload = function() { resolve(reader.result); };
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
  }

  getBase64Image(img) {
    // Create an empty canvas element
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;

    // Copy the image contents to the canvas
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0);

    // Get the data-URL formatted image
    // Firefox supports PNG and JPEG. You could check img.src to
    // guess the original format, but be aware the using "image/jpg"
    // will re-encode the image.
    var dataURL = canvas.toDataURL("image/png");

    return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
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
    return this.userReceived && this.receivedPhoto;
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

  isEditingPhoto(){
    return this.editingPhoto;
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
