import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay} from "rxjs";
import {LoginResponse} from "../../model/user/http/login/LoginResponse";
import {LoginRequest} from "../../model/user/http/login/LoginRequest";
import {CookieService} from "./cookie.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) {
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
    this.cookieService.setCookie('accessToken', accessToken, 43200)
  }

  public logout() {
    this.cookieService.unsetCookie('accessToken');
  }

  public isLoggedIn() {
    return !!this.cookieService.getCookie('accessToken');
  }

  public isLoggedOut() {
    return !this.isLoggedIn();
  }

  public getAccessToken() {
    return this.cookieService.getCookie('accessToken');
  }

  public getRole() {
    if (!this.cookieService.getCookie('accessToken')) return '';
    return JSON.parse(atob(this.cookieService.getCookie('accessToken')!.split('.')[1])).authorities[0]
  }

  public getId() {
    return JSON.parse(atob(this.cookieService.getCookie('accessToken')!.split('.')[1])).sub;
  }
}
