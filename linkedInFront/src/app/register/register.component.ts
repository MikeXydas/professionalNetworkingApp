import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RegisterUser } from './registerUser'
import { RegisterService } from './register.service'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  providers: [ RegisterService ],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm;

  register = {
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: ''
  }

  registeredUser : RegisterUser;

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

    console.log(this.register.email);
    const newUser : RegisterUser = {  email: this.register.email,
                                      password: this.register.password,
                                      firstName: this.register.firstName,
                                      lastName: this.register.lastName,
                                      phoneNumber: this.register.phoneNumber}


    /*this.registeredUser.email = this.register.email;
    this.registeredUser.email = this.registerForm.controls['password'].value;
    this.registeredUser.email = this.registerForm.controls['firstName'].value;
    this.registeredUser.email = this.registerForm.controls['elastNamemail'].value;
    this.registeredUser.email = this.registerForm.controls['phoneNumber'].value;*/

    console.log(this.registerService.addUser(newUser));
    //console.log(newUser);

  }
  ngOnInit() {
  }

}
