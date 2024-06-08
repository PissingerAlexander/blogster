import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {LoginComponent} from "../../components/user/login/login.component";

@Component({
  selector: 'app-login-page',
  host: {
    class: 'page'
  },
  standalone: true,
  imports: [
    HeaderComponent,
    LoginComponent
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {

}
