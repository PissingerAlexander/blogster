import { Routes } from '@angular/router';
import { IndexComponent } from "./pages/index/index.component";
import { LoginComponent } from "./components/login/login.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent }, // TODO: LoginPageComponent
  { path: 'index', component: IndexComponent },
  { path: '**', component: IndexComponent }
];
