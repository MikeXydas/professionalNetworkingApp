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
export class HomepageService {

  apiRoot = GlobalVariable.BASE_API_URL;
  constructor(private http: HttpClient) { }

  getNetwork(id) {
    return this.http.get(this.apiRoot + "ConnectionRequest/connections/" + id);
  }

  getConnectedArticles(id) {
    return this.http.get(this.apiRoot + "Article/showArticles/" + id);
  }

  postArticle(article) {
    return this.http.post(this.apiRoot + "Article/post", article, httpOptions);
  }

  postComment(comment) {
    return this.http.post(this.apiRoot + "Article/postComment", comment, httpOptions);
  }

  showInterest(interest) {
    return this.http.post(this.apiRoot + "Article/showInterest", interest, httpOptions);
  }
}
