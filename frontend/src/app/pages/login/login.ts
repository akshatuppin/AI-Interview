import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from "@angular/router";
import { Auth } from '../../services/auth';
import { LoginResponse } from '../../models/login-response';
import { LoginRequest } from '../../models/login-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  constructor(private authService:Auth){}

  loginForm = new FormGroup({

    email: new FormControl('',[
      Validators.required,
      Validators.email
    ]),

    password: new FormControl('',[
      Validators.required,
      Validators.minLength(6)
    ])
  });

  get email (){
    return this.loginForm.get('email');
  }

  get password(){
    return this.loginForm.get('password');
  }

  


  onSubmit(){
    
    if(this.loginForm.invalid){
      return ;
    }

    const loginRequest: LoginRequest = {
      email: this.email!.value!,

      password: this.password!.value!
    };

    this.authService.login(loginRequest).subscribe({
      next: (response: LoginResponse) =>{

        localStorage.setItem("token", response.token);

        localStorage.setItem("email", response.email);

        localStorage.setItem("role", response.roel);

        console.log("Login Successfull");

        console.log(response);
      },

      error: (error) => {
        console.log(error); 
      }
    })
    
  }
  
}

