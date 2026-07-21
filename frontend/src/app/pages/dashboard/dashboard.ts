import { Component, OnInit, signal } from '@angular/core';
import { DashboardService } from '../../services/dashboard';
import { DashboardResponse } from '../../models/dashboard-response';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit{

  dashboard = signal<DashboardResponse | null>(null);
  
  constructor(
    private dashboardService: DashboardService,
    private router: Router
  ){}


  ngOnInit(): void {
    this.loadDashboard();
  }


  loadDashboard(){
    this.dashboardService.getDashboard().subscribe({
      next: (response) => {
        this.dashboard.set(response);
      },

      error: (error) => {
        console.log(error);
      }
    });
  }


  continueInterview(sessionId: number){
    this.router.navigate(['/interview', sessionId]);
  }

  startInterview(){
    this.router.navigate(['/interview']);
  }

  history(){
    this.router.navigate(['/history']);
  }

}
