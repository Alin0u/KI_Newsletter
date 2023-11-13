import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private signUpUser: any[] = [];

  /**
   * Getter function to retrieve the sign-up user data
   */
  getSignUpUser(): any[] {
    return this.signUpUser;
  }

  /**
   * Setter function to add a new user to the sign-up data
   */
  setSignUpUser(user: any): void {
    this.signUpUser.push(user);
    localStorage.setItem('signUpUser', JSON.stringify(this.signUpUser));
    // TODO: send to Backend
  }

  /**
   * unction to check if a user with the provided username and password exists in the sign-up data
   */
  checkLogin(username: string, password: string): boolean {
    const foundUser = this.signUpUser.find(user => user.userName === username && user.password === password);
    return !!foundUser;
  }
}
