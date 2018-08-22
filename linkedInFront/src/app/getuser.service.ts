import { Injectable } from '@angular/core';
import { GlobalVariable } from './global'
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { WelcomeService } from './welcome/welcome.service'
import { Observable} from 'rxjs';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class GetuserService {
  apiRoot = GlobalVariable.BASE_API_URL;
  user;

  constructor(private http: HttpClient,
              private welcomeService: WelcomeService) {

  }

  getUser(userId) {
    return this.http.get(this.apiRoot + "User/" + userId);
  }

  getSkills(userId) {
    return this.http.get(this.apiRoot + "User/skill/" + userId);
  }

  //0 -> Guest, 1 -> User, 2 -> Moderator
  getAccessLevelObs (wantedAccessLevel) :Observable<boolean> {


    const simpleObservable = new Observable((observer) => {
    
      if(localStorage.getItem('currentUser') == null) {
        if(wantedAccessLevel != 0)
        this.welcomeService.logout();
        observer.next(false);
      }
  
      var receivedAccessLevel = 3;
      var userId = this.welcomeService.getLoginedUser();
      this.getUser(userId)
      .subscribe(
        data=> {
          this.user = data;
          if(this.user.isModerator == 0) {
            receivedAccessLevel = 1;
          }
          else if(this.user.isModerator == 1) {
            receivedAccessLevel = 2;
          }
          else {
            receivedAccessLevel = 0;
          }
          if(receivedAccessLevel != wantedAccessLevel) {
            this.welcomeService.logout();
            observer.next(false);
          }
          else {
            observer.next(true);
          }
        }
      );
      return simpleObservable;
  })
  return simpleObservable;
    /*if(localStorage.getItem('currentUser') == null) {
      if(wantedAccessLevel != 0)
      this.welcomeService.logout();
      return false;
    }

    var receivedAccessLevel = 3;
    var userId = this.welcomeService.getLoginedUser();
    console.log(userId);
    this.getUser(userId)
    .subscribe(
      data=> {
        this.user = data;
        console.log(this.user.isModerator);
        if(this.user.isModerator == 0) {
          receivedAccessLevel = 1;
        }
        else if(this.user.isModerator == 1) {
          console.log("Return2");
          receivedAccessLevel = 2;
        }
        else {
          receivedAccessLevel = 0;
        }
        if(receivedAccessLevel != wantedAccessLevel) {
          this.welcomeService.logout();
          return false;
        }
        else {
          return true;
        }
      }
    );

    return false;*/
  }

  updateUser(user) {
    return this.http.post(this.apiRoot + "User/update", user, httpOptions);
  }

  insertSkills(skills) {
    return this.http.post(this.apiRoot + "User/insertSkill", skills, httpOptions);
  }

  /*getConnectedUsers(id) {
    return this.http.post(this.apiRoot + "ConnectionRequest/connections", "{\"id\": 42}",httpOptions);
  }*/
}
