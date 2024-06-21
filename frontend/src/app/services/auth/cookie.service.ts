import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CookieService {

  constructor() { }

  setCookie(name: string, value: string, maxAge: number): void {
    document.cookie = `${name}=${value}; max-age=${maxAge}; path=/; samesite=None; secure`;
  }

  unsetCookie(name: string): void {
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/; samesite=None; secure`;
  }

  getCookie(name: string): string | undefined {
    let cookieValue;
    document.cookie.split(';').forEach(cookie => {
      cookie = cookie.trim();
      if (cookie.startsWith(`${name}=`)) cookieValue = cookie.split('=')[1];
    });
    return cookieValue;
  }
}
