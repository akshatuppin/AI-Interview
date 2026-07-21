import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DashboardResponse } from '../models/dashboard-response';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {

  private apiUrl = "http://localhost:8081/api/dashboard";
  
  constructor(private http: HttpClient){}

  getDashboard(): Observable<DashboardResponse>{
    console.log("Calling Dashboard API......");
    console.log(this.apiUrl);

    return this.http.get<DashboardResponse>(this.apiUrl);
  }

}
