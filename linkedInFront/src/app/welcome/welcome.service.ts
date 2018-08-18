import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { GlobalVariable } from '../global'
import { HttpHeaders } from '@angular/common/http';

import { LoginForm } from './loginForm'

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'responseType': 'text/plain'
  })
};

@Injectable({
  providedIn: 'root'
})
export class WelcomeService {

  constructor(private http: HttpClient) { }

    login(loginForm : LoginForm) {
        return this.http.post<any>(GlobalVariable.BASE_API_URL + `User/login`, loginForm, httpOptions)
            .pipe(map(user => {
                //console.log("User exists");
                // login successful if there's a jwt token in the response
                if (user && user.token) {
                    
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
                return user;
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
    }
}
