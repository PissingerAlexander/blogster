import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {LoginRequest} from "../model/http/login-request";
import {LoginResponse} from "../model/http/login-response";
import {throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {
  }

  public login(username: string, password: string) {
    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    }
    let loginRequest: LoginRequest = {
      username: username,
      password: password
    };
    return this.http.post<LoginResponse>(environment.apiUrl + '/auth/login', loginRequest, options);
  }

  public logout() {
    document.cookie = 'accessToken=; expires=Thu, 01 Jan 1970 00:00:01 GMT; SameSite=None; Secure';
  }

  public isAuthenticated() {
    let accessToken = this.getAccessToken();
    return accessToken.length > 0;
  }

  public getAccessToken() {
    let accessToken = '';
    document.cookie.split(';').forEach(cookie => {
      cookie = cookie.trim();
      if (cookie.startsWith('accessToken=')) accessToken = cookie.split('=')[1];
    })
    return accessToken
  }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.log('An error occurred:', error.error);
    } else {
      console.error(`Backend returned code ${error.status}, body was: ${error.error}`);
    }

    return throwError(() => new Error('Something bad happened; please try again later'))
  }
}
