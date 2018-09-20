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
  notifications;

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
                  //this.sortRequests();
                  this.heapSortRequests();
                  for(var whichReq = 0; whichReq < this.requests.length; whichReq++) {
                    this.requests[whichReq]['isAccepted'] = false;
                    this.requests[whichReq]['isDeclined'] = false;
                  }
                  this.receivedRequests = true;
                  console.log(this.requests);
                },
                error => {
                  console.log("Failed to receive requests");
                }
              );

              this.notificationsService.getNotifications(this.loginedUser)
              .subscribe(
                data=> {
                  this.notifications = data;
                  this.heapSortNotifications();
                  //this.sortNotifications();
                  this.receivedNotifications = true;
                },
                error => {
                  console.log("Failed to receive notifications")
                }
              )
            }
          );
        }
      }
    );  
  }

  isLoadingComplete() {
    return this.validatedAccess && this.userReceived && this.receivedRequests && this.receivedNotifications;
  }

  transformDate(mseconds) {
    var date = new Date(mseconds);
    return date.toLocaleString();;
  }

  acceptRequest(whichRequest) {
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

  isNotificationsEmpty() {
    return this.notifications.length == 0;
  }
  
  isPending(whichRequest) {
    return !this.requests[whichRequest].isAccepted && !this.requests[whichRequest].isDeclined;
  }

  /*sortRequests() {
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

  sortNotifications() {
    for(var i = 0; i < this.notifications.length; i++) {
      for(var j = 0; j < this.notifications.length - i - 1; j++) {
        if(this.notifications[j].uploadTime < this.notifications[j + 1].uploadTime) {
          var temp = this.notifications[j];
          this.notifications[j] = this.notifications[j + 1];
          this.notifications[j + 1] = temp;
        }
      }
    }
  }*/

  sortNotificationsHeapify(n, i) {
    let largest = i;
    let l = 2*i + 1;
    let r = 2*i + 2;

    if((l < n) && (this.notifications[l].uploadTime < this.notifications[largest].uploadTime)) {
      largest = l;
    }

    if((r < n) && (this.notifications[r].uploadTime < this.notifications[largest].uploadTime)) {
      largest = r;
    }

    if(largest != i) {
      var temp = this.notifications[i];
      this.notifications[i] = this.notifications[largest];
      this.notifications[largest] = temp;

      this.sortNotificationsHeapify(n, largest);
    }
  }

  heapSortNotifications() {

      let n = this.notifications.length;

      for(let i = Math.floor(n/2 - 1); i >= 0; i--) {
        console.log(i);
        this.sortNotificationsHeapify(n, i);
      }

      for(let i = n - 1; i >= 0; i--) {
        var temp = this.notifications[0];
        this.notifications[0] = this.notifications[i];
        this.notifications[i] = temp;

        this.sortNotificationsHeapify(i, 0);
      }

  }

  sortRequestsHeapify(n, i) {
    let largest = i;
    let l = 2*i + 1;
    let r = 2*i + 2;

    if((l < n) && (this.requests[l].sendTime < this.requests[largest].sendTime)) {
      largest = l;
    }

    if((r < n) && (this.requests[r].sendTime < this.requests[largest].sendTime)) {
      largest = r;
    }

    if(largest != i) {
      var temp = this.requests[i];
      this.requests[i] = this.requests[largest];
      this.requests[largest] = temp;

      this.sortRequestsHeapify(n, largest);
    }
  }

  heapSortRequests() {

    let n = this.requests.length;

    for(let i = Math.floor(n/2 - 1); i >= 0; i--) {
      console.log(i);
      this.sortRequestsHeapify(n, i);
    }

    for(let i = n - 1; i >= 0; i--) {
      var temp = this.requests[0];
      this.requests[0] = this.requests[i];
      this.requests[i] = temp;

      this.sortRequestsHeapify(i, 0);
    }
  }

  
}
