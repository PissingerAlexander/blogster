import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {User} from "../../model/user/user";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay, throwError} from "rxjs";
import {UpdateUserInfoRequest} from "../../model/http/update-user-info-request";
import {UpdatePasswordRequest} from "../../model/http/update-password-request";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
    this.getCurrentUserInfo().subscribe(() => {});
  }

  public createUser(
    fullName: string | null,
    username: string,
    role: string,
    mailAddress: string,
    password: string
  ): Observable<User> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    let createUserRequest = {
      role: role,
      username: username,
      password: password,
      fullName: fullName,
      mailAddress: mailAddress
    }
    return this.http.post<User>(environment.apiUrl + '/admin/user/', createUserRequest, options)
      .pipe(shareReplay(1));
  }

  public getCurrentUserInfo(): Observable<User> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json'
      })
    };
    return this.http.get<User>(environment.apiUrl + '/user/', options)
      .pipe(shareReplay(1));
  }

  public updateUserInfo(
    fullName: string | null | undefined,
    username: string | null | undefined,
    mailAddress: string | null | undefined
  ): Observable<User> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json',
        'Content-Type': 'application/json'
      })
    }
    let updateUserInfoRequest: UpdateUserInfoRequest = {
      fullName: fullName,
      username: username,
      mailAddress: mailAddress
    }
    return this.http.put<User>(environment.apiUrl + '/user/', updateUserInfoRequest, options)
      .pipe(shareReplay(1));
  }

  public updatePassword(
    oldPassword: string,
    newPassword: string,
  ): Observable<string> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'test/plain',
        'Content-Type': 'application/json'
      })
    };
    let updatePasswordRequest: UpdatePasswordRequest = {
      oldPassword: oldPassword,
      newPassword: newPassword
    }
    return this.http.put<string>(environment.apiUrl + '/user/password/', updatePasswordRequest, options)
      .pipe(shareReplay(1));
  }

  public getAllUsers(): Observable<User[]> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json'
      })
    };
    return this.http.get<User[]>(environment.apiUrl + '/admin/user/all/', options)
      .pipe(shareReplay(1));
  }

  public deleteUser(user: User): Observable<User> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json',
        'Content-Type': 'application/json'
      }),
      body: user
    };
    return this.http.delete<User>(environment.apiUrl + '/admin/user/', options)
      .pipe(shareReplay(1));
  }

  public handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(`Backend returned code ${error.status}, body was: ${error.error.error}`);
    }

    return throwError(() => new Error('Something bad happened; please try again later'))
  }
}
