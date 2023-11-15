import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, tap} from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private readonly jwtTokenKey = 'jwt_token';
    private _isLoggedIn$ = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient) {
        this.checkToken();
    }

    // Method for the login process
    login(username: string, password: string): Observable<any> {
        return this.http.post<any>('/url-backend', { username, password }).pipe(//TODO change URL for the Backend
            tap((response) => {
                if (response && response.token) { // Check if the server response contains a token
                    this.setToken(response.token);
                }
            })
        );
    }
    // Checks if the user is logged in by verifying the presence of a token in local storage
    isLoggedIn(): Observable<boolean> {
        return this._isLoggedIn$.asObservable();
    }

    // Sets the JWT token in local storage and updates the isLoggedIn variable
    private setToken(token: string): void {
        localStorage.setItem(this.jwtTokenKey, token);
        this._isLoggedIn$.next(true);
    }

    // Checks at the start of the application if a token is present in local storage
    private checkToken(): void {
        const token = localStorage.getItem(this.jwtTokenKey);
        this._isLoggedIn$.next(!!token);
    }
}
