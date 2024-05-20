import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {LoginRequest} from "../model/login-request";
import {LoginResponse} from "../model/login-response";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  baseHeader: HttpHeaders = new HttpHeaders({
    'Access-Control-Allow-Origin': '*',
  });

  constructor(private http: HttpClient) { }

  public login(username: string, password: string) {
    let headers: HttpHeaders = this.baseHeader;
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    let options = {
      headers: headers
    }
    let loginRequest: LoginRequest = {
      username: username,
      password: password
    };
    this.http.post<LoginResponse>(environment.apiUrl + '/auth/login', loginRequest, options).subscribe(
      (data) => document.cookie = `accessToken=${data.accessToken}; max-age=60*60*24`
    );
  }
}
