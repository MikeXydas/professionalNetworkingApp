import { Component, OnInit } from '@angular/core';
import { ModeratorService} from './moderator.service'
import { FormBuilder, FormGroup, FormArray, FormControl, ValidatorFn } from '@angular/forms';
import { map } from 'rxjs/operators';
import { UserIds } from './userIds';
import {saveAs as importedSaveAs} from "file-saver";

@Component({
  selector: 'app-moderator',
  templateUrl: './moderator.component.html',
  providers: [ ModeratorService ],
  styleUrls: ['./moderator.component.css']
})
export class ModeratorComponent implements OnInit {

  users; 

  constructor(
    private moderatorService: ModeratorService 
  ) {

  }

  checkedItems: number[] = [];
  ngOnInit() {
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

}
