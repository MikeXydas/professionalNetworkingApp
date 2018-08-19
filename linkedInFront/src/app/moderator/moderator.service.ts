import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { GlobalVariable } from '../global'
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'responseType': 'text/xml'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ModeratorService {

  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get(this.apiRoot + "User/users");
  }

  getXML(userIds) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(this.apiRoot + "User/getXML", userIds, { headers, responseType: 'blob'});
  }
  
}

