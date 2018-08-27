import { Component, OnInit } from '@angular/core';
import { NetworkService } from './network.service';
import { ActivatedRoute } from '@angular/router';
import { GetuserService } from '../getuser.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { SearchForm } from './searchForm'

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
  error;
  searchResults;
  constructor(private route: ActivatedRoute,
    private networkService: NetworkService,
    private getUserService: GetuserService,
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
      }
    );
  }
  
  isValidated(){
    return this.accessValidated;
  }

  isLoading(){
    return this.loading;
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
        //this.router.navigate(['results'])
        console.log(this.searchResults);
      },
      error => {
        this.error = error;
        this.loading = false;
        
      }
    );

  }
}

