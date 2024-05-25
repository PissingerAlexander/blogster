import { Component } from '@angular/core';
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {LoginService} from "../../services/auth/login.service";
import {MatIcon} from "@angular/material/icon";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatButton,
    RouterLink,
    MatIcon,
    MatMiniFabButton,
    MatIconButton
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  constructor(private loginService: LoginService, private authService: AuthService) {  }

  public isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  public logout() {
    this.loginService.logout();
  }
}
