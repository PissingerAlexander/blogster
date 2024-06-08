import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {User} from "../../model/user/user";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay, throwError} from "rxjs";
import {UpdateUserInfoRequest} from "../../model/http/update_user/UpdateUserInfoRequest";
import {UpdatePasswordRequest} from "../../model/http/update_password/UpdatePasswordRequest";
import {GetUserResponse} from "../../model/http/get_user/GetUserResponse";
import {UpdateUserInfoResponse} from "../../model/http/update_user/UpdateUserInfoResponse";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {  }

  public createUser(
    fullName: string | null,
    username: string,
    role: string,
    mailAddress: string,
    password: string
  ): Observable<GetUserResponse> {
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
    return this.http.post<GetUserResponse>(environment.apiUrl + '/admin/user/', createUserRequest, options)
      .pipe(shareReplay(1));
  }

  public getCurrentUserInfo(): Observable<GetUserResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json'
      })
    };
    return this.http.get<GetUserResponse>(environment.apiUrl + '/user/', options)
      .pipe(shareReplay(1));
  }

  public updateUserInfo(
    fullName: string | null | undefined,
    username: string | null | undefined,
    mailAddress: string | null | undefined
  ): Observable<UpdateUserInfoResponse> {
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
    return this.http.put<UpdateUserInfoResponse>(environment.apiUrl + '/user/', updateUserInfoRequest, options)
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

  public deleteUser(user: User): Observable<GetUserResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accepts': 'application/json',
        'Content-Type': 'application/json'
      }),
      body: user
    };
    return this.http.delete<GetUserResponse>(environment.apiUrl + '/admin/user/', options)
      .pipe(shareReplay(1));
  }
}
