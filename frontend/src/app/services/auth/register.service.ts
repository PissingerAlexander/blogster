import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {RegisterRequest} from "../../model/http/register/RegisterRequest";
import {environment} from "../../../environments/environment";
import {catchError, Observable, shareReplay, throwError} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../../model/user/user";
import {RegisterResponse} from "../../model/http/register/RegisterResponse";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient, private route: Router) { }

  public register(fullName: string | null, username: string, mailAddress: string, password: string): Observable<RegisterResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    let registerRequest: RegisterRequest = {
      username: username,
      password: password,
      fullName: fullName,
      mailAddress: mailAddress
    }
    if (fullName) registerRequest.fullName = fullName;
    return this.http.post<RegisterResponse>(environment.apiUrl + '/auth/register/', registerRequest, options)
      .pipe(shareReplay(1));
  }

  redirectToLoginPage(): void {
    this.route.navigate(['/login']).then();
  }
}
