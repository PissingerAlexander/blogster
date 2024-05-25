import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public getAccessToken() {
    let accessToken = '';
    document.cookie.split(';').forEach(cookie => {
      cookie = cookie.trim();
      if (cookie.startsWith('accessToken=')) accessToken = cookie.split('=')[1];
    })
    return accessToken
  }

  public isAuthenticated() {
    let accessToken = this.getAccessToken();
    return accessToken.length > 0;
  }
}
