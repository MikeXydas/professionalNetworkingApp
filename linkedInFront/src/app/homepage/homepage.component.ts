import { Component, OnInit } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute, ChildActivationStart } from '@angular/router';
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
  articleTitle = "";
  articleContext = "";
  articleCollapsed = true;
  successfulArticlePost = false;

  commentBoxes: string[] = [];
  successfulComment: boolean[] = [];

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
              //this.sortArticles();
              this.sortComments();
              for(var i = 0; i < this.articles.length; i++) {
                this.commentBoxes.push("");
                this.successfulComment.push(false);
              }
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

  sortComments() {
    for(var whichArticle = 0; whichArticle < this.articles.length; whichArticle++) {
      for(var i = 0; i < this.articles[whichArticle].comments.length; i++) {
        for(var j = 0; j < this.articles[whichArticle].comments.length - i - 1; j++) {
          if(this.articles[whichArticle].comments[j].uploadTime < this.articles[whichArticle].comments[j + 1].uploadTime) {
            var temp = this.articles[whichArticle].comments[j];
            this.articles[whichArticle].comments[j] = this.articles[whichArticle].comments[j + 1];
            this.articles[whichArticle].comments[j + 1] = temp;
          }
        }
      }
    }
  }

  showInterest(whichArticle) {
    const newInterest : Interest = {
      articleId: this.articles[whichArticle].idArticle,
      interesterId: this.loginedUser
    }

    for(var whichInterest = 0; whichInterest < this.articles[whichArticle].interests.length; whichInterest++) {
      if(this.articles[whichArticle].interests[whichInterest].interesterId == this.loginedUser) {
        return;
      }
    }
    console.log(this.articles[whichArticle].idArticle)
    this.homepageService.showInterest(newInterest)
    .subscribe(
      data=> {
        this.articles[whichArticle].interests.push(data);
        console.log("Succesfully showed interest");
      },
      error => {
        console.log("Failed to show interest");
      }
    );
  }

  postComment(whichArticle) {
    const newComment : Comment = {
      articleId: this.articles[whichArticle].idArticle,
      commenterId: this.loginedUser,
      text: this.commentBoxes[whichArticle]
    }

    this.homepageService.postComment(newComment)
    .subscribe(
      data =>{
        this.articles[whichArticle].comments.unshift(data);
        console.log("Successfully posted comment");
        this.commentBoxes[whichArticle] = "";
        this.successfulComment[whichArticle] = true;
      },
      error => {
        console.log("Failed tp post comment");
      }
    );
  }

  collapseButton() {
    this.articleCollapsed = !this.articleCollapsed;
  }

  isArticleValid() {
    if (!this.articleTitle.replace(/\s/g, '').length) {
      return false;
    }

    if (!this.articleContext.replace(/\s/g, '').length) {
      return false;
    }

    return (this.articleTitle != "") && (this.articleContext != "");
  }

  postArticle() {
    const newArticle : Article = {
      userId: this.loginedUser,
      title: this.articleTitle,
      contentText:this.articleContext
    }

    this.homepageService.postArticle(newArticle)
    .subscribe(
      data=> {
        this.articleCollapsed = true;
        this.articles.unshift(data);
        this.successfulArticlePost = true;
        this.articleContext = "";
        this.articleTitle = "";
      },
      error=> {
        console.log(error);
      }
    )
  }

  connectionsExist() {
    return this.network.length != 0;
  }

  articlesExist() {
    return this.articles.length != 0;
  }

  commentBoxValid(whichArticle) {
    if (!this.commentBoxes[whichArticle].replace(/\s/g, '').length) {
      return false;
    }
    return this.commentBoxes[whichArticle] != "";
  }

  commentsExist(whichArticle) {
    
    return this.articles[whichArticle].comments.length != 0;
  }

  transformDate(mseconds) {
    var date = new Date(mseconds);
    return date.toLocaleString();;
  }


}
