import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "../../model/user/user";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay} from "rxjs";
import {GetUserResponse} from "../../model/user/http/get_user/GetUserResponse";
import {CreateUserRequest} from "../../model/user/http/create_user/CreateUserRequest";
import {UpdateUserInfoResponse} from "../../model/user/http/update_user/UpdateUserInfoResponse";
import {UpdateUserInfoRequest} from "../../model/user/http/update_user/UpdateUserInfoRequest";
import {UpdatePasswordResponse} from "../../model/user/http/update_password/UpdatePasswordResponse";
import {UpdatePasswordRequest} from "../../model/user/http/update_password/UpdatePasswordRequest";
import {DeleteUserResponse} from "../../model/user/http/delete_user/DeleteUserResponse";
import {GetUserPageResponse} from "../../model/user/http/get_page/GetUserPageResponse";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
  }

  public createUser(
    fullName: string | null,
    username: string,
    role: string,
    mailAddress: string,
    password: string
  ): Observable<GetUserResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    let createUserRequest: CreateUserRequest = {
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
        'Accept': 'application/json'
      })
    };
    return this.http.get<GetUserResponse>(environment.apiUrl + '/user/', options)
      .pipe(shareReplay(1));
  }

  public updateUserInfo(
    fullName: string,
    username: string,
    mailAddress: string
  ): Observable<UpdateUserInfoResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
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
  ): Observable<UpdatePasswordResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    let updatePasswordRequest: UpdatePasswordRequest = {
      oldPassword: oldPassword,
      newPassword: newPassword
    }
    return this.http.put<UpdatePasswordResponse>(environment.apiUrl + '/user/password/', updatePasswordRequest, options)
      .pipe(shareReplay(1));
  }

  public getAllUsers(): Observable<User[]> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<User[]>(environment.apiUrl + '/admin/user/all/', options)
      .pipe(shareReplay(1));
  }

  public getUserPage(page: number, size: number): Observable<GetUserPageResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<GetUserPageResponse>(environment.apiUrl + `/admin/user/all?page=${page}&size=${size}`, options)
      .pipe(shareReplay(1));
  }

  public deleteUser(user: User): Observable<DeleteUserResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.delete<DeleteUserResponse>(environment.apiUrl + '/admin/user/' + user.id, options)
      .pipe(shareReplay(1));
  }
}
