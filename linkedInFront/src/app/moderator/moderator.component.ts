import { Component, OnInit } from '@angular/core';
import { ModeratorService} from './moderator.service'
import { FormBuilder, FormGroup, FormArray, FormControl, ValidatorFn } from '@angular/forms';
import { WelcomeService } from '../welcome/welcome.service';
import { UserIds } from './userIds';
import {saveAs as importedSaveAs} from "file-saver";
import { GetuserService } from '../getuser.service'

@Component({
  selector: 'app-moderator',
  templateUrl: './moderator.component.html',
  providers: [ ModeratorService, GetuserService ],
  styleUrls: ['./moderator.component.css']
})
export class ModeratorComponent implements OnInit {

  users; 
  validatedAccess = false;

  constructor(
    private moderatorService: ModeratorService ,
    private welcomeService: WelcomeService,
    private getuserService : GetuserService
  ) {

  }

  checkedItems: number[] = [];
  ngOnInit() {

    this.getuserService.getAccessLevelObs(2)
    .subscribe(
      data=> {
        this.validatedAccess = data;
        if(this.validatedAccess == true) {
          this.moderatorService.getUsers()
          .subscribe(
            data=> {
              this.users = data;
              for(var whichU = 0; whichU<this.users.length; whichU++ ) {
                this.checkedItems.push(0);
              }
            },
            error => {
              console.log(error);
            }
          );
        }
      },
      error => {
        console.log("Failed to validate access level");
      }
    );
  }

  changeBox($event) {
    let elementId: string = (event.target as Element).id;

    if(this.checkedItems[parseInt(elementId)] == 0) {
      this.checkedItems[parseInt(elementId) ] = 1
    }
    else {
      this.checkedItems[parseInt(elementId)] = 0;
    }
  }

  requestXML() {
    var finalIds : number[] =[];

    for(var whichUser = 0; whichUser < this.checkedItems.length; whichUser++) {

      if(this.checkedItems[whichUser] == 1) {

        finalIds.push(this.users[whichUser].idUser);
      }
    }
    if(finalIds.length > 0) {
      const userIds : UserIds = {userIds : finalIds};
      this.moderatorService.getXML(userIds).subscribe(
        data => {
          importedSaveAs(data,"Users.xml");
          let fileURL = URL.createObjectURL(data);
          window.open(fileURL);
        },
        error => {
          console.log(error);
        }
      );
    }
  }

  logOutCLick() {
    this.welcomeService.logout();
  }
}
