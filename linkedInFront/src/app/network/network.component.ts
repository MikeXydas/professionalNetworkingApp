import { Component, OnInit } from '@angular/core';
import { NetworkService } from './network.service';
import { ActivatedRoute } from '@angular/router';
import { GetuserService } from '../getuser.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { SearchForm } from './searchForm'
import { WelcomeService } from '../welcome/welcome.service';

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  providers: [ NetworkService],
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {
  accessValidated = false;
  searchForm;
  loading = false;
  submittedSearch = false;
  receivedFriends = false;
  error;
  searchResults;
  loginedUser;
  friendList;
  constructor(private route: ActivatedRoute,
    private networkService: NetworkService,
    private getUserService: GetuserService,
    private welcomeService: WelcomeService,
    private router: Router,
    private formBuilder: FormBuilder){
      this.searchForm = formBuilder.group({
        firstName: "",
        lastName: ""
      }) ;
    }

  ngOnInit() {
    this.getUserService.getAccessLevelObs(1)
    .subscribe(
      data => {
        this.accessValidated = data;
        if(this.accessValidated == true){
          this.loginedUser = this.welcomeService.getLoginedUser();
          console.log(this.loginedUser) /**debug */
          this.networkService.getFriendList(this.loginedUser)
          .subscribe(
            data => {
              this.friendList = data;
              this.receivedFriends = true;
            },
            error => {
              this.error = error;
            }
          );

        }

      }
    ); 
  }


  profRedirectList(whichFriend) {
    this.router.navigate(['/userprof', this.friendList[whichFriend].idUser]);
  }

  searchResultsRedirectList(whichResult) {
    this.router.navigate(['/userprof', this.searchResults[whichResult].idUser]);
  }
  
  isValidated(){
    return this.accessValidated;
  }

  isLoading(){
    return this.loading;
  }

  emptyFriends() {
    return this.friendList.length == 0;
  }

  emptySearchResults() {
    return this.searchResults.length == 0;
  }

  logOutCLick() {
    this.welcomeService.logout();
  }
  
  onSubmit(){
    const newForm : SearchForm = {
      firstName: this.searchForm.controls['firstName'].value,
      lastName: this.searchForm.controls['lastName'].value
    }
    this.loading = true;
    this.networkService.getSearchedUsers(newForm)
    .subscribe(
      data=> {
        this.searchResults = data;
        this.submittedSearch = true;
        //this.router.navigate(['results'])
        console.log(this.searchResults);
        this.loading = false;
      },
      error => {
        this.error = error;
        this.loading = false;
        
      }
    );
  }
}

