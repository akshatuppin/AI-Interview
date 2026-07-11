import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Dashboard } from './pages/dashboard/dashboard';
import { Interview } from './pages/interview/interview';
import { Profile } from './pages/profile/profile';
import { History } from './pages/history/history';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path:'login',
        component: Login
    },
    {
        path: 'register',
        component: Register
    },
    {
        path: 'dashboard',
        component: Dashboard
    },
    {
        path: 'interview',
        component: Interview
    },
    {
        path: 'history',
        component: History
    },
    {
        path: 'profile',
        component: Profile
    }
];
