import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, tap} from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private readonly _jwtTokenKey = 'jwt_token';
    private _isLoggedIn$ = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient) {
        this.checkToken();
    }

    get jwtTokenKey() {
        return this._jwtTokenKey;
    }

    login(username: string, password: string): Observable<any> {
        return this.http.post<any>('http://localhost:8080/api/auth', { username, password }).pipe(
            tap((response) => {
                if (response && response.token) {
                    this.setTokenInCookie(response.token);
                    this._isLoggedIn$.next(true);
                }
            })
        );
    }

    private setTokenInCookie(token: string): void {
        document.cookie = `jwt_token=${token};path=/;`;
    }

    isLoggedIn(): Observable<boolean> {
        return this._isLoggedIn$.asObservable();
    }

    public getCookie(name: string): string | null {
        const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
        return match ? match[2] : null;
    }

    private checkToken(): void {
        const token = this.getCookie(this.jwtTokenKey);
        this._isLoggedIn$.next(!!token);
    }

      logout(): void {
        document.cookie = 'jwt_token=; Max-Age=0';
        this._isLoggedIn$.next(false);
      }

}
