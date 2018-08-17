import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { GlobalVariable } from '../global'

import { RegisterUser } from './registerUser'
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable()

export class RegisterService {
  private handleError: HandleError;

  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient,
             httpErrorHandler: HttpErrorHandler) { 
                this.handleError = httpErrorHandler.createHandleError('RegisterService');
             }

  addUser (registerUser) {
    var addUrl = this.apiRoot + "User/add";
    console.log(addUrl);
    console.log(registerUser);
    this.http.post(addUrl, registerUser, httpOptions).subscribe();
  }


}
