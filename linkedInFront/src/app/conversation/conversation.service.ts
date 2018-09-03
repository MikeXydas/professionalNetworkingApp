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
export class ConversationService {
  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient) { }

  beginConversation(conv) {
    return this.http.post(this.apiRoot + "Conversation/begin", conv, httpOptions);
  }

  sendMessage(msg) {
    return this.http.post(this.apiRoot + "Message/send", msg, httpOptions);
  }

  getMessages(convId) {
    return this.http.get(this.apiRoot + "Conversation/showMessages/" + convId);
  }

  getConvs(userId) {
    return this.http.get(this.apiRoot + "Conversation/showConvs/" + userId);
  }
}
