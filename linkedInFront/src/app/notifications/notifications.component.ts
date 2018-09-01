import { Component, OnInit } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute } from '@angular/router';
import { NotificationsService } from './notifications.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  providers: [ GetuserService, WelcomeService, NotificationsService ],
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  loginedUser;
  user;
  userReceived = false;
  validatedAccess = false;
  receivedRequests = false;
  receivedNotifications = false;

  requests;

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private notificationsService : NotificationsService) { }

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
              this.userReceived = true;
              //Receive pending requests
              this.notificationsService.getPendingRequests(this.loginedUser)
              .subscribe(
                data=> {
                  this.requests = data;
                  this.receivedRequests = true;
                  this.sortRequests();
                  for(var whichReq = 0; whichReq < this.requests.length; whichReq++) {
                    this.requests[whichReq]['isAccepted'] = false;
                    this.requests[whichReq]['isDeclined'] = false;
                  }
                  console.log(this.requests);

                },
                error => {
                  console.log("Failed to receive requests");
                }
              )
            }
          );
        }
      }
    );  
  }

  //ADD RECEIVED NOTIFICATIONS
  isLoadingComplete() {
    return this.validatedAccess && this.userReceived && this.receivedRequests;
  }

  transformDate(mseconds) {
    var date = new Date(mseconds);
    return date.toLocaleString();;
  }

  acceptRequest(whichRequest) {
    console.log("aa")
    this.notificationsService.acceptRequest(this.requests[whichRequest].reqId)
    .subscribe(
      data=> {
        console.log("Accepted request");
        this.requests[whichRequest].isAccepted = true;
      },
      error => {
        console.log("Failed to accept request");
      }
    );
  }

  declineRequest(whichRequest) {
    this.notificationsService.declineRequest(this.requests[whichRequest].reqId)
    .subscribe(
      data=> {
        console.log("Declined request");
        this.requests[whichRequest].isDeclined = true;
      },
      error => {
        console.log("Failed to decline request");
      }
    );
  }

  isRequestsEmpty() {
    return this.requests.length == 0;
  }
  isPending(whichRequest) {
    return !this.requests[whichRequest].isAccepted && !this.requests[whichRequest].isDeclined;
  }

  sortRequests() {
    for(var i = 0; i < this.requests.length; i++) {
      for(var j = 0; j < this.requests.length - i - 1; j++) {
        if(this.requests[j].sendTime < this.requests[j + 1].sendTime) {
          var temp = this.requests[j];
          this.requests[j] = this.requests[j + 1];
          this.requests[j + 1] = temp;
        }
      }
    }
  }


}
