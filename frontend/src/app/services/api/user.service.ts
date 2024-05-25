import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {LoginService} from "../auth/login.service";
import {User} from "../../model/user/user";
import {environment} from "../../../environments/environment";
import {throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private loginService: LoginService) { }

  public getCurrentUserInfo() {
    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    }
    return this.http.get<User>(environment.apiUrl + '/user/', options);
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
