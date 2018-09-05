import { Component, OnInit, AfterViewChecked, ElementRef, ViewChild } from '@angular/core';
import { GetuserService } from '../getuser.service'
import { WelcomeService } from '../welcome/welcome.service';
import { ActivatedRoute } from '@angular/router';
import { ConversationService } from './conversation.service';
import { Message } from './message'

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  providers: [ GetuserService, WelcomeService, ConversationService ],
  styleUrls: ['./conversation.component.css']
})
export class ConversationComponent implements OnInit {

  loginedUser;
  user;
  userReceived = false;
  validatedAccess = false;
  conversationReceived = false;
  receivedMessages = false;
  currentMessages;
  currentConvIndex;
  conversations;
  messageText = "";

  @ViewChild('scrollMe') private myScrollContainer: ElementRef;

  constructor(private route: ActivatedRoute,
              private welcomeService: WelcomeService,
              private getuserService : GetuserService,
              private conversationService : ConversationService) { }

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
            data => {
              this.user = data;
              this.userReceived = true;
              this.conversationService.getConvs(this.loginedUser)
              .subscribe(
                data => {
                  this.conversations = data;
                  this.conversationReceived = true;
                },
                error => {
                  console.log("Failed to receive conversations")
                }
              )
            }
          );
        }
      }
    );  
  }

  scrollToBottom(): void {
    try {
        this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch(err) { 
      console.log("err");
    }                 
  }

  isLoadingComplete() {
    return this.userReceived && this.conversationReceived && this.validatedAccess;
  }

  isConversationsEmpty() {
    return this.conversations.length == 0;
  }

  isMessageMine(whichMsg) {
    return this.currentMessages[whichMsg].senderId == this.loginedUser;
  }

  getMessages(whichConv) {
    this.conversationService.getMessages(this.conversations[whichConv].idConversation)
    .subscribe(
      data=> {
        this.currentMessages = data;
        this.receivedMessages = true;
        this.currentConvIndex = whichConv
        console.log(this.currentMessages);
      },
      error => {
        console.log("Failed to receive messages");
      }
    );
  }

  isMessagesEmpty() {
    return this.currentMessages.length = 0;
  }

  isMessageBoxEmpty() {
    return this.messageText == "";
  }

  sendMessage() {
 
    const newMsg : Message = {
        convId: this.conversations[this.currentConvIndex].idConversation,
        contentText: this.messageText,
        senderId: this.loginedUser
    }

    this.conversationService.sendMessage(newMsg)
    .subscribe(
      data => {
        this.messageText = "";
        this.currentMessages.push(data);
        this.scrollToBottom();
        console.log("Successfully sent message");
      },
      error => {
        console.log("Failed to send message");
      }
    );
  }

  transformDate(mseconds) {
    var date = new Date(mseconds);
    return date.toLocaleString();;
  }
  
}
