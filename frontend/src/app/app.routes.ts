import { Routes } from '@angular/router';
import { IndexComponent } from "./pages/index/index.component";
import { LoginPageComponent } from "./pages/login-page/login-page.component";
import {RegisterComponent} from "./components/register/register.component";



export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'index', component: IndexComponent },
  { path: '**', component: IndexComponent }
];
