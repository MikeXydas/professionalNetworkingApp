import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
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
  constructor(private formBuilder: FormBuilder) { 
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
    console.log(this.register);
  }
  ngOnInit() {
  }

}
