import { Injectable } from '@angular/core';
import { GlobalVariable } from './global'
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {
  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient) { }

  getConnectedUsers(id) {
    return this.http.post(this.apiRoot + "ConnectionRequest/connections", "{\"id\":" + id + "}",httpOptions);
  }

  sendRequest(req) {
    return this.http.post(this.apiRoot + "ConnectionRequest/send", req, httpOptions);
  }
}
