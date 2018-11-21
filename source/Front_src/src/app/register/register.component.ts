import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RegisterUser } from './registerUser'
import { RegisterService } from './register.service'
import { Anwser } from './anwser'
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  providers: [ RegisterService ],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm;

  /*register = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: ''
  }*/

  registeredUser : RegisterUser;
  anwser : Anwser = {id: 1};
  userExists : any;

  constructor(private formBuilder: FormBuilder,
              private registerService: RegisterService,
              private router :Router) {
    this.registerForm = formBuilder.group( {
      email: ['', [Validators.required, Validators.pattern(/^\S*$/)]],
      confirmPassword: ['', Validators.required],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phoneNumber: ['', Validators.required]
    });
  }

  isInvalid(control) {
    return this.registerForm.controls[control].invalid &&
            this.registerForm.controls[control].touched
  }

  isPasswordIncorrect() {
    return this.registerForm.controls['password'].value != this.registerForm.controls['confirmPassword'].value
  }

  isEmailUsed() {
    if(this.anwser.id == 0)
      return true;
  }
  isIncomplete() {
    return this.isInvalid('email') ||
    this.isInvalid('password') ||
    this.isInvalid('confirmPassword') ||
    this.isInvalid('firstName') ||
    this.isInvalid('lastName') ||
    this.isInvalid('phoneNumber') ||
    this.isPasswordIncorrect();
            
  }
  onSubmit() {
    
      const newUser : RegisterUser = {  
        email: this.registerForm.controls['email'].value,
        password: this.registerForm.controls['password'].value,
        firstName: this.registerForm.controls['firstName'].value,
        lastName: this.registerForm.controls['lastName'].value,
        phoneNumber: this.registerForm.controls['phoneNumber'].value
      }
    this.registerService.addUser(newUser)
      .subscribe((response : Anwser) => {
        this.anwser = response;
        if(this.anwser.id != 0){
          this.router.navigate(['/']);
        }
      });
  }
  ngOnInit() {
  }

}
