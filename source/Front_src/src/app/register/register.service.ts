import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { GlobalVariable } from '../global'
import { map } from 'rxjs/operators';

import { RegisterUser } from './registerUser'
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
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

  /*addUser (registerUser: RegisterUser): Observable<RegisterUser> {
    return this.http.post<RegisterUser>(this.apiRoot + "User/add", registerUser, httpOptions)
      .pipe(
        catchError(this.handleError('addUser', registerUser))
      );*/

    addUser (registerUser) {
      /*return this.http.post(this.apiRoot + "User/add", registerUser, httpOptions)
        .pipe(
          catchError(this.handleError('addUser', registerUser))
        );*/

      //return this.http.post(this.apiRoot + "User/add", registerUser, httpOptions).pipe(map((response: any) => response.text()));
      return this.http.post(this.apiRoot + "User/add", registerUser, httpOptions);
      //return this.http.post(this.apiRoot + "User/add", registerUser, httpOptions).map(res => res.text());

    }


}
