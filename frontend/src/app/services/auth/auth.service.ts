import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoginRequest} from "../../model/http/login-request";
import {LoginResponse} from "../../model/http/login-response";
import {environment} from "../../../environments/environment";
import {shareReplay} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private accessToken: string | undefined;

  constructor(private http: HttpClient) {  }

  public login(username: string, password: string) {
    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    };
    let loginRequest: LoginRequest = {
      username: username,
      password: password
    };
    return this.http.post<LoginResponse>(environment.apiUrl + '/auth/login', loginRequest, options)
      .pipe(shareReplay(1));
  }

  public setAccessToken(accessToken: string) {
    this.accessToken = accessToken;
    document.cookie = `accessToken=${accessToken}; Max-Age=86400; SameSite=None; Secure`;
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

  public logout() {
    document.cookie = 'accessToken=; expires=Thu, 01 Jan 1970 00:00:01 GMT; SameSite=None; Secure';
    delete this.accessToken;
  }

  public isLoggedIn() {
    return !!this.accessToken;
  }

  public isLoggedOut() {
    return !this.isLoggedIn();
  }

  public getAccessToken() {
    return this.accessToken;
  }
}
