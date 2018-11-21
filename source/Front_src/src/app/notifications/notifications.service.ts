import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { GlobalVariable } from '../global'


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient) { }

  getPendingRequests(id) {
    return this.http.get(this.apiRoot + "ConnectionRequest/pending/" + id);
  }

  acceptRequest(id) {
    return this.http.post(this.apiRoot + "ConnectionRequest/accept/" + id, null);
  }

  declineRequest(id) {
    return this.http.post(this.apiRoot + "ConnectionRequest/decline/" + id, null);
  }

  getNotifications(id) {
    return this.http.get(this.apiRoot + "Article/notifications/" + id);
  }
}
