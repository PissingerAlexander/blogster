import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay} from "rxjs";
import {LoginResponse} from "../../model/user/http/login/LoginResponse";
import {LoginRequest} from "../../model/user/http/login/LoginRequest";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private accessToken: string | undefined;
  private spotifyToken: string | undefined;

  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string): Observable<LoginResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    let loginRequest: LoginRequest = {
      username: username,
      password: password
    };
    return this.http.post<LoginResponse>(environment.apiUrl + '/auth/login/', loginRequest, options)
      .pipe(shareReplay(1));
  }

  public setAccessToken(accessToken: string) {
    this.accessToken = accessToken;
    document.cookie = `accessToken=${accessToken}; max-age=43200; path=/; samesite=None; secure`;
    this.loadAccessToken();
  }

  public setSpotifyToken(accessToken: string) {
    this.spotifyToken = accessToken;
    document.cookie = `spotifyToken=${accessToken}; max-age=3600; path=/; samesite=None; secure`;
    this.loadSpotifyToken();
  }

  public loadAccessToken() {
    let accessToken = '';
    document.cookie.split(';').forEach(cookie => {
      cookie = cookie.trim();
      if (cookie.startsWith('accessToken=')) accessToken = cookie.split('=')[1];
    });
    if (accessToken) this.accessToken = accessToken;
    else delete this.accessToken;
  }

  public loadSpotifyToken() {
    let spotifyToken = '';
    document.cookie.split(';').forEach(cookie => {
      cookie = cookie.trim();
      if (cookie.startsWith('spotifyToken=')) spotifyToken = cookie.split('=')[1];
    });
    if (spotifyToken) this.spotifyToken = spotifyToken;
    else delete this.spotifyToken;
  }

  public logout() {
    document.cookie = 'accessToken=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/; samesite=None; secure';
    document.cookie = 'spotifyToken=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/; samesite=None; secure';
    delete this.accessToken;
    delete this.spotifyToken;
  }

  public isLoggedIn() {
    return !!this.accessToken;
  }

  public isLoggedOut() {
    return !this.isLoggedIn();
  }

  public getAccessToken() {
    if (!this.accessToken) return '';
    return this.accessToken;
  }

  public getSpotifyToken() {
    if (!this.spotifyToken) return '';
    return this.spotifyToken;
  }

  public getRole() {
    if (!this.accessToken) return '';
    return JSON.parse(atob(this.accessToken.split('.')[1])).authorities[0]
  }

  public getId() {
    return JSON.parse(atob(this.accessToken!.split('.')[1])).sub;
  }
}
