import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  let accessToken = authService.getAccessToken();
  if (!accessToken) return next(req);

  const authenticatedRequest = req.clone({
    setHeaders: {
      'Authorization': 'Bearer ' + accessToken
    }
  });

  return next(authenticatedRequest);
};
