import { Routes } from '@angular/router';
import { IndexComponent } from "./pages/index/index.component";
import { LoginPageComponent } from "./pages/login-page/login-page.component";
import { RegisterPageComponent } from "./pages/register-page/register-page.component";
import {ProfileInfoPageComponent} from "./pages/profile-info-page/profile-info-page.component";



export const routes: Routes = [
  { path: 'register', component: RegisterPageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'index', component: IndexComponent },
  { path: 'profile', component: ProfileInfoPageComponent },
  { path: '**', component: IndexComponent }
];
