import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";

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

  constructor(private router:Router, private authService: AuthService) { }

  /**
   * Navigates between the pages.
   */
  navigateToPage(route: string): void {
    this.router.navigate([route]);
  }

    /**
     * Attempts to log in with the provided credentials.
     * If successful, navigates to the home page; otherwise, logs an error and displays an alert.
     */
  onLogin() {
        const isLoggedIn = this.authService.checkLogin(this.loginObj.userName, this.loginObj.password);

        if (isLoggedIn) {
            this.router.navigate(['/home']);
        } else {
            console.log('Login fehlgeschlagen');
            alert("Login fail");
        }
    }

}
