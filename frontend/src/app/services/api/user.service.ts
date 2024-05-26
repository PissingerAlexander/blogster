import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {User} from "../../model/user/user";
import {environment} from "../../../environments/environment";
import {shareReplay, throwError} from "rxjs";
import {UpdateUserInfoRequest} from "../../model/http/update-user-info-request";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getCurrentUserInfo() {
    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      })
    }
    return this.http.get<User>(environment.apiUrl + '/user/', options)
      .pipe(shareReplay(1));
  }

  public updateUserInfo(
    fullName: string | null | undefined,
    username: string | null | undefined,
    mailAddress: string | null | undefined
  ) {
    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    let updateUserInfoRequest: UpdateUserInfoRequest = {
      fullName: fullName,
      username: username,
      mailAddress: mailAddress
    }
    return this.http.put(environment.apiUrl + '/user/', updateUserInfoRequest, options)
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
}
