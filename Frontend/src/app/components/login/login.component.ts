import {Component, OnInit} from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  loginObj: any = {
    userName:'',
    password:''
  }

  constructor() { }

  /**
   * Navigates between the pages.
   */
  navigateToPage(): void {
    alert("Navigate");
  }

  /**
   * Attempts to log in with the provided credentials.
   * If successful, navigates to the home page; otherwise, logs an error and displays an alert.
   */
  onLogin() {
    alert("Login")
  }

}
