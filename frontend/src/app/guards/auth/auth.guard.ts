import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";

export const authGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  let authService = inject(AuthService);
  if (authService.isLoggedOut()) {
    router.navigate(['/index']).then();
    console.error('You are not logged in');
    return authService.isLoggedIn();
  } else return authService.isLoggedIn();
};
