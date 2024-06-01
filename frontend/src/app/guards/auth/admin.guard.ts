import {CanActivateFn, Router, UrlSegmentGroup, UrlTree} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";

export const adminGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  let authService = inject(AuthService);
  console.log(authService.getRole());
  if (authService.getRole() == 'ROLE_ADMIN') return true;
  else {
    router.navigate(['/index']).then();
    console.error('You are not an administrator');
    return false;
  }
};
