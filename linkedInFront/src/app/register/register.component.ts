import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RegisterUser } from './registerUser'
import { RegisterService } from './register.service'
import { Anwser } from './anwser'

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
              private registerService: RegisterService) { 
    this.registerForm = formBuilder.group( {
      email: ['', Validators.required],
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
    return this.anwser.id == 0;
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

    /*const newUser : RegisterUser = {  email: this.register.email,
                                      password: this.register.password,
                                      firstName: this.register.firstName,
                                      lastName: this.register.lastName,
                                      phoneNumber: this.register.phoneNumber}*/

      const newUser : RegisterUser = {  
        email: this.registerForm.controls['email'].value,
        password: this.registerForm.controls['password'].value,
        firstName: this.registerForm.controls['firstName'].value,
        lastName: this.registerForm.controls['lastName'].value,
        phoneNumber: this.registerForm.controls['phoneNumber'].value
      }
    /*this.registeredUser.email = this.register.email;
    this.registeredUser.email = this.registerForm.controls['password'].value;
    this.registeredUser.email = this.registerForm.controls['firstName'].value;
    this.registeredUser.email = this.registerForm.controls['elastNamemail'].value;
    this.registeredUser.email = this.registerForm.controls['phoneNumber'].value;*/

    //console.log(this.registerService.addUser(newUser).subscribe);
  
    //this.registerService.addUser(newUser)
    //  .subscribe(response => (this.userExists = response));
    //console.log(this.userExists);
    //console.log(newUser);
    this.registerService.addUser(newUser)
      .subscribe((response : Anwser) => (this.anwser = response));
  }
  ngOnInit() {
  }

}
