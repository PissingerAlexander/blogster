import { Component } from '@angular/core';
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {Router, RouterLink} from "@angular/router";
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
  constructor(private authService: AuthService, private router: Router) {  }

  public isAuthenticated(): boolean {
    return this.authService.isLoggedIn();
  }

  public logout() {
    this.authService.logout();
    this.redirectToLoginPage();
  }

  public isAdmin(): boolean {
    return this.authService.getRole() === 'ROLE_ADMIN';
  }

  private redirectToLoginPage(): void {
    this.router.navigate(['/login']).then();
  }
}
