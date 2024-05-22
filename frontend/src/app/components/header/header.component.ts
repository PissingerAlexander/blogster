import { Component } from '@angular/core';
import {MatButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatButton,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  constructor(private loginService: LoginService) {  }

  public isAuthenticated(): boolean {
    return this.loginService.isAuthenticated();
  }

  public logout() {
    this.loginService.logout();
  }
}
