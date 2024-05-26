import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {RegisterRequest} from "../../model/http/register-request";
import {environment} from "../../../environments/environment";
import {catchError, shareReplay, throwError} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient, private route: Router) { }

  public register(fullName: string | null, username: string, mailAddress: string, password: string) {
    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };
    let registerRequest: RegisterRequest = {
      username: username,
      mailAddress: mailAddress,
      password: password
    }
    if (fullName) registerRequest.fullName = fullName;
    return this.http.post(environment.apiUrl + '/auth/register', registerRequest, options)
      .pipe(shareReplay(1));
  }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.log('An error occurred:', error.error);
    } else {
      console.error(`Backend returned code ${error.status}, body was: ${error.error}`);
    }

    return throwError(() => new Error('Something bad happened; please try again later'))
  }

  redirectToLoginPage() {
    this.route.navigate(['/login']).then(() => console.log('You need to login first'));
  }
}
