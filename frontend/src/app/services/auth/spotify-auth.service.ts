import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {catchError, Observable, shareReplay, throwError} from "rxjs";
import {CookieService} from "./cookie.service";
import {GetAccessTokenResponse} from "../../model/spotify/http/get_access_token/GetAccessTokenResponse";
import {handleErrorAndShowSnackBar} from "../../components/ErrorSnackBar/HandleErrorAndShowSnackBar";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class SpotifyAuthService {

  constructor(
    private http: HttpClient,
    private cookieService: CookieService,
    private snackBar: MatSnackBar
  ) { }

  public authorizeSpotify(): Observable<{ redirectUrl: string }> {
    return this.http.get<{ redirectUrl: string }>(environment.apiUrl + '/spotify/authorize/')
      .pipe(shareReplay(1));
  }

  public requestAccessTokenWithAuthorizationCode(code: string): Observable<GetAccessTokenResponse> {
    return this.http.post<GetAccessTokenResponse>(environment.apiUrl + '/spotify/access_token?code=' + code, "")
      .pipe(shareReplay(1));
  }

  public setSpotifyTokens(accessToken: string, refreshToken: string) {
    this.cookieService.setCookie('spotifyAccessToken', accessToken, 3600);
    if (refreshToken) this.cookieService.setCookie('spotifyRefreshToken', refreshToken, 86400);
  }

  public requestAccessTokenWithRefreshToken(): Observable<GetAccessTokenResponse> {
    return this.http.post<GetAccessTokenResponse>(environment.apiUrl + '/spotify/access_token?refresh_token=' + this.cookieService.getCookie('spotifyRefreshToken'), "")
  }

  public getSpotifyAccessToken() {
    if (!this.cookieService.getCookie('spotifyAccessToken'))
      this.requestAccessTokenWithRefreshToken()
        .pipe(catchError((error: HttpErrorResponse) => {
          handleErrorAndShowSnackBar(error.error.error, this.snackBar);
          return throwError(() => new Error('Something bad happened; please try again later'));
        }))
        .subscribe(res => {
          this.setSpotifyTokens(res.data!.access_token, res.data!.refresh_token);
        });

    return this.cookieService.getCookie('spotifyAccessToken');
  }

  public getSpotifyRefreshToken() {
    return this.cookieService.getCookie('spotifyRefreshToken');
  }

  public unsetRefreshToken() {
    this.cookieService.unsetCookie('spotifyRefreshToken')
  }
}
