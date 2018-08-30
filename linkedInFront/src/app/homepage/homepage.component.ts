import { Component, OnInit } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute } from '@angular/router';
import { HomepageService } from './homepage.service';
import { Article } from './article';
import { Comment } from './comment';
import { Interest } from './interest';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  providers: [ GetuserService, WelcomeService, HomepageService ],
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {

  loginedUser;
  user;
  articles;
  network;
  userReceived = false;
  articlesReceived = false;
  networkReceived = false
  validatedAccess = false;
  loadingComplete = false;

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private homepageService : HomepageService) { }

  ngOnInit() {
    this.getuserService.getAccessLevelObs(1)
    .subscribe (
      data=> {
        this.validatedAccess = data;
        if(this.validatedAccess == true) {
          this.loginedUser = this.welcomeService.getLoginedUser();
          console.log(this.loginedUser);
          this.getuserService.getUser(this.loginedUser)
          .subscribe(
            data=> {
              this.user = data;
              this.userReceived = true;
              console.log(this.user);
            },
            error => {
              console.log("Failed to receive user");
            }
          );

          this.homepageService.getNetwork(this.loginedUser)
          .subscribe(
            data=> {
              this.network = data;
              this.networkReceived = true;
              console.log(this.network);
            },
            error => {
              console.log("Failed to receive network");
            }
          );

          this.homepageService.getConnectedArticles(this.loginedUser)
          .subscribe(
            data => {
              this.articles = data;
              this.sortArticles();
              this.articlesReceived = true;
              console.log(this.articles);
            },
            error => {
              console.log("Failed to receive articles");
            }
          );
        }
      }
    );
  }

  isLoadingComplete() {
    return this.validatedAccess && this.userReceived
            && this.networkReceived && this.articlesReceived;
  }

  sortArticles() {
    for(var i = 0; i < this.articles.length; i++) {
      for(var j = 0; j < this.articles.length - i - 1; j++) {
        if(this.articles[j].uploadTime < this.articles[j + 1].uploadTime) {
          var temp = this.articles[j];
          this.articles[j] = this.articles[j + 1];
          this.articles[j + 1] = temp;
        }
      }
    }
  }

  connectionsExist() {
    return this.network.length != 0;
  }

  articlesExist() {
    return this.articles.length != 0;
  }

}
