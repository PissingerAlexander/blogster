import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay, Subject} from "rxjs";
import {CookieService} from "./cookie.service";
import {GetAccessTokenResponse} from "../../model/spotify/http/get_access_token/GetAccessTokenResponse";

@Injectable({
  providedIn: 'root'
})
export class SpotifyAuthService {

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
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
    this.cookieService.setCookie('spotifyRefreshToken', refreshToken, 86400);
  }

  public requestAccessTokenWithRefreshToken(): Observable<GetAccessTokenResponse> {
    return this.http.post<GetAccessTokenResponse>(environment.apiUrl + '/spotify/access_token?refresh_token=' + this.cookieService.getCookie('spotifyRefreshToken'), "")
  }

  public getSpotifyAccessToken() {
    return this.cookieService.getCookie('spotifyAccessToken');
  }

  public getSpotifyRefreshToken() {
    return this.cookieService.getCookie('spotifyRefreshToken');
  }

  public unsetRefreshToken() {
    this.cookieService.unsetCookie('spotifyRefreshToken')
  }
}
