import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {RegisterComponent} from "../../components/register/register.component";

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [
    HeaderComponent,
    RegisterComponent
  ],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.scss'
})
export class RegisterPageComponent {

}
