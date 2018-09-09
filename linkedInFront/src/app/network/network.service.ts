import { Injectable } from '@angular/core';
import { GlobalVariable } from '../global'
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
export class NetworkService {
  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient) { }

  getFriendList(loginedUser){
    return this.http.get(this.apiRoot + "ConnectionRequest/connections/" + loginedUser)
  }

  getSearchedUsers(searchForm){
    return this.http.post(this.apiRoot + "User/search",searchForm,httpOptions);
  }

}
