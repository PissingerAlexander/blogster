import {HttpHandlerFn, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";
import {SpotifyAuthService} from "../../services/auth/spotify-auth.service";
import {environment} from "../../../environments/environment";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.startsWith(environment.apiUrl)) return addSpringAccessToken(req, next);
  else if (req.url.startsWith(environment.spotifyApiUrl)) return addSpotifyAccessToken(req, next);
  else return next(req);
};

function addSpringAccessToken(req: HttpRequest<any>, next: HttpHandlerFn) {
  const authService = inject(AuthService);
  let accessToken = authService.getAccessToken();
  if (!accessToken) return next(req);

  const authenticatedRequest = req.clone({
    setHeaders: {
      'Authorization': 'Bearer ' + accessToken
    }
  });

  return next(authenticatedRequest);
}

function addSpotifyAccessToken(req: HttpRequest<any>, next: HttpHandlerFn) {
  const spotifyAuthService = inject(SpotifyAuthService);
  let spotifyAccessToken = spotifyAuthService.getSpotifyAccessToken();
  if (!spotifyAccessToken) return next(req);

  const authenticatedRequest = req.clone({
    setHeaders: {
      'Authorization': 'Bearer ' + spotifyAccessToken
    }
  });

  return next(authenticatedRequest);
}
