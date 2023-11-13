import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";


@Component({
  selector: 'app-singup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent{
  signupObj: any = {
    email:'',
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

  onSignUp() {
    this.authService.setSignUpUser(this.signupObj);
  }

}
