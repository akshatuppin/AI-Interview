import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/login-request';
import { LoginResponse } from '../models/login-response';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  
  constructor(private http:HttpClient){}

  login(data:LoginRequest){
    return this.http.post<LoginResponse>(
      'http://localhost:8081/api/auth/login',
      data
    );
  }
}
