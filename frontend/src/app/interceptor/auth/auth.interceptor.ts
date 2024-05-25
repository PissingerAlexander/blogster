import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  let accessToken = '';
  document.cookie.split(';').forEach(cookie => {
    cookie = cookie.trim();
    if (cookie.startsWith('accessToken=')) accessToken = cookie.split('=')[1];
  });
  if (accessToken === '') return next(req);

  const authenticatedRequest = req.clone({
    setHeaders: {
      'Authorization': 'Bearer ' + accessToken
    }
  });

  return next(authenticatedRequest);
};
