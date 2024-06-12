import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable, shareReplay} from "rxjs";
import {Router} from "@angular/router";
import {RegisterResponse} from "../../model/user/http/register/RegisterResponse";
import {RegisterRequest} from "../../model/user/http/register/RegisterRequest";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient, private router: Router) {
  }

  public register(fullName: string | null, username: string, mailAddress: string, password: string): Observable<RegisterResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
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
    this.router.navigate(['/login']).then();
  }
}
